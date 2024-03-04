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

    private final String USER_PRODUCTS = USERS_PATH + "/products";

    private final String USER_TOP_SELLING = USER_PRODUCTS + "/topselling";

    private final String USER_RANDOM = USER_PRODUCTS + "/random";

    private final String USER_ID = USERS_PATH + "/{userId}";

    private final String USER_CART = USERS_PATH + "/carts";

    private final String USERS_RECEIVER = USERS_PATH + "/receivers";

    private final String USER_RECEIVER_INFO = USERS_RECEIVER + "/{userId}";

//    private final String USER_RECEIVER_ID = USERS_RECEIVER + "/{receiverId}";

    public Boolean deleteItemInCart(UUID cartDetailId) {

        return userService.deleteItemInCart(cartDetailId);

    }

//    /api/v1/users/products/random
    @GetMapping(USER_RANDOM)
    public List<ProductDTO> getRandomProducts() {
        return productService.getRandomProducts();
    }

//    /api/v1/users/products/topselling
    @GetMapping(USER_TOP_SELLING)
    public HashMap<String, ArrayList> get10TopSellingProducts() {
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
    public ResponseEntity addReceiverInfo(@Validated @RequestBody ReceiverInfoDTO receiverInfoDTO) {

        ReceiverInfoDTO newReceiverInfo = userService.addReceiverInfo(receiverInfoDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }


    public ResponseEntity updateUserById(@PathVariable("userId") UUID userId, @RequestBody UserDTO userDTO) {

        if (userService.updateUserById(userId, userDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    public List<CartDetailDTO> getCartInfo(UUID userId) {

        return userService.getCartInfo(userId);

    }

    public Optional<UserDTO> getInfoOfSelf(UUID userId) {

        if (userService.getUserById(userId).isEmpty()) {
            throw new NotFoundException();
        }

        return userService.getUserById(userId);
    }

    public Optional<CartDetailDTO> plusOneItemInCart(UUID cartDetailId) {
        return userService.plusOneItemInCart(cartDetailId);
    }

    public Optional<CartDetailDTO> minusOneItemInCart(UUID cartDetailId) {
        return userService.minusOneItemInCart(cartDetailId);
    }

//    POST MAPPING

    public ResponseEntity addProductToCart(@RequestBody CartDetailDTO cartDetailDTO) {

        CartDetailDTO newCartDetail = userService.addProductToCart(cartDetailDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }


}
