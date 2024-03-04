package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.*;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    List<CategoryDTO> getAllCategories();

    HashMap<String, ArrayList> getProductById(UUID productId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    List<ProductDTO> getProductsByCategory(Integer categoryId);

    ProductDTO createProduct(ProductDTO productDTO, List<ProductImageDTO> productImageDTOList);

    Optional<ProductDTO> updateProductById(UUID productId, ProductDTO productDTO);

    Optional<ProductDTO> deleteProductById(UUID productId);

    List<ReviewDTO> getProductReviews(UUID productId);

    RoleDTO createRole(RoleDTO roleDTO);

    HashMap<String, ArrayList> getTopSellingProducts();

    ProductImageDTO addProductImage(ProductImageDTO productImageDTO);

    List<ProductDTO> getRandomProducts();

}
