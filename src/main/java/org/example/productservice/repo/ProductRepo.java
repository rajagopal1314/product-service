package org.example.productservice.repo;

import org.example.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

    List<Product> findByStatusOrderByPostedDateDesc(Product.Status status);

    List<Product> findByProductNameContainingIgnoreCaseAndPriceBetweenAndPostedDateBetween(
            String productName, double minPrice, double maxPrice, LocalDateTime minPostedDate, LocalDateTime maxPostedDate);

}
