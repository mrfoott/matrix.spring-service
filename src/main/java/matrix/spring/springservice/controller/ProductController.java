package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import matrix.spring.springservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController {

    private static final String PRODUCT_PATH = "/api/v1/product";
    private static final String PRODUCT_PATH_ID = PRODUCT_PATH + "/{productId}";
    private static final String PRODUCT_PATH_CATEGORY = PRODUCT_PATH + "/{category_id}";


    private final ProductService productService;

    @GetMapping(value = PRODUCT_PATH)
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

//    @GetMapping(value = PRODUCT_PATH_ID)
//    public ProductDTO getProductById(@PathVariable("productId") UUID productId) {
//        return productService.getProductById(productId).orElseThrow(NotFoundException::new);
//    }

    @GetMapping(value = PRODUCT_PATH_CATEGORY)
    public ProductDTO getProductByCategory(@PathVariable("category_id") UUID category_id) {
        return productService.getProductByCategory(category_id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value = PRODUCT_PATH)
    public ResponseEntity createProduct(@Validated @RequestBody ProductDTO productDTO) {

        ProductDTO newProduct = productService.createProduct(productDTO);

//        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(value = PRODUCT_PATH_ID)
    public ResponseEntity updateProductById(@PathVariable("productId") UUID productId, @Validated @RequestBody ProductDTO productDTO) {

        if (productService.updateProductById(productId, productDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PatchMapping(value = PRODUCT_PATH_ID)
    public ResponseEntity deleteProductById(UUID productId, @RequestBody ProductDTO productDTO) {
        productService.deleteProductById(productId, productDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = PRODUCT_PATH_ID)
    public List<ReviewDTO> getProductReviews(@RequestParam(required = true) UUID productId) {
        return productService.getProductReviews(productId);
    }

}
