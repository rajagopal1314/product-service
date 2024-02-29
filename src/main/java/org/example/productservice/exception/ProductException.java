package org.example.productservice.exception;


public class ProductException extends RuntimeException{
    private String message;

    public ProductException(String message){
        super(message);
    }
}
