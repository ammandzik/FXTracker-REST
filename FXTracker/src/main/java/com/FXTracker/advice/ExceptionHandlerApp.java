package com.FXTracker.advice;

import com.FXTracker.exception.*;
import com.FXTracker.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ExceptionHandlerApp {

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStockNotFound(StockNotFoundException exception) {

        log.error("Handling StockNotFoundException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.NOT_FOUND, exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockServiceException.class)
    public ResponseEntity<ErrorResponse> handleStockServiceServiceError(StockServiceException exception) {

        log.error("Handling StockServiceException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {

        log.error("Handling ResourceNotFoundException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.NOT_FOUND, exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException exception) {

        log.error("Handling InsufficientStockException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.NOT_ACCEPTABLE, exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(WalletServiceException.class)
    public ResponseEntity<ErrorResponse> handleWalletServiceException(WalletServiceException exception) {

        log.error("Handling WalletServiceException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException exception) {

        log.error("Handling InsufficientFundsException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.NOT_ACCEPTABLE, exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(PortfolioServiceException.class)
    public ResponseEntity<ErrorResponse> handlePortfolioServiceException(PortfolioServiceException exception) {

        log.error("Handling PortfolioServiceException: {}", exception.getMessage());

        var response = createResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createResponse(HttpStatusCode status, Exception exception) {

        return ErrorResponse.builder()
                .status(status.value())
                .message(exception.getMessage())
                .build();
    }

}
