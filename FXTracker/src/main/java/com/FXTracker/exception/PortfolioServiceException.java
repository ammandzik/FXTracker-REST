package com.FXTracker.exception;

public class PortfolioServiceException extends RuntimeException{
    public PortfolioServiceException(String message) {
        super(message);
    }

    public PortfolioServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
