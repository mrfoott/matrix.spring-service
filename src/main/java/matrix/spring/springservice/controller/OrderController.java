package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.OrderDTO;
import matrix.spring.springservice.models.ShippingDTO;
import matrix.spring.springservice.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public OrderDTO getOrderById(UUID orderId) {
        return orderService.getOrderById(orderId).orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public ResponseEntity updateShipping(@PathVariable("shippingId") UUID orderId, @Validated @RequestBody ShippingDTO shippingDTO) {

        if (orderService.updateShipping(orderId, shippingDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


}
