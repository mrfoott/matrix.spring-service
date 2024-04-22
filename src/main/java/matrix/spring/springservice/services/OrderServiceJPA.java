package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.*;
import matrix.spring.springservice.mappers.*;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.repositories.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

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

//        AtomicReference<Optional<ShippingDTO>> atomicReference = new AtomicReference<>();
//
//        shippingRepository.findById(shippingId).ifPresentOrElse(existingShipping -> {
//            existingShipping.setShippingLocation(shippingDTO.getShippingLocation());
//            existingShipping.setShippingStatus(shippingDTO.getShippingStatus());
//
//            atomicReference.set(Optional.of(shippingMapper
//                    .shippingToShippingDto(shippingRepository.save(existingShipping))));
//        }, () -> {
//            atomicReference.set(Optional.empty());
//        });
//        return atomicReference.get();

        Shipping shipping = shippingRepository.findById(shippingId).orElse(null);
        shipping.setShippingStatus(shippingDTO.getShippingStatus());
        shipping.setShippingLocation(shippingDTO.getShippingLocation());

        shippingRepository.save(shipping);

        ShippingDTO returnShipping = shippingMapper.shippingToShippingDto(shipping);
        returnShipping.setOrderId(shipping.getOrder().getId());

        return Optional.of(returnShipping);

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
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        ReceiverInfo receiverInfo = receiverInfoRepository.findById(orderDTO.getReceiverInfoId()).orElse(null);
        User user = userRepository.findById(orderDTO.getUserId()).orElse(null);

        Order order = orderMapper.orderDtoToOrder(orderDTO);
        order.setUser(user);
        order.setReceiverInfo(receiverInfo);
        assert user != null;
        order.setDiscountPercentage(user.getMembership().getDiscountPercentage());
        order.setShippingUnit("PN Express");

        LocalDate today = LocalDate.now();
        LocalDate estimatedDayPlus3 = today.plusDays(3);
        int day3 = estimatedDayPlus3.getDayOfMonth();
        int month3 = estimatedDayPlus3.getMonthValue();
        int year3 = estimatedDayPlus3.getYear();

        LocalDate estimatedDayPlus5 = today.plusDays(5);
        int day5 = estimatedDayPlus5.getDayOfMonth();
        int month5 = estimatedDayPlus5.getMonthValue();
        int year5 = estimatedDayPlus5.getYear();

        order.setEstimatedDeliveryDate(day3 + "/" + month3 + "/" + year3 + " - " + day5 + "/" + month5 + "/" + year5);

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
        returnOrder.setShippingId(shipping.getId());

//        BigDecimal currentOrderTotal = calculateOrderTotal(orderDTO);
        BigDecimal currentOrderTotal = orderDTO.getTotalPrice();

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime oneYearAgo = currentDate.minusYears(1);
        BigDecimal totalOrderValueLastYear = getTotalOrderValueLastYear(user, oneYearAgo);
        Membership membership = user.getMembership();

        BigDecimal totalOrderedValue = currentOrderTotal.add(totalOrderValueLastYear);

        if (totalOrderedValue.compareTo(membership.getMinPrice()) >= 0) {
            updateMembership(user, currentDate, totalOrderedValue, currentOrderTotal);
        }


        return returnOrder;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {

        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);

        order.setPaymentStatus(orderDTO.getPaymentStatus());

        orderRepository.save(order);

        OrderDTO returnOrder = orderMapper.orderToOrderDto(order);

        return returnOrder;

    }

    @Override
    public OrderDTO updateBillOfLadingCode(OrderDTO orderDTO) {

        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);

        order.setBillOfLadingCode(orderDTO.getBillOfLadingCode());

        orderRepository.save(order);

        OrderDTO returnOrder = orderMapper.orderToOrderDto(order);

        returnOrder.setShippingId(orderRepository.findById(orderDTO.getId()).orElse(null).getShipping().getId());
        returnOrder.setUserId(orderRepository.findById(orderDTO.getId()).orElse(null).getUser().getId());
        returnOrder.setReceiverInfoId(orderRepository.findById(orderDTO.getId()).orElse(null).getReceiverInfo().getId());

        return returnOrder;

    }

    @Override
    public OrderDTO updateDeliveryDate(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);

//        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setDeliveryDate(orderDTO.getDeliveryDate());

        orderRepository.save(order);

        OrderDTO returnOrder = orderMapper.orderToOrderDto(order);

        returnOrder.setShippingId(orderRepository.findById(orderDTO.getId()).orElse(null).getShipping().getId());
        returnOrder.setUserId(orderRepository.findById(orderDTO.getId()).orElse(null).getUser().getId());
        returnOrder.setReceiverInfoId(orderRepository.findById(orderDTO.getId()).orElse(null).getReceiverInfo().getId());

        return returnOrder;
    }

//    private BigDecimal calculateOrderTotal(OrderDTO orderDTO) {
//        BigDecimal total = BigDecimal.ZERO;
//        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetails()) {
//            BigDecimal price = orderDetailDTO.getPriceAtOrder();
//            int quantity = orderDetailDTO.getOrderQuantity();
//            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
//        }
//        return total;
//    }

    private BigDecimal getTotalOrderValueLastYear(User user, LocalDateTime oneYearAgo) {
        BigDecimal total = BigDecimal.ZERO;
        for (Order order : user.getOrderList()) {
            LocalDateTime orderDate = order.getCreatedAt();
            if (orderDate.isAfter(oneYearAgo)) {
                total = total.add(order.getTotalPrice());
            }
        }
        return total;
    }

    private void updateMembership(User user, LocalDateTime currentDate, BigDecimal totalOrderedValue, BigDecimal currentOrderTotal) {
        List<Membership> memberships = membershipRepository.findAll();
        memberships.sort(Comparator.comparing(Membership::getMinPrice).reversed());

//        BigDecimal totalOrderValue = user.getOrderList().stream()
//                .map(Order::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
        for (Membership membership : memberships) {
            if (totalOrderedValue.compareTo(membership.getMinPrice()) >= 0) {
                user.setMembership(membership);
                user.setMembershipPromotedDay(currentDate);
                user.setMembershipExpiredDay(currentDate.plusYears(1));
                user.setMembershipRank(membership.getMembershipRank());

//                int membershipPoint = currentOrderTotal.divide(BigDecimal.TEN.pow(currentOrderTotal.scale() - 5), RoundingMode.DOWN).intValue();
                BigDecimal reducedNumber = currentOrderTotal.setScale(currentOrderTotal.scale() - 3, BigDecimal.ROUND_DOWN);
                int membershipPoint = (reducedNumber.intValue())/1000;


                user.setMembershipPoint(user.getMembershipPoint() + (int) membershipPoint);
                userRepository.save(user);
                break;
            }
        }
    }

}
