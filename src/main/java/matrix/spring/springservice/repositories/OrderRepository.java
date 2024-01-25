package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserId(UUID userId);
}
