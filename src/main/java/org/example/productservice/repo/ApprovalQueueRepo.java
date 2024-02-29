package org.example.productservice.repo;

import org.example.productservice.entity.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApprovalQueueRepo extends JpaRepository<ApprovalQueue, String> {
//    List<ApprovalQueue> findAllByRequestedDate();
}
