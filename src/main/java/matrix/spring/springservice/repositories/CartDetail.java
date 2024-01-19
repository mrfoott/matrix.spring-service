package matrix.spring.springservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartDetail extends JpaRepository<matrix.spring.springservice.entities.CartDetail, UUID> {
}
