package com.FXTracker.service;

import com.FXTracker.model.StockWrapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class RefreshService {

    private final Map<StockWrapper, Boolean> stockToRefresh;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Duration refreshPeriod = Duration.ofSeconds(15);

    public RefreshService() {

        stockToRefresh = new HashMap<>();
    }

    public boolean shouldRefresh(final StockWrapper stock){

        if(!stockToRefresh.containsKey(stock)){

            stockToRefresh.put(stock, false);
            return true;
        }
        return stockToRefresh.get(stock);
    }

    private void shouldRefreshEvery15Minutes() {

        scheduler.scheduleAtFixedRate(() ->
                stockToRefresh.forEach((stock, value) -> {
                    if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshPeriod))) {
                        System.out.println("Should refresh" + stock.getStock().getSymbol());
                        stockToRefresh.remove(stock);
                        stockToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
                    }
                }), 8, 15, SECONDS);
    }
}
