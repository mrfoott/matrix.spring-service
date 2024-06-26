package matrix.spring.springservice.repositories;

import matrix.spring.springservice.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findAllByOrderId(UUID orderId);
}
