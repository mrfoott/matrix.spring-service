package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

//    @Query(value = "SELECT * FROM review WHERE product_id=", nativeQuery = true)
    List<Review> findAllByProduct_id(UUID product_id);

}
