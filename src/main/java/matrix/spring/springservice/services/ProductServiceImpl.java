package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private Map<UUID, ProductDTO> productDTOMap;

    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID product_id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductDTO> getProductByCategory(String category_name) {
        return Optional.empty();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public void updateProduct(UUID product_id, ProductDTO productDTO) {

    }

    @Override
    public Boolean deleteProductById(UUID product_id) {
        return null;
    }

    @Override
    public Boolean deleteUserById(UUID user_id) {
        return null;
    }

    @Override
    public List<ReviewDTO> getProductReviews(UUID product_id) {
        return null;
    }
}
