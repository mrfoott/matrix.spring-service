package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}
