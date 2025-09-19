package com.hisham.tasks.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;

    public ExceptionResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
