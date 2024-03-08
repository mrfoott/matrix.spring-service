package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private Map<UUID, ProductDTO> productDTOMap;

    private Map<UUID, ReviewDTO> reviewDTOMap;

    private Map<Integer, CategoryDTO> categoryDTOMap;

    private Map<Integer, RoleDTO> roleDTOMap;

    public ProductServiceImpl() {
        this.productDTOMap = new HashMap<>();
        this.reviewDTOMap = new HashMap<>();
        this.categoryDTOMap = new HashMap<>();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return new ArrayList<>(productDTOMap.values());
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return new ArrayList<>(categoryDTOMap.values());
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID productId) {
        return null;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryDTO newCategory = CategoryDTO.builder()
                .categoryName(categoryDTO.getCategoryName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        categoryDTOMap.put(newCategory.getId(), categoryDTO);

        return newCategory;

    }

    @Override
    public CategoryDTO getProductsByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        ProductDTO newProduct = ProductDTO.builder()
                .version(0)
                .productName(productDTO.getProductName())
                .productDescription(productDTO.getProductDescription())
                .price(productDTO.getPrice())
                .productQuantity(productDTO.getProductQuantity())
                .brand(productDTO.getBrand())
                .soldQuantity(0)
                .categoryId(productDTO.getCategoryId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        productDTOMap.put(newProduct.getId(), productDTO);

        return newProduct;
    }

    @Override
    public Optional<ProductDTO> updateProductById(UUID productId, ProductDTO productDTO) {

        ProductDTO existing = productDTOMap.get(productId);

        existing.setProductName(productDTO.getProductName());
        existing.setProductDescription(productDTO.getProductDescription());
        existing.setPrice(productDTO.getPrice());
        existing.setProductQuantity(productDTO.getProductQuantity());
        existing.setBrand(productDTO.getBrand());
        existing.setSoldQuantity(productDTO.getSoldQuantity());

        productDTOMap.put(existing.getId(), existing);

        return Optional.of(existing);
    }

    @Override
    public Optional<ProductDTO> deleteProductById(UUID productId) {

        ProductDTO existingProduct = productDTOMap.get(productId);

        existingProduct.setIsDeleted(LocalDateTime.now());

        productDTOMap.put(existingProduct.getId(), existingProduct);

        return Optional.of(existingProduct);
    }

    @Override
    public Optional<ProductDTO> undeleteProductById(UUID productId) {
        return Optional.empty();
    }

    @Override
    public List<ReviewDTO> getProductReviews(UUID productId) {

        return new ArrayList<>(reviewDTOMap.values());

    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        RoleDTO newRole = RoleDTO.builder()
                .roleName(roleDTO.getRoleName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        roleDTOMap.put(newRole.getId(), roleDTO);

        return newRole;

    }

    @Override
    public List<ProductDTO> getTopSellingProducts() {
        return null;
    }

    @Override
    public ProductImageDTO addProductImage(ProductImageDTO productImageDTO) {
        return null;
    }

    @Override
    public List<ProductDTO> getRandomProducts() {
        return null;
    }

    @Override
    public List<ProductDTO> getAllActiveProducts() {
        return null;
    }
}
