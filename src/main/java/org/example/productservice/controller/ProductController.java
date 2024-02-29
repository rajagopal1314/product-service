package org.example.productservice.controller;

import org.example.productservice.dto.CreateProductRequest;
import org.example.productservice.entity.ApprovalQueue;
import org.example.productservice.entity.Product;
import org.example.productservice.service.ApprovalQueueService;
import org.example.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    private final ApprovalQueueService approvalQueueService;

    public ProductController(ProductService productService, ApprovalQueueService approvalQueueService) {
        this.productService = productService;
        this.approvalQueueService = approvalQueueService;
    }

    @PostMapping
    public Product save(@RequestBody CreateProductRequest request) {
        return productService.save(request);
    }

    @GetMapping("/{productId}")
    public Product get(@PathVariable("productId") String productId) {
        return productService.get(productId);
    }

    @GetMapping
    public List<Product> getAllActiveProducts() {
        return productService.getAllActiveProducts();
    }

    @GetMapping("search")
    public List<Product> getProducts(@RequestParam(value = "productName", required = false) String productName,
                                     @RequestParam(value = "minPrice", required = false) double minPrice,
                                     @RequestParam(value = "maxPrice", required = false) double maxPrice,
                                     @RequestParam(value = "minPostedDate", required = false) LocalDateTime minPostedDate,
                                     @RequestParam(value = "maxPostedDate", required = false) LocalDateTime maxPostedDate) {
        return productService.searchProducts(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
    }

    @PutMapping("/{productId}")
    public Product update(@PathVariable("productId") String productId, @RequestBody CreateProductRequest request) {
        return productService.update(productId, request);
    }

    @DeleteMapping("/{productId}")
    public ApprovalQueue delete(@PathVariable("productId") String productId) {
        return productService.delete(productId);
    }

    @GetMapping("/approval-queue")
    public List<ApprovalQueue> getApprovalQueueProducts() {
        return approvalQueueService.getApprovalQueueProducts();
    }

    @PutMapping("/approval-queue/{approvalId}/approve")
    public Map<String, String> approve(@PathVariable("approvalId") String approvalId) {
        return approvalQueueService.approve(approvalId);
    }

    @PutMapping("/approval-queue/{approvalId}/reject")
    public Map<String, String> reject(@PathVariable("approvalId") String approvalId) {
        return approvalQueueService.reject(approvalId);
    }
}
