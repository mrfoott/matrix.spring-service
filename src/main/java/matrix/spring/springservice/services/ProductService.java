package matrix.spring.springservice.services;

import matrix.spring.springservice.models.CategoryDTO;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import matrix.spring.springservice.models.RoleDTO;

import java.util.*;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    List<CategoryDTO> getAllCategories();

    HashMap<String, ArrayList> getProductById(UUID productId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    List<ProductDTO> getProductsByCategory(Integer categoryId);

    ProductDTO createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProductById(UUID productId, ProductDTO productDTO);

    Optional<ProductDTO> deleteProductById(UUID productId);

    List<ReviewDTO> getProductReviews(UUID productId);

    RoleDTO createRole(RoleDTO roleDTO);

    Map<String, List> getTopSellingProducts();


}
