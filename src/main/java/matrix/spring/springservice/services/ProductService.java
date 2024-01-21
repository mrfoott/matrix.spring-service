package matrix.spring.springservice.services;

import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    Optional<ProductDTO> getProductById(UUID product_id);

    Optional<ProductDTO> getProductByCategory(UUID category_id);

    ProductDTO createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProductById(UUID product_id, ProductDTO productDTO);

    Optional<ProductDTO> deleteProductById(UUID product_id, ProductDTO productDTO);

    List<ReviewDTO> getProductReviews(UUID product_id);

}
