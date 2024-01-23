package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.Review;
import matrix.spring.springservice.mappers.ProductMapper;
import matrix.spring.springservice.mappers.ReviewMapper;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import matrix.spring.springservice.repositories.ProductRepository;
import matrix.spring.springservice.repositories.ReviewRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID product_id) {
        return Optional.ofNullable(productMapper.productToProductDto(productRepository.findById(product_id)
                .orElse(null)));
    }

    @Override
    public Optional<ProductDTO> getProductByCategory(UUID category_id) {
        return Optional.empty();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(productDTO)));
    }

    @Override
    public Optional<ProductDTO> updateProductById(UUID product_id, ProductDTO productDTO) {
        AtomicReference<Optional<ProductDTO>> atomicReference = new AtomicReference<>();

        productRepository.findById(product_id).ifPresentOrElse(existingProduct -> {
            existingProduct.setProductName(productDTO.getProduct_name());
            existingProduct.setProductDescription(productDTO.getProduct_description());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setProductQuantity(productDTO.getProduct_quantity());
            existingProduct.setBrand(productDTO.getBrand());
            existingProduct.setSoldQuantity(productDTO.getSold_quantity());
            existingProduct.setIsDeleted(productDTO.getIs_deleted());

            atomicReference.set(Optional.of(productMapper
                    .productToProductDto(productRepository.save(existingProduct))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Optional<ProductDTO> deleteProductById(UUID product_id, ProductDTO productDTO) {

        AtomicReference<Optional<ProductDTO>> atomicReference = new AtomicReference<>();

        productRepository.findById(product_id).ifPresentOrElse(existingProduct -> {
            existingProduct.setIsDeleted(productDTO.getIs_deleted());

            atomicReference.set(Optional.of(productMapper
                    .productToProductDto(productRepository.save(existingProduct))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

//    @Override
//    public Optional<ReviewDTO> getProductReviews(UUID product_id) {
//        return Optional.ofNullable(reviewMapper.reviewToReviewDto(reviewRepository.findById(product_id)
//                .orElse(null)));
//    }

//    @Override
//    public List<ReviewDTO> getProductReviews(UUID product_id) {
//        return reviewRepository.findAll(product_id)
//                .stream()
//                .map(reviewMapper::reviewToReviewDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ReviewDTO> getProductReviews(UUID product_id) {

        List<Review> listReviews;

        if (!product_id.equals("")) {
            listReviews = listReviewsOfAProduct(product_id);
        } else {
            return null;
        }

        return listReviews
                .stream()
                .map(reviewMapper::reviewToReviewDto)
                .collect(Collectors.toList());

    }

    public List<Review> listReviewsOfAProduct(UUID product_id) {
        return reviewRepository.findAllByProduct_id(product_id);
    }


}
