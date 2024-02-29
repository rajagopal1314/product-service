package org.example.productservice.service;

import org.example.productservice.dto.CreateProductRequest;
import org.example.productservice.entity.ApprovalQueue;
import org.example.productservice.entity.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface ProductService {

    Product save(CreateProductRequest request);

    Product get(String productId);

    List<Product> getAllActiveProducts();

    Product update(String productId, CreateProductRequest request);

    ApprovalQueue delete(String productId);

    List<Product> searchProducts(String productName, double minPrice, double maxPrice,
                                 LocalDateTime minPostedDate, LocalDateTime maxPostedDate);

}
