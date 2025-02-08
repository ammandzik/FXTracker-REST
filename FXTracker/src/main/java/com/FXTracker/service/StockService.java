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

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;

    public StockDto addStock(StockDto stockDto) {

        try {
            stockRepository.save(stockMapper.toStock(stockDto));

        } catch (Exception ex) {
            throw new StockServiceException("Error occurred while saving a stock.");
        }
        return stockDto;
    }

    public StockDto getStock(String symbol) {

        Optional<Stock> stock = stockRepository.findStock(symbol);

        if (stock.isEmpty()) {
            throw new StockNotFoundException(String.format("Stock was not found with given symbol: %s", symbol));

        } else {
            return stockMapper.toDto(stock.get());

        }
    }
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



    public boolean stockExistsInDataBase(String symbol) {

        return stockRepository.existsBySymbol(symbol);
    }
}
