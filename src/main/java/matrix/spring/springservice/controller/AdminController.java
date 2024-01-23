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

    private static final String ADMIN_PATH = "/api/v1/admin";
    private static final String ADMIN_PATH_ID = ADMIN_PATH + "/{admin_id}";

    private static final String ADMIN_PATH_ALL_PRODUCTS = ADMIN_PATH + "/products";
    private static final String ADMIN_PATH_PRODUCT_ID = ADMIN_PATH_ALL_PRODUCTS + "/{product_id}";
    private static final String ADMIN_PATH_ALL_USERS = ADMIN_PATH + "/users";
    private static final String ADMIN_PATH_USER_ID = ADMIN_PATH_ALL_USERS + "/{user_id}";


    private final UserService userService;
//    private final OrderService orderService;
    private final ProductService productService;

//    GET MAPPING

    @GetMapping(value = ADMIN_PATH)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = ADMIN_PATH)
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

//    POST MAPPING


//    PUT MAPPING

}
