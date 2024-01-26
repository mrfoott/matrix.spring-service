package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final String ORDER_PATH = "/api/v1/order";
    private final String ORDER_ID_PATH = ORDER_PATH + "/{orderId}";

}
