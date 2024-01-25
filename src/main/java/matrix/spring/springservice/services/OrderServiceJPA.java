package matrix.spring.springservice.services;

import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.Order;
import matrix.spring.springservice.mappers.OrderMapper;
import matrix.spring.springservice.mappers.ShippingMapper;
import matrix.spring.springservice.mappers.UserMapper;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.repositories.OrderRepository;
import matrix.spring.springservice.repositories.ShippingRepository;
import matrix.spring.springservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class OrderServiceJPA implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShippingRepository shippingRepository;
    private final ShippingMapper shippingMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDTO> getOrderById(UUID orderId) {
        return Optional.ofNullable(orderMapper.orderToOrderDto(orderRepository.findById(orderId)
                .orElse(null)));
    }

    @Override
    public Optional<ShippingDTO> updateShipping(UUID shippingId, ShippingDTO shippingDTO) {

        AtomicReference<Optional<ShippingDTO>> atomicReference = new AtomicReference<>();

        shippingRepository.findById(shippingId).ifPresentOrElse(existingShipping -> {
            existingShipping.setShippingLocation(shippingDTO.getShippingLocation());
            existingShipping.setShippingStatus(shippingDTO.getShippingStatus());

            atomicReference.set(Optional.of(shippingMapper
                    .shippingToShippingDto(shippingRepository.save(existingShipping))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public List<OrderDTO> getAllOrdersOfUserByUserId(UUID userId) {

        List<Order> orderList;

        if (!userId.toString().isEmpty()) {
            orderList = listOrdersOfAUser(userId);
        } else {
            return null;
        }

        return orderList
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    public List<Order> listOrdersOfAUser(UUID userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, OrderDetailDTO orderDetailDTO, ReceiverInfoDTO receiverInfoDTO, List<ProductDTO> productDTOList) {
        return orderMapper.orderToOrderDto(orderRepository.save(orderMapper.orderDtoToOrder(orderDTO)));
    }
}
