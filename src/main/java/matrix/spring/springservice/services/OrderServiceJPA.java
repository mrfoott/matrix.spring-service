package matrix.spring.springservice.services;

import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.*;
import matrix.spring.springservice.mappers.*;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.repositories.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final CartDetailRepository cartDetailRepository;
    private final CartDetailMapper cartDetailMapper;
    private final ReceiverInfoRepository receiverInfoRepository;
    private final ReceiverInfoMapper receiverInfoMapper;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final UserService userService;

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
    public OrderDTO createOrder(OrderDTO orderDTO) {

        ReceiverInfo receiverInfo = receiverInfoRepository.findById(orderDTO.getReceiverInfoId()).orElse(null);

        Order order = orderMapper.orderDtoToOrder(orderDTO);
        order.setReceiverInfo(receiverInfo);

//        order = orderRepository.save(order);

        List<String> cartDetailIdList = orderDTO.getCartDetailIdList();
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<CartDetail> cartDetails = new ArrayList<>();

        for (String cartDetailId : cartDetailIdList) {
            CartDetail cartDetail = cartDetailRepository.findById(UUID.fromString(cartDetailId)).orElse(null);
            cartDetails.add(cartDetail);
        }

        for (CartDetail cartDetail : cartDetails) {
            Product product = cartDetail.getProduct();

            if ((product.getProductQuantity() - cartDetail.getItemQuantity()) >= 0) {
                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setOrderQuantity(cartDetail.getItemQuantity());
                orderDetail.setPriceAtOrder(cartDetail.getProduct().getPrice());
                orderDetail.setProductNameAtOrder(cartDetail.getProductName());

                orderDetails.add(orderDetail);

                product.setProductQuantity(product.getProductQuantity() - cartDetail.getItemQuantity());
                product.setSoldQuantity(product.getSoldQuantity() + cartDetail.getItemQuantity());

                product = productRepository.save(product);

            } else {
                break;
//                return "Not enough in-stock quantity";
            }

        }

        orderDetails = orderDetailRepository.saveAll(orderDetails);

        for (CartDetail cartDetail : cartDetails) {
            userService.deleteItemInCart(cartDetail.getId());
        }


        Shipping shipping = new Shipping();
        shipping.setShippingStatus("Dang xu ly");
        shipping.setShippingLocation("184 Lê Đại Hành - VTC Academy - Booth 4");

        shipping = shippingRepository.save(shipping);

        return orderMapper.orderToOrderDto(order);
    }
}
