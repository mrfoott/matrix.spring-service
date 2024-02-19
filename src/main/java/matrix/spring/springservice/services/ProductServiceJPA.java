package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.Product;
import matrix.spring.springservice.entities.Review;
import matrix.spring.springservice.mappers.CategoryMapper;
import matrix.spring.springservice.mappers.ProductMapper;
import matrix.spring.springservice.mappers.ReviewMapper;
import matrix.spring.springservice.models.CategoryDTO;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import matrix.spring.springservice.repositories.CategoryRepository;
import matrix.spring.springservice.repositories.ProductRepository;
import matrix.spring.springservice.repositories.ReviewRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Transactional
@Service
@Primary
@RequiredArgsConstructor
public class ProductServiceJPA implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID productId) {
        return Optional.ofNullable(productMapper.productToProductDto(productRepository.findById(productId)
                .orElse(null)));
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.save(categoryMapper.categoryDtoToCategory(categoryDTO)));
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId) {

        List<Product> productsListByCategory;

        if (categoryId != null) {
            productsListByCategory = listProductsByCategory(categoryId);
        } else {
            return null;
        }

        return productsListByCategory
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());

    }

    List<Product> listProductsByCategory(Integer categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(productDTO)));
    }

    @Override
    public Optional<ProductDTO> updateProductById(UUID productId, ProductDTO productDTO) {
        AtomicReference<Optional<ProductDTO>> atomicReference = new AtomicReference<>();

        productRepository.findById(productId).ifPresentOrElse(existingProduct -> {
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setProductDescription(productDTO.getProductDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setProductQuantity(productDTO.getProductQuantity());
            existingProduct.setBrand(productDTO.getBrand());
            existingProduct.setSoldQuantity(productDTO.getSoldQuantity());

            atomicReference.set(Optional.of(productMapper
                    .productToProductDto(productRepository.save(existingProduct))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Optional<ProductDTO> deleteProductById(UUID productId) {

        AtomicReference<Optional<ProductDTO>> atomicReference = new AtomicReference<>();

        productRepository.findById(productId).ifPresentOrElse(existingProduct -> {
            existingProduct.setIsDeleted(LocalDateTime.now());

            atomicReference.set(Optional.of(productMapper
                    .productToProductDto(productRepository.save(existingProduct))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

//    @Override
//    public Optional<ReviewDTO> getProductReviews(UUID productId) {
//        return Optional.ofNullable(reviewMapper.reviewToReviewDto(reviewRepository.findById(productId)
//                .orElse(null)));
//    }

//    @Override
//    public List<ReviewDTO> getProductReviews(UUID productId) {
//        return reviewRepository.findAll(productId)
//                .stream()
//                .map(reviewMapper::reviewToReviewDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ReviewDTO> getProductReviews(UUID productId) {

        List<Review> listReviews;

        if (!productId.toString().isEmpty()) {
            listReviews = listReviewsOfAProduct(productId);
        } else {
            return null;
        }

        return listReviews
                .stream()
                .map(reviewMapper::reviewToReviewDto)
                .collect(Collectors.toList());

    }

    public List<Review> listReviewsOfAProduct(UUID productId) {
        return reviewRepository.findAllByProductId(productId);
    }


}
