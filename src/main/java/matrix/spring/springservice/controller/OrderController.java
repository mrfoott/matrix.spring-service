package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.OrderDTO;
import matrix.spring.springservice.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final String ORDER_PATH = "/api/v1/order";
    private final String ORDER_ID_PATH = ORDER_PATH + "/{orderId}";

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

}
