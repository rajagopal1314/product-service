package org.example.productservice.exception;

import org.example.productservice.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorResponse> handle(ProductException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder().message(e.getMessage()).build());
    }
}
