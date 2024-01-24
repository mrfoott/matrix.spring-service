package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.ProductDTO;
import matrix.spring.springservice.models.UserDTO;
import matrix.spring.springservice.services.OrderService;
import matrix.spring.springservice.services.ProductService;
import matrix.spring.springservice.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController {

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


//    POST MAPPING


//    PUT MAPPING

}
