package com.abhishek.sampleresourceserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ProblemFieldException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> resourceNotFoundException(ProblemFieldException ex) {
        return Mono.just(new ErrorResponse(400, ex.getMessage()));
    }
}
