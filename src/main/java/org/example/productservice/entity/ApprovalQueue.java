package org.example.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
public class ApprovalQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private ApprovalType type;

    @ManyToOne
    private Product product;

    @CreationTimestamp
    private LocalDateTime requestedDate;

    public enum ApprovalType {
        PRICE_APPROVAL, DELETE_PRODUCT
    }
}
