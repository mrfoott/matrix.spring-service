package matrix.spring.springservice.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @ObjectFactory
    public <T> T map(Integer id, @TargetType Class<T> entityClass) {
        return entityManager.find(entityClass, id);
    }
}
