package matrix.spring.springservice.services;

import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.mappers.ProductMapper;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import matrix.spring.springservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class ProductServiceJPA implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
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
