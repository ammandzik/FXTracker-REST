package com.FXTracker.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        String message
) {}
