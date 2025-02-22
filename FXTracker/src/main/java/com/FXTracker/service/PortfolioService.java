package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for handling operations on users portfolios.
 * Handles operations like creating, updating, adding stocks and counting balance of user portfolio.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final StockService stockService;

    /**
     * creates and saves Portfolio class entity to DB
     *
     * @param portfolioDto takes object of class PortfolioDto as a parameter
     * @return entity of class Portfolio
     */
    public Portfolio createPortfolio(PortfolioDto portfolioDto) {

        Map<String, String> stocks = new HashMap<>();

        try {
            var entity = portfolioMapper.toEnity(portfolioDto);
            entity.setStocks(stocks);
            portfolioRepository.save(entity);

            return entity;

        } catch (NullPointerException exception) {
            throw new ResourceNotFoundException("No portfolio was found.");
        }
    }

    /**
     * Finds user portfolio based on user ID
     *
     * @param userId represents user ID
     * @return object of class PortfolioDto
     */
    public PortfolioDto portfolioByUserId(String userId) {

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

        countProfitAndLoss(portfolio, symbol, quantity);
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

        var portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found for user id: %s ", userId)));

        Map<String, String> stocks;

        try {
            stocks = portfolio.getStocks();
            stocks.containsKey(symbol);

        } catch (NullPointerException ex) {
            throw new ResourceNotFoundException(String.format("No stocks were found for portfolio ID: %s", portfolio.getId()));
        }

        addStock(portfolio.getStocks(), quantity, symbol);
        updatePortfolio(portfolio, symbol, quantity);

        return portfolioRepository.save(portfolio);
    }

    /**
     * handles adding stocks to stocks in portfolio
     *
     * @param stocks   represents user portfolio of stocks
     * @param quantity represents the amount of stock bought/sold
     * @param symbol   represents stock symbol
     */
    public void addStock(Map<String, String> stocks, String quantity, String symbol) {

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

        int owned = 0;

        if (symbol == null) {
            throw new ResourceNotFoundException(String.format("Given symbol was null: %s", symbol));
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
    public double countBalance(Portfolio portfolio) {

        Map<String, String> stocks = portfolio.getStocks();

        double balance = 0;

        for (Map.Entry<String, String> entry : stocks.entrySet()) {

            var stock = stockService.getStock(entry.getKey());

            double price = Double.parseDouble(stock.getPrice());
            balance += Double.parseDouble(entry.getValue()) * price;

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
    public double countBudgetSpent(Portfolio portfolio, String symbol, String quantity) {

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

        List<Portfolio> portfolios = portfolioRepository.findAll();

        if (portfolios.isEmpty()) throw new ResourceNotFoundException("No portfolios were found.");

        return portfolios
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }


}
