package com.example.demo.Exception;


public class DuplicateDealTransactionException extends DealTransactionException {

    public DuplicateDealTransactionException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
