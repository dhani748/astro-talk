package com.astrotalk.exception;

public class ConsultationNotActiveException extends RuntimeException {
    public ConsultationNotActiveException(String message) {
        super(message);
    }
}
