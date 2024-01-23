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
    public Optional<ProductDTO> getProductById(UUID product_id) {
        return Optional.of(productDTOMap.get(product_id));
    }

    @Override
    public Optional<ProductDTO> getProductByCategory(UUID category_id) {
        return Optional.empty();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        ProductDTO newProduct = ProductDTO.builder()
                .product_name(productDTO.getProduct_name())
                .product_description(productDTO.getProduct_description())
                .price(productDTO.getPrice())
                .product_quantity(productDTO.getProduct_quantity())
                .brand(productDTO.getBrand())
                .sold_quantity(0)
                .build();

        productDTOMap.put(newProduct.getId(), productDTO);

        return newProduct;
    }

    @Override
    public Optional<ProductDTO> updateProductById(UUID product_id, ProductDTO productDTO) {

        ProductDTO existing = productDTOMap.get(product_id);

        existing.setProduct_name(productDTO.getProduct_name());
        existing.setProduct_description(productDTO.getProduct_description());
        existing.setPrice(productDTO.getPrice());
        existing.setProduct_quantity(productDTO.getProduct_quantity());
        existing.setBrand(productDTO.getBrand());
        existing.setSold_quantity(productDTO.getSold_quantity());
        existing.setIs_deleted(productDTO.getIs_deleted());

        productDTOMap.put(existing.getId(), existing);

        return Optional.of(existing);
    }

    @Override
    public Optional<ProductDTO> deleteProductById(UUID product_id, ProductDTO productDTO) {

        ProductDTO existingProduct = productDTOMap.get(product_id);

        existingProduct.setIs_deleted(LocalDateTime.now());

        productDTOMap.put(existingProduct.getId(), existingProduct);

        return Optional.of(existingProduct);
    }

    @Override
    public List<ReviewDTO> getProductReviews(UUID product_id) {

        return new ArrayList<>(reviewDTOMap.values());

    }
}
