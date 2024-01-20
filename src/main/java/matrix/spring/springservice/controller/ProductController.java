package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController {

    private static final String PRODUCT_PATH = "/api/v1/product";
    private static final String PRODUCT_PATH_ID = PRODUCT_PATH + "/{product_id}";

    private final ProductService productService;

    @GetMapping(value = PRODUCT_PATH)
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

//    @GetMapping(value = PRODUCT_PATH_ID) {
//        return productService.getProductById();
//    }


}
