package org.example.productservice.service.impl;

import org.example.productservice.entity.ApprovalQueue;
import org.example.productservice.entity.Product;
import org.example.productservice.exception.ProductException;
import org.example.productservice.repo.ApprovalQueueRepo;
import org.example.productservice.repo.ProductRepo;
import org.example.productservice.service.ApprovalQueueService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ApprovalQueueServiceImpl implements ApprovalQueueService {

    private final ApprovalQueueRepo approvalQueueRepo;

    private final ProductRepo productRepo;

    public ApprovalQueueServiceImpl(ApprovalQueueRepo approvalQueueRepo, ProductRepo productRepo) {
        this.approvalQueueRepo = approvalQueueRepo;
        this.productRepo = productRepo;
    }

    @Override
    public ApprovalQueue save(ApprovalQueue.ApprovalType type, Product product) {
        ApprovalQueue queue = new ApprovalQueue();
        queue.setProduct(product);
        queue.setType(type);
        return approvalQueueRepo.save(queue);
    }

    @Override
    public List<ApprovalQueue> getApprovalQueueProducts() {
        return approvalQueueRepo.findAll(Sort.by(Sort.Direction.ASC, "requestedDate"));
    }

    @Override
    public Map<String,String> approve(String approvalId) {
        ApprovalQueue approvalQueue = get(approvalId);
        switch (approvalQueue.getType()) {
            case PRICE_APPROVAL -> {
                Product product = approvalQueue.getProduct();
                product.setPrice(product.getApprovalPrice());
                product.setApprovalPrice(null);
                product.setStatus(Product.Status.ACTIVE);
                productRepo.save(product);
                approvalQueueRepo.delete(approvalQueue);
                return Map.of("status", "Price Updated");
            }
            case DELETE_PRODUCT -> {
                Product product = approvalQueue.getProduct();
                productRepo.delete(product);
                approvalQueueRepo.delete(approvalQueue);
                return Map.of("status", "Product deleted");
            }
        }
        approvalQueueRepo.delete(approvalQueue);
        return Map.of("status", "Approval Queue Cleared");
    }

    @Override
    public Map<String,String> reject(String approvalId) {
        ApprovalQueue approvalQueue = get(approvalId);
        switch (approvalQueue.getType()) {
            case PRICE_APPROVAL -> {
                Product product = approvalQueue.getProduct();
                product.setApprovalPrice(null);
                product.setStatus(Product.Status.ACTIVE);
                productRepo.save(product);
                approvalQueueRepo.delete(approvalQueue);
                return Map.of("status", "Price Not updated");
            }
            case DELETE_PRODUCT -> {
                approvalQueueRepo.delete(approvalQueue);
                return Map.of("status", "Product Not deleted");
            }
        }
        approvalQueueRepo.delete(approvalQueue);
        return Map.of("status", "Approval Queue Cleared");
    }

    private ApprovalQueue get(String approvalId){
       return approvalQueueRepo.findById(approvalId)
                .orElseThrow(()-> new ProductException("approval Id does not exists : "+approvalId));
    }
}
