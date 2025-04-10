package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.*;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import com.FXTracker.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.FXTracker.advice.ExceptionMessages.OPERATION_NOT_ALLOWED;

/**
 * Service class for handling operations on users portfolios.
 * Handles operations like creating, updating, adding stocks and counting balance of user portfolio.
 */

@RequiredArgsConstructor
@Service
@Log4j2
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final StockService stockService;
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private static final String LOG_NULL = "Null values occurred while updating portfolio";

    /**
     * creates and saves Portfolio class entity to DB
     *
     * @param portfolioDto takes object of class PortfolioDto as a parameter
     * @return entity of class Portfolio
     */
    public Portfolio createPortfolio(PortfolioDto portfolioDto) {

        log.info("Invoked createPortfolio method");
        Map<String, String> stocks = new HashMap<>();

        if (portfolioDto == null) {
            log.warn("Error saving portfolio to DB");
            throw new PortfolioServiceException("Error occurred while saving Portfolio");
        }
        if(portfolioExists(portfolioDto.getUserId())){
            log.warn("Portfolio with given ID already exists!");
            throw new ExistingResourceException(String.format("Portfolio with user ID: %s already exists!", portfolioDto.getUserId()));
        }

        var entity = portfolioMapper.toEntity(portfolioDto);
        entity.setStocks(stocks);
        portfolioRepository.save(entity);

        log.info("Saving Portfolio to DB {}", portfolioDto);
        return entity;
    }

    /**
     * Finds user portfolio based on user ID
     *
     * @param userId represents user ID
     * @return object of class PortfolioDto
     */
    public PortfolioDto portfolioByUserId(String userId) {
        log.info("Invoked portfolioByUserId method");

        if (userId == null) {
            log.warn("User id is null.");
            throw new PortfolioServiceException("User id is null.");
        }
        log.info("Fetching portfolio for user with id {}", userId);
        return portfolioRepository.findByUserId(userId)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found with Id: %s", userId)));
    }

    /**
     * updates balance on portfolio
     *
     * @param portfolio entity of class Portfolio
     * @param symbol    represents stock symbol
     * @param quantity  represents quantity of stocks
     */
    public void updatePortfolio(Portfolio portfolio, String symbol, String quantity) {

        if (portfolio == null || symbol == null || quantity == null) {

            log.warn(LOG_NULL);
            throw new PortfolioServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }

        log.info("Invoked updatePortfolio method");
        try {
            countProfitAndLoss(portfolio, symbol, quantity);

        } catch (Exception e) {
            log.warn("Error while updating portfolio with ID {}", portfolio.getId());
            throw new PortfolioServiceException("Error occurred while updating portfolio.");
        }
    }

    /**
     * handles buy/sell operations on portfolio
     *
     * @param userId   represents user ID
     * @param symbol   represents stock symbol
     * @param quantity represents stock quantity
     * @return updated portfolio
     */
    @Transactional
    public Portfolio updateStocksInPortfolio(String userId, String symbol, String quantity) {

        log.info("Invoked updateStocksInPortfolio method");

        if (userId == null || symbol == null || quantity == null) {
            log.warn(LOG_NULL);
            throw new PortfolioServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }

        var portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found for user id: %s ", userId)));

        Map<String, String> stocks;
        try {
            stocks = portfolio.getStocks();
            stocks.containsKey(symbol);

        } catch (Exception ex) {
            log.warn("Error while updating stocks in portfolio with user ID {}", userId);
            throw new ResourceNotFoundException(String.format("No stocks were found for portfolio ID: %s", portfolio.getId()));
        }

        addStock(portfolio.getStocks(), quantity, symbol, userId);
        updatePortfolio(portfolio, symbol, quantity);

        log.info("Saving updated portfolio for user with ID {}", userId);
        return portfolioRepository.save(portfolio);
    }

    /**
     * handles adding stocks to stocks in portfolio
     *
     * @param stocks   represents user portfolio of stocks
     * @param quantity represents the amount of stock bought/sold
     * @param symbol   represents stock symbol
     */
    public void addStock(Map<String, String> stocks, String quantity, String symbol, String userId) {

        log.info("Invoked addStock method");
        double priceSum = stockService.countStocksTotalPrice(symbol, quantity);
        walletService.updateWalletBalance(userId, priceSum);


        if (stocks == null || quantity == null || symbol == null) {
            log.warn(LOG_NULL);
            throw new StockServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }

        int traded = Integer.parseInt(quantity);
        int sum = parseIfContainsSymbol(stocks, symbol) + traded;

        if (sum >= 0) {
            if (stocks.containsKey(symbol)) stocks.put(symbol, String.valueOf(sum));
            else stocks.put(symbol, quantity);
        } else
            throw new InsufficientStockException(String.format("Operation not allowed. Not enough stocks with Symbol: %s in portfolio", symbol));

    }

    /**
     * handles parsing number of owned and existing stocks from String to Integer
     *
     * @param stocks represents user portfolio of stocks
     * @param symbol represents stock symbol
     * @return int value of owned stock if symbol exists in portfolio
     */
    public Integer parseIfContainsSymbol(Map<String, String> stocks, String symbol) {

        log.info("Invoked parseIfContainsSymbol method");

        int owned = 0;

        if (symbol == null) {
            log.info(LOG_NULL);
            throw new ResourceNotFoundException(OPERATION_NOT_ALLOWED.getDescription());
        }
        if (stocks.containsKey(symbol)) {
            owned = Integer.parseInt(stocks.get(symbol));
            return owned;
        }
        return owned;
    }

    /**
     * @param portfolio represents user portfolio of stocks
     * @param symbol    represents stock symbol
     * @param quantity  represents quantity of stocks
     * @return result between balance and budget spent
     */

    public double countProfitAndLoss(Portfolio portfolio, String symbol, String quantity) {

        log.info("Invoked countProfitAndLoss method");
        if (portfolio == null || symbol == null || quantity == null) {
            log.warn(LOG_NULL);
            throw new PortfolioServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        double budgetSpent = countBudgetSpent(portfolio, symbol, quantity);
        double balance = countBalance(portfolio);
        double result = balance - budgetSpent;

        if (result == 0) {
            portfolio.setProfit(0d);
            portfolio.setLoss(0d);
        }
        if (result > 0) portfolio.setProfit(result);
        else if (result < 0) portfolio.setLoss(result);

        return result;
    }

    /**
     * @param portfolio takes portfolio as parameter
     * @return balance counted based on the amount and price of the stock
     */
    public Double countBalance(Portfolio portfolio) {

        log.info("Invoked countBalance method");
        if (portfolio == null) {
            log.warn(LOG_NULL);
            throw new PortfolioServiceException(OPERATION_NOT_ALLOWED.getDescription());

        }
        Map<String, String> stocks = portfolio.getStocks();

        double balance = 0;

        for (Map.Entry<String, String> entry : stocks.entrySet()) {

            double sum = stockService.countStocksTotalPrice(entry.getKey(), entry.getValue());

            balance += sum;
        }
        portfolio.setBalance(balance);
        return balance;
    }

    /**
     * Counts user budget spent on stocks
     *
     * @param portfolio takes portfolio as parameter
     * @param symbol    represents stock symbol
     * @param quantity  represents the amount of stock bought/sold
     * @return budgetSpent spent on stocks
     */
    public Double countBudgetSpent(Portfolio portfolio, String symbol, String quantity) {

        log.info("Invoked countBudgetSpent method");
        if (portfolio == null || symbol == null || quantity == null) {
            log.warn(LOG_NULL);
            throw new PortfolioServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        double budgetSpent = 0;
        var stock = stockService.getStock(symbol);
        double price = Double.parseDouble(stock.getPrice());
        budgetSpent += (Double.parseDouble(quantity) * price) + portfolio.getFundsSpent();
        portfolio.setFundsSpent(budgetSpent);

        return budgetSpent;
    }

    /**
     * Finds all existing portfolios
     *
     * @return list of all existing portfolios
     */
    public List<PortfolioDto> getAllPortfolios() {

        log.info("Fetching list of portfolios from DB");

        List<Portfolio> portfolios = portfolioRepository.findAll();

        if (portfolios.isEmpty()) throw new ResourceNotFoundException("No portfolios were found.");
        else return portfolios
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }

    public boolean portfolioExists(String userId) {

        log.info("Invoked portfolioExists method");
        return portfolioRepository.existsByUserId(userId);
    }

}
