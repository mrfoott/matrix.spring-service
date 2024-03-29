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

import java.util.*;

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

    //    Admin get top selling products
    private final String ADMIN_TOP_SELLING = ADMIN_PRODUCTS + "/topselling";

    //    Admin get all active products
    private final String ADMIN_ACTIVE_PRODUCTS = ADMIN_PRODUCTS + "/active";

    //    Admin get all product images
    private final String ADMIN_PRODUCT_IMAGE = ADMIN_PRODUCTS + "/images";

    //    Admin get product's info
    private final String ADMIN_PRODUCT_ID = ADMIN_PRODUCTS + "/{productId}";


    //    Admin get all users
    private final String ADMIN_USERS = ADMIN_PATH + "/users";

    //    Admin get all active users
    private final String ADMIN_ACTIVE_USERS = ADMIN_USERS + "/active";

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
    //    Admin get all roles
    private final String ADMIN_ROLES = ADMIN_PATH + "/roles";
    //    Admin get all users by role id
    private final String ADMIN_ROLE_ID = ADMIN_ROLES + "/{roleId}";
    //    Admin get all memberships
    private final String ADMIN_MEMBERSHIPS = ADMIN_PATH + "/memberships";
    //    Admin get all users by membership id
    private final String ADMIN_MEMBERSHIP_ID = ADMIN_MEMBERSHIPS + "/{membershipId}";

//    GET MAPPING

//    /api/v1/admin/products/active
    @GetMapping(ADMIN_ACTIVE_PRODUCTS)
    public List<ProductDTO> getAllActiveProducts() {
        return productService.getAllActiveProducts();
    }

//    /api/v1/admin/users/active
    @GetMapping(ADMIN_ACTIVE_USERS)
    public List<UserDTO> getAllActiveUsers() {
        return userService.getAllActiveUsers();
    }

//    /api/v1/admin/memberships/membershipid
    @GetMapping(ADMIN_MEMBERSHIP_ID)
    public MembershipDTO getAllUsersByMembershipId( @PathVariable("membershipId") Integer membershipId) {

        return userService.getAllUsersByMembershipId(membershipId);

    }

//    /api/v1/admin/products/topselling
    @GetMapping(ADMIN_TOP_SELLING)
    public List<ProductDTO> get10TopSellingProducts() {
        return productService.getTopSellingProducts();
    }

    //    /api/v1/admin/memberships
    @GetMapping(ADMIN_MEMBERSHIPS)
    public List<MembershipDTO> getAllMemberships() {
        return userService.getAllMemberships();
    }

    //    /api/v1/admin/roles
    @GetMapping(ADMIN_ORDERS)
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    //    /api/v1/admin/roles
    @GetMapping(ADMIN_ROLES)
    public List<RoleDTO> getAllRoles() {
        return userService.getAllRoles();
    }

    //    /api/v1/admin/roles/roleId
    @GetMapping(ADMIN_ROLE_ID)
    public RoleDTO getAllUsersByRoleId(@PathVariable("roleId") Integer roleId) {
        return userService.getAllUsersByRoleId(roleId);
    }

    //    /api/v1/admin/users
    @GetMapping(ADMIN_USERS)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(ADMIN_USER_ID)
    public Optional<UserDTO> getUserByid(@PathVariable("userId") UUID userid) {
        return userService.getUserById(userid);
    }

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
    public CategoryDTO getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    //    PATCH MAPPING
    @PatchMapping(ADMIN_USER_ID)
    public ResponseEntity undeleteUserByUserId(@PathVariable("userId") UUID userId) {

        Optional<UserDTO> userDTO = userService.undeleteUserByUserId(userId);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(userDTO, httpHeaders, HttpStatus.OK);

    }

    //    POST MAPPING

    //    /ap1/v1/admin/orders
    @PostMapping(ADMIN_ORDERS)
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {

        OrderDTO newOrder = orderService.createOrder(orderDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(newOrder, httpHeaders, HttpStatus.CREATED);

    }

    //    /api/v1/admin/memberships
    @PostMapping(ADMIN_MEMBERSHIPS)
    public ResponseEntity<MembershipDTO> createMembership(@RequestBody MembershipDTO membershipDTO) {

        MembershipDTO newMembership = userService.createMembership(membershipDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(newMembership, httpHeaders, HttpStatus.CREATED);

    }

    //    /api/v1/admin/users
    @PostMapping(ADMIN_USERS)
    public ResponseEntity createUser(@Validated @RequestBody UserDTO userDTO) {

        UserDTO newUser = userService.createUser(userDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        newUser.setPassword(null);

//        UserDTO returnUser = newUser;

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }

    //    /api/v1/admin/products
    @PostMapping(ADMIN_PRODUCTS)
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO productDTO) {

        ProductDTO newProduct = productService.createProduct(productDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(newProduct, httpHeaders, HttpStatus.CREATED);

    }

//    @PostMapping(ADMIN_PRODUCTS)
//    public ResponseEntity createProduct(HttpServletRequest request) {
//        ProductDTO productDTO = extractProductDTO(request);
//        List<ProductImageDTO> productImageDTOList = extractProductImageDTOList(request);
//
//        productService.createProduct(productDTO, productImageDTOList);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
//    }



//    @PostMapping(ADMIN_PRODUCTS)
//    public ResponseEntity createProduct(@Validated @RequestBody CreateProductRequest productRequest) {
//
//        ProductDTO productDTO = productRequest.getProductDTO();
//        List<ProductImageDTO> productImageDTOList = productRequest.getProductImageDTOList();
//
//        productService.createProduct(productDTO, productImageDTOList);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
//
//    }

//    /api/v1/admin/products/productId
    @PostMapping(ADMIN_PRODUCT_IMAGE)
    public ResponseEntity addProductImage(@Validated @RequestBody ProductImageDTO productImageDTO) {

        ProductImageDTO newProductImage = productService.addProductImage(productImageDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }

    //    /api/v1/admin/categories
    @PostMapping(ADMIN_CATEGORIES)
    public ResponseEntity<CategoryDTO> createCategory(@Validated @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO newCategory = productService.createCategory(categoryDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(newCategory, httpHeaders, HttpStatus.CREATED);

    }

    //    /api/v1/admin/roles
    @PostMapping(ADMIN_ROLES)
    public ResponseEntity<RoleDTO> createRole(@Validated @RequestBody RoleDTO roleDTO) {

        RoleDTO newRole = productService.createRole(roleDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);

    }

//    PUT MAPPING

    //    /api/v1/admin/products/productId
    @PutMapping(ADMIN_PRODUCT_ID)
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable("productId") UUID productId, @Validated @RequestBody ProductDTO productDTO) {
        if (productService.updateProductById(productId, productDTO).isEmpty()) {
            throw new NotFoundException();
        }

        Optional<ProductDTO> updatedProduct = productService.updateProductById(productId, productDTO);

        return new ResponseEntity(updatedProduct, HttpStatus.OK);

    }

//    DELETE MAPPING

    //    /api/v1/admin/products/productId
    @DeleteMapping(ADMIN_PRODUCT_ID)
    public ResponseEntity deleteProductById(@PathVariable("productId") UUID productId) {
        if (productService.deleteProductById(productId).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(ADMIN_PRODUCT_ID)
    public ResponseEntity undeleteProductById(@PathVariable("productId") UUID productId) {
        if (productService.undeleteProductById(productId).isEmpty()) {
            throw new NotFoundException();
        }

        Optional<ProductDTO> productDTO = productService.undeleteProductById(productId);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(productDTO, httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(ADMIN_USER_ID)
    public ResponseEntity deleteUserById(@PathVariable("userId") UUID userId) {
        if (userService.deleteUserById(userId).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>("User is deleted!!!",HttpStatus.NO_CONTENT);
    }

    public List<ReviewDTO> getProductReviews(UUID productId) {
        return productService.getProductReviews(productId);
    }

    public List<CartDetailDTO> getCartInfo(UUID userId) {

        return userService.getCartInfo(userId);

    }

    @PutMapping(ADMIN_USER_ID)
    public ResponseEntity<Optional<UserDTO>> updateUserById(@PathVariable("userId") UUID userId, @Validated @RequestBody UserDTO userDTO) {

        if (userService.updateUserById(userId, userDTO).isEmpty()) {
            throw new NotFoundException();
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        Optional<UserDTO> existingUser = userService.updateUserById(userId, userDTO);

//        Optional<UserDTO> existing = userService

        return new ResponseEntity<>(existingUser, httpHeaders, HttpStatus.OK);

    }

    public Boolean deleteItemInCart(UUID cartDetailId) {

        return userService.deleteItemInCart(cartDetailId);

    }

}
