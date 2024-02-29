package org.example.productservice.service;

import org.example.productservice.entity.ApprovalQueue;
import org.example.productservice.entity.Product;

import java.util.List;
import java.util.Map;


public interface ApprovalQueueService {

    ApprovalQueue save(ApprovalQueue.ApprovalType type, Product product);

    List<ApprovalQueue> getApprovalQueueProducts();

    Map<String,String> approve(String approvalId);

    Map<String,String> reject(String approvalId);
}
