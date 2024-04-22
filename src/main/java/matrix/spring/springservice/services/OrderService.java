package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    Optional<OrderDTO> getOrderById(UUID orderId);

    Optional<ShippingDTO> getShippingById(UUID shippingId);

    Optional<ShippingDTO> updateShipping(UUID shippingId, ShippingDTO shippingDTO);

    List<OrderDTO> getAllOrdersOfUserByUserId(UUID userId);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(OrderDTO orderDTO);

    OrderDTO updateBillOfLadingCode(OrderDTO orderDTO);

    OrderDTO updateDeliveryDate(OrderDTO orderDTO);

//    Might not need payment service!!!

}
