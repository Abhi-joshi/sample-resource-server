package com.abhishek.sampleresourceserver.exception;

public class ProblemFieldException extends RuntimeException{
    public ProblemFieldException() {
        super();
    }
    public ProblemFieldException(String message) {
        super(message);
    }
}
