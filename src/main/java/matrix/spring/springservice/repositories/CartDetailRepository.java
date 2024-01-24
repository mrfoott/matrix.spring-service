package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartDetailRepository extends JpaRepository<CartDetail, UUID> {

    List<CartDetail> findAllByUserId(UUID userId);

}
