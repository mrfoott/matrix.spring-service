package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.ReceiverInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReceiverInfoRepository extends JpaRepository<ReceiverInfo, UUID> {
    List<ReceiverInfo> findAllByUserId(UUID userId);
}
