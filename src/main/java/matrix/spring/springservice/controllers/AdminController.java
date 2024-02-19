package matrix.spring.springservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.services.OrderService;
import matrix.spring.springservice.services.ProductService;
import matrix.spring.springservice.services.UserService;
import org.springframework.http.HttpHeaders;
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
public class AdminController {

    private final ProductService productService;

    private final UserService userService;

    private final OrderService orderService;

    private final String ADMIN_PATH = "/api/v1/admin";

    //    Admin get all products
    private final String ADMIN_PRODUCTS = ADMIN_PATH + "/products";
    //    Admin get product's info
    private final String ADMIN_PRODUCT_ID = ADMIN_PRODUCTS + "/{productId}";


    //    Admin get all users
    private final String ADMIN_USERS = ADMIN_PATH + "/users";
    //    Admin get user's info
    private final String ADMIN_USER_ID = ADMIN_USERS + "/{userId}";
    //    Admin get all categories
    private final String ADMIN_CATEGORIES = ADMIN_PATH + "/categories";
    //    Admin products of a category
    private final String ADMIN_PRODUCTS_CATEGORY = ADMIN_CATEGORIES + "/{categoryId}";
    //    Admin get all orders
    private final String ADMIN_ORDERS = ADMIN_PATH + "/orders";
    //    Admin get order's info
    private final String ADMIN_ORDER_ID = ADMIN_ORDERS + "/{orderId}";

//    GET MAPPING

    //    /api/v1/admin/products
    @GetMapping(ADMIN_PRODUCTS)
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    //    /api/v1/admin/products/productId
    @GetMapping(ADMIN_PRODUCT_ID)
    public Optional<ProductDTO> getProductById(@PathVariable("productId") UUID productId) {
        return productService.getProductById(productId);
    }

    //    /api/v1/admin/categories
    @GetMapping(ADMIN_CATEGORIES)
    public List<CategoryDTO> getAllCategories() {
        return productService.getAllCategories();
    }

    //    /api/v1/admin/categories/categoryId
    @GetMapping(ADMIN_PRODUCTS_CATEGORY)
    public List<ProductDTO> getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping()
    public Optional<UserDTO> getUserByid(@PathVariable("userId") UUID userid) {
        return userService.getUserById(userid);
    }

    //    POST MAPPING

    //    /api/v1/admin/products
    @PostMapping(ADMIN_PRODUCTS)
    public ResponseEntity createProduct(@Validated @RequestBody ProductDTO productDTO) {

        ProductDTO newProduct = productService.createProduct(productDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }

    //    /api/v1/admin/categories
    @PostMapping(ADMIN_CATEGORIES)
    public ResponseEntity createCategory(@Validated @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO newCategory = productService.createCategory(categoryDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }


//    PUT MAPPING

//    /api/v1/admin/products/productId
    @PutMapping(ADMIN_PRODUCT_ID)
    public ResponseEntity updateProductById(@PathVariable("productId") UUID productId, @Validated @RequestBody ProductDTO productDTO) {
        if (productService.updateProductById(productId, productDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

//    /api/v1/admin/products/productId
    @DeleteMapping(ADMIN_PRODUCT_ID)
    public ResponseEntity deleteProductById(@PathVariable("productId") UUID productId) {
        if (productService.deleteProductById(productId).isEmpty()) {
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

    public Boolean deleteItemInCart(UUID cartDetailId) {

        return userService.deleteItemInCart(cartDetailId);

    }

}
