package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
