package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByRoleId(Integer roleId);

    @Query("SELECT u FROM User u WHERE u.isDeleted IS NULL")
    List<User> findAllByIsDeletedNull();
}
