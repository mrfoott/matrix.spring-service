package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.Product;
import matrix.spring.springservice.entities.ProductImage;
import matrix.spring.springservice.entities.Review;
import matrix.spring.springservice.entities.ReviewImage;
import matrix.spring.springservice.mappers.*;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.repositories.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewImageMapper reviewImageMapper;
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

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
    public HashMap<String, ArrayList> getProductById(UUID productId) {

        HashMap<String, ArrayList> productInfo = new HashMap<>();

        List<Product> productList = new ArrayList<>();

        List<ReviewImage> reviewImageList = new ArrayList<>();

        List<Review> listReviews = new ArrayList<>();

        List<ProductImage> productImageList = new ArrayList<>();

        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {

            ProductDTO productDTO = productMapper.productToProductDto(product);

            productList.add(product);

            productImageList = listProductImagesOfAProduct(productId);

            productImageList
                    .stream()
                    .map(productImageMapper::productImageToProductImageDto)
                    .collect(Collectors.toList());


//        List reviews of a product

            if (!productId.toString().isEmpty()) {
                listReviews = listReviewsOfAProduct(productId);
            } else {
                return null;
            }

            listReviews
                    .stream()
                    .map(reviewMapper::reviewToReviewDto)
                    .collect(Collectors.toList());

            reviewImageList
                    .stream()
                    .map(reviewImageMapper::reviewImageToReviewImageDto)
                    .collect(Collectors.toList());

//        List review images of a product

            for (Review review :
                    listReviews) {
                List<ReviewImage> listReviewImages = listReviewImagesOfAReview(review.getId());

                for (ReviewImage reviewImage :
                        listReviewImages) {

                    reviewImageList.add(reviewImage);

                }

            }
            productInfo.put("productList", (ArrayList) productList);
            productInfo.put("productImageList", (ArrayList) productImageList);
            productInfo.put("listReviews", (ArrayList) listReviews);
            productInfo.put("reviewImageList", (ArrayList) reviewImageList);

        } else {
            return null;
        }


//        return Optional.ofNullable(productMapper.productToProductDto(productRepository.findById(productId)
//                .orElse(null)));

        return productInfo;

    }

    List<ProductImage> listProductImagesOfAProduct(UUID productId) {
        return productImageRepository.findAllByProductId(productId);
    }

    List<ReviewImage> listReviewImagesOfAReview(UUID reviewId) {
        return reviewImageRepository.findAllByReviewId(reviewId);
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
    public ProductDTO createProduct(ProductDTO productDTO, List<ProductImageDTO> productImageDTOList) {
//        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(productDTO)));

        Product product = productMapper.productDtoToProduct(productDTO);
        product = productRepository.save(product);


        List<ProductImage> productImages = new ArrayList<>();
        for (ProductImageDTO productImageDTO : productImageDTOList) {
            ProductImage productImage = productImageMapper.productImageDtoToProductImage(productImageDTO);
            productImage.setProductId(product.getId());
            productImages.add(productImage);
        }

        productImageRepository.saveAll(productImages);

        return productMapper.productToProductDto(product);

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


    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        return roleMapper.roleToRoleDto(roleRepository.save(roleMapper.roleDtoToRole(roleDTO)));
    }

    @Override
    public Map<String, List> getTopSellingProducts() {

        HashMap<String, List> listOfProducts = new HashMap<>();

        List<Product> listProducts = new ArrayList<>();

        List<ProductImage> listProductImages = new ArrayList<>();

        listProducts = get10TopSellingProducts();

        listProducts
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());

        listProductImages
                .stream()
                .map(productImageMapper::productImageToProductImageDto)
                .collect(Collectors.toList());

        for (Product product: listProducts ){

            List<ProductImage> productImageList = listProductImagesOfAProduct(product.getId());

            for (ProductImage productImage : productImageList) {
                listProductImages.add(productImage);
            }

        }

        listOfProducts.put("listProducts", listProducts);
        listOfProducts.put("listProductImages", listProductImages);

        return listOfProducts;
    }

    @Override
    public ProductImageDTO addProductImage(ProductImageDTO productImageDTO) {
        return productImageMapper.productImageToProductImageDto(productImageRepository.save(productImageMapper.productImageDtoToProductImage(productImageDTO)));
    }

    public List<Product> get10TopSellingProducts() {

        return productRepository.findTop10BySoldQuantityOrderByProductNameAsc();

    }

}
