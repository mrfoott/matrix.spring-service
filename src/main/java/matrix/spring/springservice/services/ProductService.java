package matrix.spring.springservice.services;

import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    Optional<ProductDTO> getProductById(UUID product_id);

    Optional<ProductDTO> getProductByCategory(String category_name);

    ProductDTO createProduct(ProductDTO productDTO);

    void updateProduct(UUID product_id, ProductDTO productDTO);

    Boolean deleteProductById(UUID product_id);

    Boolean deleteUserById(UUID user_id);

    List<ReviewDTO> getProductReviews(UUID product_id);

}
