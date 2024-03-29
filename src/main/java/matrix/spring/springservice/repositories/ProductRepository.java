package matrix.spring.springservice.repositories;

import jakarta.transaction.Transactional;
import matrix.spring.springservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.isDeleted IS NULL ORDER BY p.soldQuantity DESC LIMIT 10")
    List<Product> findFirst10ByOrderBySoldQuantityDescAndIsDeletedIsNull();

    @Query(value = "SELECT * FROM product WHERE is_deleted IS NULL ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Product> findRandomProducts();

    @Query("SELECT p FROM Product p WHERE p.isDeleted IS NULL")
    List<Product> findAllByIsDeletedNull();

    List<Product> findAllByProductNameIsLikeIgnoreCase(String productName);

    List<Product> findAllByProductNameContainingIgnoreCase(String productName);

//    @Query("SELECT p FROM Product p ORDER BY p.soldQuantity DESC, p.productName ASC")
//    List<Product> findFirst10BySoldQuantityOrderByProductNameAsc();
}
