package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.*;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    List<CategoryDTO> getAllCategories();

    Optional<ProductDTO> getProductById(UUID productId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getProductsByCategory(Integer categoryId);

    ProductDTO createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProductById(UUID productId, ProductDTO productDTO);

    Optional<ProductDTO> deleteProductById(UUID productId);

    Optional<ProductDTO> undeleteProductById(UUID productId);

    List<ReviewDTO> getProductReviews(UUID productId);

    RoleDTO createRole(RoleDTO roleDTO);

    List<ProductDTO> getTopSellingProducts();

    ProductImageDTO addProductImage(ProductImageDTO productImageDTO);

    List<ProductDTO> getRandomProducts();

    List<ProductDTO> getAllActiveProducts();

}
