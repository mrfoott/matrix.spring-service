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
public class UserController {

    private final UserService userService;

    private final OrderService orderService;

    private final ProductService productService;

    private final String USERS_PATH = "/api/v1/users";

    private final String USER_CART = USERS_PATH + "/carts";

    private final String USER_CART_PLUS_ONE = USER_CART + "/{userId}" + "/plusone" + "/{cartDetailId}";

    private final String USER_CART_MINUS_ONE = USER_CART + "/{userId}" + "/minusone" + "/{cartDetailId}";


    private final String USER_CART_ID = USER_CART + "/{userId}";

    private final String USER_PRODUCTS = USERS_PATH + "/products";

    private final String USER_TOP_SELLING = USER_PRODUCTS + "/topselling";

    private final String USER_RANDOM = USER_PRODUCTS + "/random";

    private final String USER_ID = USERS_PATH + "/{userId}";

    private final String USERS_RECEIVER = USERS_PATH + "/receivers";

    private final String USER_RECEIVER_INFO = USERS_RECEIVER + "/{userId}";

//    private final String USER_RECEIVER_ID = USERS_RECEIVER + "/{receiverId}";

    private final String USER_REVIEWS = USERS_PATH + "/reviews";

    @DeleteMapping(USER_CART_ID)
    public Boolean deleteItemInCart(@PathVariable("cartDetailId") UUID cartDetailId) {

        return userService.deleteItemInCart(cartDetailId);

    }

//    /api/v1/users/products/random
    @GetMapping(USER_RANDOM)
    public List<ProductDTO> getRandomProducts() {
        return productService.getRandomProducts();
    }

//    /api/v1/users/products/topselling
    @GetMapping(USER_TOP_SELLING)
    public List<ProductDTO> get10TopSellingProducts() {
        return productService.getTopSellingProducts();
    }

//    /api/v1/users/receivers/userId
    @GetMapping(USER_RECEIVER_INFO)
    public List<ReceiverInfoDTO> getAllReceiverInfoOfAUserByUserId(@PathVariable("userId") UUID userId) {

        return userService.getAllReceiverInfoOfAUserByUserId(userId);

    }

    @GetMapping
    public List<OrderDTO> getAllOrdersOfUserByUserId (UUID userId) {

        return orderService.getAllOrdersOfUserByUserId(userId);

    }


//    /api/v1/users/receivers
    @PostMapping(USERS_RECEIVER)
    public ResponseEntity<ReceiverInfoDTO> addReceiverInfo(@Validated @RequestBody ReceiverInfoDTO receiverInfoDTO) {

        ReceiverInfoDTO newReceiverInfo = userService.addReceiverInfo(receiverInfoDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(newReceiverInfo, httpHeaders, HttpStatus.CREATED);

    }

//    /api/v1/users/reviews
    @PostMapping(USER_REVIEWS)
    public ResponseEntity reviewProduct(ReviewDTO reviewDTO) {

        ReviewDTO newReview = userService.reviewProduct(reviewDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(newReview, httpHeaders, HttpStatus.CREATED);

    }

    public ResponseEntity updateUserById(@PathVariable("userId") UUID userId, @RequestBody UserDTO userDTO) {

        if (userService.updateUserById(userId, userDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

//    /api/v1/users/carts/userId
    @GetMapping(USER_CART_ID)
    public List<CartDetailDTO> getCartInfo(@PathVariable("userId") UUID userId) {

        return userService.getCartInfo(userId);

    }


    @GetMapping(USER_ID)
    public Optional<UserDTO> getInfoOfSelf(@PathVariable("userId") UUID userId) {

        if (userService.getInfoOfSelf(userId).isEmpty()) {
            throw new NotFoundException();
        }

        return userService.getInfoOfSelf(userId);
    }

//    /api/v1/users/carts/userId/plusone/cartdetailid
    @PutMapping(USER_CART_PLUS_ONE)
    public ResponseEntity plusOneItemInCart(@PathVariable("userId") UUID userId, @PathVariable("cartDetailId") @RequestBody UUID cartDetailId) {

        Optional<CartDetailDTO> cartDetailDTO = userService.plusOneItemInCart(cartDetailId);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(cartDetailDTO, httpHeaders, HttpStatus.OK);

    }

    @PutMapping(USER_CART_MINUS_ONE)
    public ResponseEntity minusOneItemInCart(@PathVariable("userId") UUID userId, @PathVariable("cartDetailId") @RequestBody UUID cartDetailId) {

        Optional<CartDetailDTO> cartDetailDTO = userService.minusOneItemInCart(cartDetailId);

        if (cartDetailDTO.isPresent()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            return new ResponseEntity(cartDetailDTO.get(), httpHeaders, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Quantity cannot be less than 1");
        }

    }

//    POST MAPPING

    @PostMapping(USER_CART)
    public ResponseEntity addProductToCart(@RequestBody CartDetailDTO cartDetailDTO) {

        CartDetailDTO newCartDetail = userService.addProductToCart(cartDetailDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(newCartDetail, httpHeaders, HttpStatus.CREATED);

    }


}
