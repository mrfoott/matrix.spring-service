package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private Map<UUID, ProductDTO> productDTOMap;

    private Map<UUID, ReviewDTO> reviewDTOMap;

    public ProductServiceImpl() {
        this.productDTOMap = new HashMap<>();
        this.reviewDTOMap = new HashMap<>();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return new ArrayList<>(productDTOMap.values());
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID productId) {
        return Optional.of(productDTOMap.get(productId));
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        return new ArrayList<>(productDTOMap.values());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        ProductDTO newProduct = ProductDTO.builder()
                .productName(productDTO.getProductName())
                .productDescription(productDTO.getProductDescription())
                .price(productDTO.getPrice())
                .productQuantity(productDTO.getProductQuantity())
                .brand(productDTO.getBrand())
                .soldQuantity(0)
                .categoryId(productDTO.getCategoryId())
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
    public Optional<ProductDTO> deleteProductById(UUID productId, ProductDTO productDTO) {

        ProductDTO existingProduct = productDTOMap.get(productId);

//        existingProduct.setIsDeleted(LocalDateTime.now());

        productDTOMap.put(existingProduct.getId(), existingProduct);

        return Optional.of(existingProduct);
    }

    @Override
    public List<ReviewDTO> getProductReviews(UUID productId) {

        return new ArrayList<>(reviewDTOMap.values());

    }
}
