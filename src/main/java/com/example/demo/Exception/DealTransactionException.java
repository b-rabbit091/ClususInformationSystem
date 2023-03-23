package com.example.demo.Exception;

public class DealTransactionException extends RuntimeException {

    public DealTransactionException(String message) {
        super(message);
    }
    public DealTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}

