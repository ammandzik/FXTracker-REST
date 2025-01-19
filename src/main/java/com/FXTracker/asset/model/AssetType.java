package com.FXTracker.asset.model;

public enum AssetType {

    STOCK("Stock"),
    BOND("Bond"),
    CRYPTO("Crypto");
    final String description;

    AssetType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
