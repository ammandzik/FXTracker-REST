package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Stock;
import com.FXTracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Service class for handling operations on stocks.
 * Handles operations like adding, getting, updating, finding all of existing stocks.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;
    private final Logger logger = LoggerFactory.getLogger(StockService.class);

    /**
     * @param stockDto represents object of StockDto class
     * @return object of class StockDto
     */
    public StockDto addStock(StockDto stockDto) {

        try {
            logger.info("Saving {} stock to DB", stockDto);
            stockRepository.save(stockMapper.toStock(stockDto));

        } catch (Exception ex) {
            throw new StockServiceException("Error occurred while saving a stock.");
        }
        return stockDto;
    }

    /**
     * @param symbol represents stock symbol
     * @return object of class StockDto
     */
    public StockDto getStock(String symbol) {

        logger.info("Invoked findByStockSymbol method for symbol {}", symbol);
        Optional<Stock> stock = stockRepository.findStockBySymbol(symbol);

        if (stock.isEmpty()) {
            logger.warn("No stock was found for symbol {}", symbol);
            throw new StockNotFoundException(String.format("Stock was not found with given symbol: %s", symbol));
        } else {
            logger.info("Returned stockDto for symbol {}", symbol);
            return stockMapper.toDto(stock.get());
        }
    }

    /**
     * @param symbol represents stock symbol
     * @param stock  takes object of class StockDto as a parameter
     * @return updated object of class StockDto
     */
    public StockDto updateStock(String symbol, StockDto stock) {

        logger.info("Invoked updateStock method");

        logger.info("Invoked getStock method for symbol {}", symbol);
        var updated = getStock(symbol);

        try {
            logger.info("Setting id for updated stock with symbol {}", symbol);
            stock.setId(updated.getId());

        } catch (Exception ex) {
            throw new StockServiceException("Error occurred while updating a stock.");
        }

        logger.info("Saving updated stock for symbol {} to DB", symbol);
        stockRepository.save(stockMapper.toStock(stock));

        return stock;
    }

    /**
     * @return list of objects of class StockDto
     */
    public List<StockDto> findAllStocks() {

        logger.info("Invoked findAllStocks method");

        List<Stock> stocks = stockRepository.findAll();

        if (stocks.isEmpty()) {
            logger.warn("No stocks were found in DB");
            throw new StockNotFoundException("No stocks were found.");
        }else {
            logger.info("Fetching stocks {} list from DB ", stocks);
            return stocks.stream()
                    .map(stockMapper::toDto)
                    .collect(toList());
        }
    }

    /**
     * @param symbol represents stock symbol
     * @return true if stock exists in DB, false otherwise
     */
    public boolean stockExistsInDatabase(String symbol) {

        logger.info("Invoked stockExistsInDatabase method");
        return stockRepository.existsBySymbol(symbol);
    }

}
