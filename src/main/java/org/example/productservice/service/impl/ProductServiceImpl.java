package org.example.productservice.service.impl;

import org.example.productservice.dto.CreateProductRequest;
import org.example.productservice.entity.ApprovalQueue;
import org.example.productservice.entity.Product;
import org.example.productservice.exception.ProductException;
import org.example.productservice.repo.ProductRepo;
import org.example.productservice.service.ApprovalQueueService;
import org.example.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ApprovalQueueService approvalQueueService;

    public ProductServiceImpl(ProductRepo productRepo, ApprovalQueueService approvalQueueService) {
        this.productRepo = productRepo;
        this.approvalQueueService = approvalQueueService;
    }

    @Override
    public Product save(CreateProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        if (request.getPrice() > 5000) {
            product.setStatus(Product.Status.INACTIVE);

        } else {
            product.setStatus(Product.Status.ACTIVE);
        }
        Product product1 = productRepo.save(product);
        if (product1.getStatus() == Product.Status.INACTIVE) {
            approvalQueueService.save(ApprovalQueue.ApprovalType.PRICE_APPROVAL, product1);
        }
        return product1;
    }

    @Override
    public Product get(String productId) {
        return productRepo.findById(productId)
                .orElseThrow(()-> new ProductException("product does not exists with id "+productId));
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return productRepo.findByStatusOrderByPostedDateDesc(Product.Status.ACTIVE);
    }

    @Override
    public Product update(String productId, CreateProductRequest request) {
        Product product = get(productId);
        product.setProductName(request.getProductName());

        if (request.getPrice() > product.getPrice() * 1.5) {
            product.setStatus(Product.Status.INACTIVE);
            product.setApprovalPrice(request.getPrice());
            approvalQueueService.save(ApprovalQueue.ApprovalType.PRICE_APPROVAL, product);
        } else {
            product.setPrice(request.getPrice());
        }
        return productRepo.save(product);
    }

    @Override
    public ApprovalQueue delete(String productId) {
        return  approvalQueueService.save(ApprovalQueue.ApprovalType.DELETE_PRODUCT, get(productId));
    }

    public List<Product> searchProducts(String productName, double minPrice, double maxPrice,
                                        LocalDateTime minPostedDate, LocalDateTime maxPostedDate) {
        return productRepo.findByProductNameContainingIgnoreCaseAndPriceBetweenAndPostedDateBetween(
                productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
    }
}
