package com.example.demo.Exception;

public class InvalidDealTransactionException extends DealTransactionException {

    public InvalidDealTransactionException(String message) {
        super(message);
    }
    public InvalidDealTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}