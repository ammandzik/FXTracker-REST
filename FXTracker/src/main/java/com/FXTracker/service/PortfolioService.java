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
     * @param portfolioDto takes object of class PortfolioDto as a parameter
     * @return entity of class Portfolio
     */
    public Portfolio createPortfolio(PortfolioDto portfolioDto) {

        Map<String, String> stocks = new HashMap<>();

        var entity = portfolioMapper.toEnity(portfolioDto);

        entity.setStocks(stocks);

        portfolioRepository.save(entity);

        return entity;
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
     * Updates user portfolio
     *
     * @param userId       takes user ID as a parameter to find connected portfolio
     * @param portfolioDto takes object of class PortfolioDto as a parameter
     */
    public void updatePortfolio(String userId, PortfolioDto portfolioDto) {

        var portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found for user id: %s ", userId)));

        portfolio.setStocks(portfolioDto.getStocks());
        portfolio.setBalance(portfolioDto.getBalance());
        portfolio.setProfit(portfolioDto.getProfit());
        portfolio.setProfit(portfolioDto.getLoss());

        portfolioRepository.save(portfolio);

    }

    /**
     * handles buy/sell operations on portfolio
     *
     * @param userId   represents user ID
     * @param symbol   represents stock symbol
     * @param quantity represents stock quantity
     * @variable traded represents quantity of bought/sold stocks
     * @variable sum represents owned number of stocks + traded number of stocks
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

        int traded = Integer.parseInt(quantity);

        int sum = parseIfContainsSymbol(portfolio, symbol) + traded;

        addStock(portfolio, sum, quantity, symbol);
        portfolio.setBalance(countBalance(portfolio));

        return portfolioRepository.save(portfolio);
    }

    /**
     * handles adding stocks to stocks in portfolio
     *
     * @param portfolio represents user portfolio of stocks
     * @param sum       represents sum of quantity for buying/selling stock and owned stock
     * @param quantity  represents the amount of stock bought/sold
     * @param symbol    represents stock symbol
     */
    public void addStock(Portfolio portfolio, int sum, String quantity, String symbol) {

        Map<String, String> stocks = portfolio.getStocks();

        if (sum >= 0) {
            if (stocks.containsKey(symbol)) {
                portfolio.getStocks().put(symbol, String.valueOf(sum));

            } else portfolio.getStocks().put(symbol, quantity);
        } else
            throw new InsufficientStockException(String.format("Operation not allowed. Not enough stocks with Symbol: %s in portfolio", symbol));

    }

    /**
     * handles parsing number of owned and existing stocks from String to Integer
     *
     * @param portfolio represents user portfolio of stocks
     * @param symbol represents stock symbol
     * @return int value of owned stock if symbol exists in portfolio
     */
    public int parseIfContainsSymbol(Portfolio portfolio, String symbol) {

        int owned = 0;
        Map<String, String> stocks = portfolio.getStocks();

        if (stocks.containsKey(symbol)) {
            owned = Integer.parseInt(stocks.get(symbol));
        }
        return owned;
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
        return balance;
    }

    /**
     * Finds all existing portfolios
     *
     * @return list of all existing portfolios
     */
    public List<PortfolioDto> getAllPortfolios() {

        List<Portfolio> portfolios = portfolioRepository.findAll();

        if (portfolios.isEmpty()) {
            throw new ResourceNotFoundException("No portfolios were found.");
        }
        return portfolios
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }


}
