package matrix.spring.springservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.OrderDTO;
import matrix.spring.springservice.models.ShippingDTO;
import matrix.spring.springservice.services.OrderService;
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
public class OrderController {

    private final String ORDER_PATH = "/api/v1/orders";
    private final String ORDER_ID_PATH = ORDER_PATH + "/{orderId}";
    private final String SHIPPING_PATH = "/api/v1/shipping";
    private final String SHIPPING_ID_PATH = SHIPPING_PATH + "/{shippingId}";

    private final OrderService orderService;

    @GetMapping(ORDER_PATH)
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(ORDER_ID_PATH)
    public OrderDTO getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(SHIPPING_ID_PATH)
    public Optional<ShippingDTO> getShippingById(@PathVariable("shippingId") UUID shippingId) {

        return orderService.getShippingById(shippingId);

    }

    @PutMapping(SHIPPING_ID_PATH)
    public ResponseEntity<Optional<ShippingDTO>> updateShipping(@PathVariable("shippingId") UUID shippingId, @Validated @RequestBody ShippingDTO shippingDTO) {

        if (orderService.updateShipping(shippingId, shippingDTO).isEmpty()) {
            throw new NotFoundException();
        }

        Optional<ShippingDTO> returnShipping = orderService.getShippingById(shippingId);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity<>(returnShipping, httpHeaders, HttpStatus.OK);

    }


}
