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
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

    private final UserService userService;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderDTOList = orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());

        for (OrderDTO orderDTO : orderDTOList) {
            orderDTO.setUserId(orderRepository.findById(orderDTO.getId()).orElse(null).getUser().getId());
            orderDTO.setReceiverInfoId(orderRepository.findById(orderDTO.getId()).orElse(null).getReceiverInfo().getId());
            orderDTO.setShippingId(shippingRepository.findByOrderId(orderDTO.getId()).getId());

            List<OrderDetail> orderDetails = orderRepository.findById(orderDTO.getId())
                    .orElse(null)
                    .getOrderDetails();

            List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetails) {
                OrderDetailDTO orderDetailDTO = orderDetailMapper.orderDetailToOrderDetailDto(orderDetail);
                orderDetailDTO.setProductId(orderDetail.getProduct().getId());
                orderDetailDTO.setOrderId(orderDetail.getOrder().getId());

                List<ProductImage> productImages = orderDetail.getProduct().getProductImages();

                orderDetailDTO.setProductImage(productImages.get(0).getImageLink());

                orderDetailDTOs.add(orderDetailDTO);
            }

            orderDTO.setOrderDetails(orderDetailDTOs);

        }

        return orderDTOList;

    }

    @Override
    public Optional<OrderDTO> getOrderById(UUID orderId) {
        OrderDTO orderDTO = orderMapper.orderToOrderDto(orderRepository.findById(orderId).orElse(null));

        orderDTO.setUserId(orderRepository.findById(orderDTO.getId()).orElse(null).getUser().getId());
        orderDTO.setReceiverInfoId(orderRepository.findById(orderDTO.getId()).orElse(null).getReceiverInfo().getId());
        orderDTO.setShippingId(shippingRepository.findByOrderId(orderDTO.getId()).getId());

        List<OrderDetail> orderDetails = orderRepository.findById(orderDTO.getId())
                .orElse(null)
                .getOrderDetails();

        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailDTO orderDetailDTO = orderDetailMapper.orderDetailToOrderDetailDto(orderDetail);
            orderDetailDTO.setProductId(orderDetail.getProduct().getId());
            orderDetailDTO.setOrderId(orderDetail.getOrder().getId());

            List<ProductImage> productImages = orderDetail.getProduct().getProductImages();

            orderDetailDTO.setProductImage(productImages.get(0).getImageLink());

            orderDetailDTOs.add(orderDetailDTO);
        }

        orderDTO.setOrderDetails(orderDetailDTOs);

        return Optional.of(orderDTO);

    }

    @Override
    public Optional<ShippingDTO> getShippingById(UUID shippingId) {
        Shipping shipping = shippingRepository.findById(shippingId).orElse(null);

        ShippingDTO shippingDTO = shippingMapper.shippingToShippingDto(shipping);

        shippingDTO.setOrderId(shipping.getOrder().getId());

        return Optional.of(shippingDTO);

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

        List<Order> orderList = new ArrayList<>();

        if (!userId.toString().isEmpty()) {
            orderList = listOrdersOfAUser(userId);
        } else {
            return null;
        }

        List<OrderDTO> orderDTOList = orderList.stream().map(orderMapper::orderToOrderDto).toList();

        for (OrderDTO orderDTO : orderDTOList) {
            orderDTO.setUserId(orderRepository.findById(orderDTO.getId()).orElse(null).getUser().getId());
            orderDTO.setReceiverInfoId(orderRepository.findById(orderDTO.getId()).orElse(null).getReceiverInfo().getId());
            orderDTO.setShippingId(shippingRepository.findByOrderId(orderDTO.getId()).getId());

            List<OrderDetail> orderDetails = orderRepository.findById(orderDTO.getId())
                    .orElse(null)
                    .getOrderDetails();

            List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetails) {
                OrderDetailDTO orderDetailDTO = orderDetailMapper.orderDetailToOrderDetailDto(orderDetail);
                orderDetailDTO.setProductId(orderDetail.getProduct().getId());
                orderDetailDTO.setOrderId(orderDetail.getOrder().getId());

                List<ProductImage> productImages = orderDetail.getProduct().getProductImages();

                orderDetailDTO.setProductImage(productImages.get(0).getImageLink());

                orderDetailDTOs.add(orderDetailDTO);
            }

            orderDTO.setOrderDetails(orderDetailDTOs);

        }

        return orderDTOList;
    }

    public List<Order> listOrdersOfAUser(UUID userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        ReceiverInfo receiverInfo = receiverInfoRepository.findById(orderDTO.getReceiverInfoId()).orElse(null);
        User user = userRepository.findById(orderDTO.getUserId()).orElse(null);

        Order order = orderMapper.orderDtoToOrder(orderDTO);
        order.setUser(user);
        order.setReceiverInfo(receiverInfo);
        assert user != null;
        order.setDiscountPercentage(user.getMembership().getDiscountPercentage());

        orderRepository.save(order);

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

                orderDetail.setProduct(product);
                orderDetail.setOrder(order);
                orderDetail.setOrderQuantity(cartDetail.getItemQuantity());
                orderDetail.setPriceAtOrder(cartDetail.getProduct().getPrice());
                orderDetail.setProductNameAtOrder(cartDetail.getProductName());
                orderDetail.setProductImage(product.getProductImages().get(0).getImageLink());

                orderDetails.add(orderDetail);

                product.setProductQuantity(product.getProductQuantity() - cartDetail.getItemQuantity());
                product.setSoldQuantity(product.getSoldQuantity() + cartDetail.getItemQuantity());

                productRepository.save(product);

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
        shipping.setOrder(order);
        shipping.setShippingStatus("Dang xu ly");
        shipping.setShippingLocation("184 Lê Đại Hành - VTC Academy - Booth 4");

        shipping = shippingRepository.save(shipping);

        OrderDTO returnOrder = orderMapper.orderToOrderDto(order);
        returnOrder.setUserId(order.getUser().getId());
        returnOrder.setReceiverInfoId(order.getReceiverInfo().getId());

        return returnOrder;
    }
}
