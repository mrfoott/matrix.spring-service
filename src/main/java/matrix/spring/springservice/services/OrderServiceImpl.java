package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private Map<UUID, OrderDTO> orderDTOMap;
    private Map<UUID, ShippingDTO> shippingDTOMap;

    public OrderServiceImpl() {
        this.orderDTOMap = new HashMap<>();
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return new ArrayList<>(orderDTOMap.values());
    }

    @Override
    public Optional<OrderDTO> getOrderById(UUID orderId) {
        return Optional.of(orderDTOMap.get(orderId));
    }

    @Override
    public Optional<ShippingDTO> updateShipping(UUID shippingId, ShippingDTO shippingDTO) {
        ShippingDTO existingShipping = shippingDTOMap.get(shippingId);

        existingShipping.setShippingLocation(shippingDTO.getShippingLocation());
        existingShipping.setShippingStatus(shippingDTO.getShippingStatus());

        shippingDTOMap.put(existingShipping.getId(), existingShipping);

        return Optional.of(existingShipping);
    }

    @Override
    public List<OrderDTO> getAllOrdersOfUserByUserId(UUID userId) {
        return new ArrayList<>(orderDTOMap.values());
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailDTOList, ReceiverInfoDTO receiverInfoDTO, List<ProductDTO> productDTOList) {
        OrderDTO newOrder = OrderDTO.builder()
                .totalPrice(orderDTO.getTotalPrice())
                .shippingFee(orderDTO.getShippingFee())
                .paymentMethod(orderDTO.getPaymentMethod())
                .paymentStatus(orderDTO.getPaymentStatus())
                .userId(orderDTO.getUserId())
                .receiverInfoId(orderDTO.getReceiverInfoId())
                .build();

//        for (:
//             ) {
//
//        }

        return null;

    }
}
