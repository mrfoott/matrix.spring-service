package matrix.spring.springservice.services;

import matrix.spring.springservice.entities.Category;
import matrix.spring.springservice.models.CategoryDTO;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    Optional<ProductDTO> getProductById(UUID productId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    List<ProductDTO> getProductsByCategory(Integer categoryId);

    ProductDTO createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProductById(UUID productId, ProductDTO productDTO);

    Optional<ProductDTO> deleteProductById(UUID productId, ProductDTO productDTO);

    List<ReviewDTO> getProductReviews(UUID productId);

}
