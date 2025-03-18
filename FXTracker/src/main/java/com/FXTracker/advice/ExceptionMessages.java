package com.FXTracker.advice;

public enum ExceptionMessages {
    OPERATION_NOT_ALLOWED("Operation could not be finished due to non existing values.");

    private String description;

    public String getDescription() {
        return description;
    }
    ExceptionMessages(String description){
        this.description = description;
    }
}
