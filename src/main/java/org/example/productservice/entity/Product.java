package org.example.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String productName;

    @Column
    private Double price;

    @Column
    private Double approvalPrice;

    @Column
    private Status status;

    @CreationTimestamp
    private LocalDateTime postedDate;

    public enum Status {
        ACTIVE, INACTIVE
    }
}
