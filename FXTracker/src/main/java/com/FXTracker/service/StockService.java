package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Stock;
import com.FXTracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * @param stockDto represents object of StockDto class
     * @return object of class StockDto
     */
    public StockDto addStock(StockDto stockDto) {

        try {
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

        Optional<Stock> stock = stockRepository.findStockBySymbol(symbol);

        if (stock.isEmpty()) {
            throw new StockNotFoundException(String.format("Stock was not found with given symbol: %s", symbol));
        } else {
            return stockMapper.toDto(stock.get());
        }
    }

    /**
     * @param symbol represents stock symbol
     * @param stock  takes object of class StockDto as a parameter
     * @return updated object of class StockDto
     */
    public StockDto updateStock(String symbol, StockDto stock) {

        var updated = getStock(symbol);

        try {
            stock.setId(updated.getId());

        } catch (Exception ex) {

            throw new StockServiceException("Error occurred while updating a stock.");
        }

        stockRepository.save(stockMapper.toStock(stock));

        return stock;
    }

    /**
     * @return list of objects of class StockDto
     */
    public List<StockDto> findAllStocks() {

        List<Stock> stocks = stockRepository.findAll();

        if (stocks.isEmpty()) {
            throw new StockNotFoundException("No stocks were found.");
        }

        return stocks.stream()
                .map(stockMapper::toDto)
                .collect(toList());
    }

    /**
     * @param symbol represents stock symbol
     * @return true if stock exists in DB, false otherwise
     */
    public boolean stockExistsInDataBase(String symbol) {

        return stockRepository.existsBySymbol(symbol);
    }

}
