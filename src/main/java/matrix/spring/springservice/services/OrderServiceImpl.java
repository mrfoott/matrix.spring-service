package matrix.spring.springservice.services;

import matrix.spring.springservice.models.OrderDTO;
import matrix.spring.springservice.models.OrderDetailDTO;
import matrix.spring.springservice.models.ReceiverInfoDTO;
import matrix.spring.springservice.models.ShippingDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderDTO> getAllOrders() {
        return null;
    }

    @Override
    public Optional<OrderDTO> getOrderById(UUID orderId) {
        return Optional.empty();
    }

    @Override
    public void updateShipping(UUID orderId, ShippingDTO shippingDTO) {

    }

    @Override
    public List<OrderDTO> getAllOrdersOfUserByUserId(UUID userId) {
        return null;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, OrderDetailDTO orderDetailDTO, ReceiverInfoDTO receiverInfoDTO) {
        return null;
    }
}
