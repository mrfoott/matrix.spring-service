package matrix.spring.springservice.services;

import matrix.spring.springservice.entities.Shipping;
import matrix.spring.springservice.models.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    Optional<OrderDTO> getOrderById(UUID orderId);

    void updateShipping(UUID orderId, ShippingDTO shippingDTO);

    List<OrderDTO> getAllOrdersOfUserByUserId(UUID userId);

    OrderDTO createOrder(OrderDTO orderDTO, OrderDetailDTO orderDetailDTO, ReceiverInfoDTO receiverInfoDTO);

//    Might not need payment service!!!

}
