package com.re.session20.repository;
import com.re.session20.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.product"})
    List<PurchaseOrder> findAllByUserId(Long userId);
}