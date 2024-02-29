package org.example.productservice.dto;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class CreateProductRequest {

    private String productName;

    private double price;
}
