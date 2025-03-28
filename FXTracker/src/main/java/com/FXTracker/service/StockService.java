package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Stock;
import com.FXTracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.FXTracker.advice.ExceptionMessages.OPERATION_NOT_ALLOWED;

/**
 * Service class for handling operations on stocks.
 * Handles operations like adding, getting, updating, finding all of existing stocks.
 */
@RequiredArgsConstructor
@Service
@Log4j2
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;
    private static final String NULL_MESSAGE = "Provided values are null";

    /**
     * @param stockDto represents object of StockDto class
     * @return object of class StockDto
     */
    public StockDto addStock(StockDto stockDto) {

        log.info("Invoked addStock method.");

        if (stockDto == null) {
            log.warn(NULL_MESSAGE);
            throw new StockServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        try {
            log.info("Saving {} stock to DB", stockDto);
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

        log.info("Invoked getStock method.");

        if (symbol == null) {
            log.warn(NULL_MESSAGE);
            throw new StockServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        log.info("Invoked findByStockSymbol method for symbol {}", symbol);
        Optional<Stock> stock = stockRepository.findStockBySymbol(symbol);

        if (stock.isEmpty()) {
            log.warn("No stock was found for symbol {}", symbol);
            throw new StockNotFoundException(String.format("Stock was not found with given symbol: %s", symbol));
        } else {
            log.info("Returned stockDto for symbol {}", symbol);
            return stockMapper.toDto(stock.get());
        }
    }

    /**
     * @param symbol represents stock symbol
     * @param stock  takes object of class StockDto as a parameter
     * @return updated object of class StockDto
     */
    public StockDto updateStock(String symbol, StockDto stock) {

        log.info("Invoked updateStock method");

        if (stock == null || symbol == null) {
            log.warn(NULL_MESSAGE);
            throw new StockServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        log.info("Invoked getStock method for symbol {}", symbol);
        var updated = getStock(symbol);

        try {
            log.info("Setting id for updated stock with symbol {}", symbol);
            stock.setId(updated.getId());

        } catch (Exception ex) {
            throw new StockServiceException("Error occurred while updating a stock.");
        }

        log.info("Saving updated stock for symbol {} to DB", symbol);
        stockRepository.save(stockMapper.toStock(stock));

        return stock;
    }

    /**
     * @return list of objects of class StockDto
     */
    public List<StockDto> findAllStocks() {

        log.info("Invoked findAllStocks method");

        List<Stock> stocks = stockRepository.findAll();

        if (stocks.isEmpty()) {
            log.warn("No stocks were found in DB");
            throw new StockNotFoundException("No stocks were found.");
        } else {
            log.info("Fetching stocks {} list from DB ", stocks);
            return stocks.stream()
                    .map(stockMapper::toDto)
                    .toList();
        }
    }

    /**
     * @param symbol represents stock symbol
     * @return true if stock exists in DB, false otherwise
     */
    public boolean stockExistsInDatabase(String symbol) {

        log.info("Invoked stockExistsInDatabase method");
        return stockRepository.existsBySymbol(symbol);
    }

}
