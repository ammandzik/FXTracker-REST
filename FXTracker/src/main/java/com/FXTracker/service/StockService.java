package com.FXTracker.service;

import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.model.StockWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
public class StockService {

    private final RefreshService refreshService;

    public StockWrapper findStock(final String ticker) {

        try {

            var stock = YahooFinance.get(ticker);

            if (stock == (null)) {

                throw new StockNotFoundException(String.format("Stock with ticker: %s not found", ticker));
            }

            return new StockWrapper(stock);


        } catch (IOException e) {

            throw new StockServiceException(e.getMessage(), e.getCause());
        }

    }

    public BigDecimal findPrice(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPrice();

    }

    public List<StockWrapper> findStocks(final List<String> tickers) {

        return tickers.stream()
                .map(this::findStock)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public BigDecimal findPercentageChange(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeInPercent();
    }

    public BigDecimal findChangeFrom50AvgPercent(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg50InPercent();
    }

    public BigDecimal findChangeFrom200AvgPercent(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg200InPercent();
    }

    public BigDecimal findYearlyHigh(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getYearHigh();
    }

    public BigDecimal findYearlyLow(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getYearLow();
    }

    public BigDecimal findDailyHigh(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getDayHigh();
    }

    public BigDecimal findDailyLow(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getDayLow();
    }

    public BigDecimal getAskPrice(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getAsk();
    }

    public BigDecimal getBidPrice(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getBid();
    }

    public BigDecimal findPreviousClose(final StockWrapper stock) throws IOException {

        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPreviousClose();
    }

}
