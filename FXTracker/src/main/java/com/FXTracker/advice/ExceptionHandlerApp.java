package com.FXTracker.advice;

import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerApp {

    @ExceptionHandler(StockServiceException.class)
    public String handleException(StockServiceException exception, Model model) {

        return "Sorry for any inconvenience caused. Internal server error occurred.";
    }

    @ExceptionHandler(StockServiceException.class)
    public String handleException(StockNotFoundException exception, Model model) {

        return "Given stock was not found.";
    }


}
