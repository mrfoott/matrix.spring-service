package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, UUID> {
}
