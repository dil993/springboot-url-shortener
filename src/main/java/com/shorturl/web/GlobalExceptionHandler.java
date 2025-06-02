package com.shorturl.web;

import com.shorturl.domain.exception.ShortUrlNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public String handleShortUrlNotFoundException(ShortUrlNotFoundException ex) {
        return "error/404";
    }
}
