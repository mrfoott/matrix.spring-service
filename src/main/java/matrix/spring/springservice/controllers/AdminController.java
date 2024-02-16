package matrix.spring.springservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.CartDetailDTO;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.ReviewDTO;
import matrix.spring.springservice.models.UserDTO;
import matrix.spring.springservice.services.ProductService;
import matrix.spring.springservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController {

    ProductService productService;

    UserService userService;

    private final String ADMIN_PATH = "/api/v1/admin";

    //    Admin get all products
    private final String ADMIN_PRODUCTS = ADMIN_PATH + "/products";
    //    Admin get product's info
    private final String ADMIN_PRODUCT_ID = ADMIN_PRODUCTS + "/{productId}";

    //    Admin get all users
    private final String ADMIN_USERS = ADMIN_PATH + "/users";
    //    Admin get user's info
    private final String ADMIN_USER_ID = ADMIN_USERS + "/{userId}";

    //    Admin get all orders
    private final String ADMIN_ORDERS = ADMIN_PATH + "/orders";
    //    Admin get order's info
    private final String ADMIN_ORDER_ID = ADMIN_ORDERS + "/{orderId}";

//    GET MAPPING
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    public Optional<ProductDTO> getProductById(@PathVariable("productId") UUID productId) {
        return productService.getProductById(productId);
    }

    public List<ProductDTO> getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

//    POST MAPPING

    public ResponseEntity createProduct(@Validated @RequestBody ProductDTO productDTO) {

        ProductDTO newProduct = productService.createProduct(productDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }

//    PUT MAPPING

    public ResponseEntity updateProductById(@PathVariable("productId") UUID productId, @Validated @RequestBody ProductDTO productDTO) {
        if (productService.updateProductById(productId, productDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    public ResponseEntity deleteProductById(@PathVariable("productId") UUID productId, @RequestBody ProductDTO productDTO) {
        if (productService.deleteProductById(productId, productDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public List<ReviewDTO> getProductReviews(UUID productId) {
        return productService.getProductReviews(productId);
    }

    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    public Optional<UserDTO> getUserById(UUID userId) {
        return userService.getUserById(userId);
    }

    public ResponseEntity createUser(@Validated @RequestBody UserDTO userDTO) {

        UserDTO newUser = userService.createUser(userDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }

    public ResponseEntity deleteUserById(@PathVariable("userId") UUID userId) {
        if (userService.deleteUserById(userId).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public List<CartDetailDTO> getCartInfo(UUID userId) {

        return userService.getCartInfo(userId);

    }

    public ResponseEntity updateUserById(@PathVariable("userId") UUID userId, @RequestBody UserDTO userDTO) {

        if (userService.updateUserById(userId, userDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
