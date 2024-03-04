package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p ORDER BY p.soldQuantity DESC, p.productName ASC")
    List<Product> findTop10BySoldQuantityOrderByProductNameAsc();

}
