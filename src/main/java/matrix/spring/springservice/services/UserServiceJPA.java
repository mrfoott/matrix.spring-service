package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.*;
import matrix.spring.springservice.mappers.*;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.repositories.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Transactional
@Service
@Primary
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CartDetailRepository cartDetailRepository;
    private final CartDetailMapper cartDetailMapper;
    private final ReceiverInfoRepository receiverInfoRepository;
    private final ReceiverInfoMapper receiverInfoMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewImageMapper reviewImageMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final ShippingRepository shippingRepository;
    private final ShippingMapper shippingMapper;

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> allUsers = userRepository.findAllByRoleId(1);

        List<UserDTO> nonAdminUsers = allUsers.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());

        for (UserDTO userDTO : nonAdminUsers) {
            userDTO.setPassword(null);
            userDTO.setCreatedAt(null);
            userDTO.setUpdatedAt(null);
            userDTO.setMembershipId(userRepository.findById(userDTO.getId()).orElse(null).getMembership().getId());
        }

        return nonAdminUsers;

    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
//        return Optional.ofNullable(userMapper.userToUserDto(userRepository.findById(userId)
//                .orElse(null)));

        User user = userRepository.findById(userId).orElse(null);

        UserDTO userDTO = userMapper.userToUserDto(user);

        List<CartDetail> cartDetails = cartDetailRepository.findAllByUserId(userId);

        List<CartDetailDTO> cartDetailDTOList = cartDetails.stream().map(cartDetailMapper::cartDetailToCartDetailDto).toList();

        for (CartDetailDTO cartDetailDTO : cartDetailDTOList) {

            CartDetail cartDetail = cartDetailRepository.findById(cartDetailDTO.getId()).orElse(null);

            cartDetailDTO.setProductId(cartDetail.getProduct().getId());
            cartDetailDTO.setUserId(cartDetail.getUser().getId());
            cartDetailDTO.setProductQuantity(cartDetail.getProduct().getProductQuantity());

        }

        List<Order> orderList = orderRepository.findAllByUserId(userId);
        List<OrderDTO> orderDTOList = orderList.stream().map(orderMapper::orderToOrderDto).toList();

        for (OrderDTO orderDTO : orderDTOList) {

            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderDTO.getId());
            List<OrderDetailDTO> orderDetailDTOList = orderDetails.stream().map(orderDetailMapper::orderDetailToOrderDetailDto).toList();

            for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
                orderDetailDTO.setOrderId(orderDetailRepository.findById(orderDetailDTO.getId()).orElse(null).getOrder().getId());
                orderDetailDTO.setProductId(orderDetailRepository.findById(orderDetailDTO.getId()).orElse(null).getProduct().getId());
            }

            orderDTO.setOrderDetails(orderDetailDTOList);
            orderDTO.setUserId(orderRepository.findById(orderDTO.getId()).orElse(null).getUser().getId());
            orderDTO.setShippingId(shippingRepository.findByOrderId(orderDTO.getId()).getId());
            orderDTO.setReceiverInfoId(orderRepository.findById(orderDTO.getId()).orElse(null).getReceiverInfo().getId());

        }

        userDTO.setPassword(null);
        userDTO.setCreatedAt(null);
        userDTO.setUpdatedAt(null);
        userDTO.setMembershipId(userRepository.findById(userDTO.getId()).orElse(null).getMembership().getId());
        userDTO.setCartDetails(cartDetailDTOList);
        userDTO.setOrderList(orderDTOList);
        userDTO.setRoleId(userRepository.findById(userDTO.getId()).orElse(null).getRole().getId());

        return Optional.of(userDTO);

    }

    @Override
    public Optional<UserDTO> getInfoOfSelf(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);

        UserDTO userDTO = userMapper.userToUserDto(user);

        userDTO.setPassword(null);
        userDTO.setCreatedAt(null);
        userDTO.setUpdatedAt(null);
        userDTO.setMembershipId(user.getMembership().getId());

        return Optional.ofNullable(userDTO);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
//        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDTO)));

        Membership membership = membershipRepository.findById(userDTO.getMembershipId()).orElse(null);
        Role role = roleRepository.findById(userDTO.getRoleId()).orElse(null);

        User user = userMapper.userDtoToUser(userDTO);

        user.setMembership(membership);
        user.setRole(role);
        user.setMembershipRank(membership.getMembershipRank());

        user = userRepository.save(user);

//        user.setPassword(null);

        return userMapper.userToUserDto(user);

    }

    @Override
    public Optional<UserDTO> deleteUserById(UUID userId) {
        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(userId).ifPresentOrElse(existingUser -> {
            existingUser.setIsDeleted(LocalDateTime.now());

            atomicReference.set(Optional.of(userMapper.userToUserDto(userRepository.save(existingUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public List<CartDetailDTO> getCartInfo(UUID userId) {

        List<CartDetail> itemsInCartOfAUser;

        if (!userId.equals("")) {
            itemsInCartOfAUser = cartDetailByUserId(userId);
        } else {
            return null;
        }

        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();

        for (CartDetail cartDetail : itemsInCartOfAUser) {
            CartDetailDTO cartDetailDTO = cartDetailMapper.cartDetailToCartDetailDto(cartDetail);

            cartDetailDTO.setUserId(cartDetail.getUser().getId());
            cartDetailDTO.setProductId(cartDetail.getProduct().getId());
            cartDetailDTO.setProductQuantity(cartDetail.getProduct().getProductQuantity());

            cartDetailDTOList.add(cartDetailDTO);

        }
        return cartDetailDTOList;

    }

    public List<CartDetail> cartDetailByUserId(UUID userId) {
        return cartDetailRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID userId, UserDTO userDTO) {

//        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();
//
//        userRepository.findById(userId).ifPresentOrElse(existingUser -> {
//            existingUser.setUserEmail(userDTO.getUserEmail());
//            existingUser.setFullName(userDTO.getFullName());
//            existingUser.setUserPhone(userDTO.getUserPhone());
//            existingUser.setUpdatedAt(LocalDateTime.now());
//
//            atomicReference.set(Optional.of(userMapper.userToUserDto(userRepository.save(existingUser))));
//        }, () -> {
//            atomicReference.set(Optional.empty());
//        });
//
//        return atomicReference.get();

        User user = userRepository.findById(userId).orElse(null);

        user.setUserEmail(userDTO.getUserEmail());
        user.setFullName(userDTO.getFullName());
        user.setUserPhone(userDTO.getUserPhone());
        user.setUpdatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        return Optional.ofNullable(userMapper.userToUserDto(user));

    }

    @Override
    public Optional<UserDTO> updateAvatar(UserDTO userDTO) {

        User user = userRepository.findById(userDTO.getId()).orElse(null);

        assert user != null;
        user.setAvatar(userDTO.getAvatar());

        userRepository.save(user);

        UserDTO returnUser = userMapper.userToUserDto(user);

        return Optional.ofNullable(returnUser);

    }


    @Override
    public Boolean deleteItemInCart(UUID cartDetailId) {

        if (cartDetailRepository.existsById(cartDetailId)) {
            cartDetailRepository.deleteById(cartDetailId);
            return true;
        }

        return false;
    }

    @Override
    public Optional<CartDetailDTO> plusOneItemInCart(UUID cartDetailId) {

//        AtomicReference<Optional<CartDetailDTO>> atomicReference = new AtomicReference<>();
//
//        cartDetailRepository.findById(cartDetailId).ifPresentOrElse(existingCartItem -> {
//
//            existingCartItem.setItemQuantity(existingCartItem.getItemQuantity() + 1);
//
//            atomicReference.set(Optional.of(cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem))));
//        }, () -> {
//            atomicReference.set(Optional.empty());
//        });
//
//        return atomicReference.get();

        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElse(null);
        cartDetail.setItemQuantity(cartDetail.getItemQuantity() + 1);
        cartDetailRepository.save(cartDetail);

        return Optional.ofNullable(cartDetailMapper.cartDetailToCartDetailDto(cartDetail));

    }

    @Override
    public Optional<Object> minusOneItemInCart(UUID cartDetailId) {

        Optional<Object> cartDetailDTO = cartDetailRepository.findById(cartDetailId)
                .map(existingCartItem -> {
                    if (existingCartItem.getItemQuantity() > 1) {
                        existingCartItem.setItemQuantity(existingCartItem.getItemQuantity() - 1);
                        return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem));
                    } else {
                        deleteItemInCart(cartDetailId);
                        return "Deleted!";
                    }
                });
        return cartDetailDTO;

    }


    @Override
    public Optional<UserDTO> addProductToCart(CartDetailDTO cartDetailDTO) {

        Optional<CartDetail> existingCartDetail = cartDetailRepository.findByUserIdAndProductId(cartDetailDTO.getUserId(), cartDetailDTO.getProductId());

        if (existingCartDetail.isPresent()) {

            CartDetail cartDetail = existingCartDetail.get();

            cartDetail.setItemQuantity(cartDetail.getItemQuantity() + cartDetailDTO.getItemQuantity());

            cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(cartDetail));

        } else {
//            return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository
//                    .save(cartDetailMapper.cartDetailDtoToCartDetail(cartDetailDTO)));

            User user = userRepository.findById(cartDetailDTO.getUserId()).orElse(null);
            Product product = productRepository.findById(cartDetailDTO.getProductId()).orElse(null);

            List<ProductImage> productImages = productImageRepository.findAllByProductId(cartDetailDTO.getProductId());

            CartDetail cartDetail = cartDetailMapper.cartDetailDtoToCartDetail(cartDetailDTO);
            cartDetail.setProduct(product);
            cartDetail.setUser(user);
            cartDetail.setProductImage(productImages.get(0).getImageLink());
            cartDetail.setProductName(product.getProductName());
            cartDetail.setProductPrice(product.getPrice());
            cartDetail.setItemQuantity(cartDetailDTO.getItemQuantity());

            cartDetail = cartDetailRepository.save(cartDetail);

            cartDetailMapper.cartDetailToCartDetailDto(cartDetail);

        }

        return getUserById(cartDetailDTO.getUserId());

    }

    @Override
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO) {

        User user = userRepository.findById(reviewDTO.getUserId()).orElse(null);
        Product product = productRepository.findById(reviewDTO.getProductId()).orElse(null);

        Review review = reviewMapper.reviewDtoToReview(reviewDTO);

        review.setProduct(product);
        review.setUser(user);

        review = reviewRepository.save(review);

        List<ReviewImageDTO> reviewImageDTOList = reviewDTO.getReviewImages();
        List<ReviewImage> reviewImages = reviewImageDTOList.stream().map(reviewImageMapper::reviewImageDtoToReviewImage).collect(Collectors.toList());

        for (ReviewImage reviewImage : reviewImages) {
            reviewImage.setReview(review);
        }

        reviewImages = reviewImageRepository.saveAll(reviewImages);

        return reviewMapper.reviewToReviewDto(review);

    }

    @Override
    public ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO) {
//        return receiverInfoMapper.receiverInfoToReceiverInfoDto(receiverInfoRepository
//                .save(receiverInfoMapper.receiverInfoDtoToReceiverInfo(receiverInfoDTO)));

        User user = userRepository.findById(receiverInfoDTO.getUserId()).orElse(null);

        ReceiverInfo receiverInfo = receiverInfoMapper.receiverInfoDtoToReceiverInfo(receiverInfoDTO);

        receiverInfo.setUser(user);

        if (receiverInfo.getIsDefault().equals(1)) {

            List<ReceiverInfo> receiverInfoList = receiverInfoRepository.findAllByUserId(receiverInfoDTO.getUserId());

            for (ReceiverInfo exsisting : receiverInfoList) {
                exsisting.setIsDefault(0);
            }

            receiverInfoRepository.saveAll(receiverInfoList);
            receiverInfo = receiverInfoRepository.save(receiverInfo);

            return receiverInfoMapper.receiverInfoToReceiverInfoDto(receiverInfo);

        } else {

            receiverInfo = receiverInfoRepository.save(receiverInfo);

            return receiverInfoMapper.receiverInfoToReceiverInfoDto(receiverInfo);

        }

    }

    @Override
    public List<ReceiverInfoDTO> getAllReceiverInfoOfAUserByUserId(UUID userId) {
        List<ReceiverInfo> allReceiverInfoOfAUser;

        if (userId != null) {
            allReceiverInfoOfAUser = listReceiverInfoByUserId(userId);
        } else {
            return null;
        }

        return allReceiverInfoOfAUser
                .stream()
                .map(receiverInfoMapper::receiverInfoToReceiverInfoDto)
                .collect(Collectors.toList());
    }

    private List<ReceiverInfo> listReceiverInfoByUserId(UUID userId) {
        return receiverInfoRepository.findAllByUserId(userId);
    }


    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getAllUsersByRoleId(Integer roleId) {
//        List<User> allUsersByRoleId;
//
//        if (roleId != null) {
//            allUsersByRoleId = listUsersByRoleId(roleId);
//        } else {
//            return null;
//        }
//
//        return allUsersByRoleId
//                .stream()
//                .map(userMapper::userToUserDto)
//                .collect(Collectors.toList());

        Role role = roleRepository.findById(roleId).orElse(null);
        return roleMapper.roleToRoleDto(role);
    }

    @Override
    public MembershipDTO createMembership(MembershipDTO membershipDTO) {
        return membershipMapper.membershipToMembershipDto(membershipRepository
                .save(membershipMapper.membershipDtoToMembership(membershipDTO)));
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        List<Membership> membershipList = membershipRepository.findAll();

        List<MembershipDTO> membershipDTOList = membershipList.stream()
                .map(membershipMapper::membershipToMembershipDto)
                .collect(Collectors.toList());

        return membershipDTOList;

    }

    @Override
    public MembershipDTO getAllUsersByMembershipId(Integer membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElse(null);

        return membershipMapper.membershipToMembershipDto(membership);

    }

    @Override
    public Optional<UserDTO> undeleteUserByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        user.setIsDeleted(null);
        user = userRepository.save(user);

        UserDTO userDTO = userMapper.userToUserDto(user);
        userDTO.setPassword(null);

        return Optional.of(userDTO);

    }

    @Override
    public List<UserDTO> getAllActiveUsers() {

        List<User> userList = userRepository.findAllByIsDeletedNull();

        List<UserDTO> userDTOList = userList.stream().map(userMapper::userToUserDto).collect(Collectors.toList());

        for (UserDTO userDTO : userDTOList) {
            userDTO.setPassword(null);
            userDTO.setMembershipId(userRepository.findById(userDTO.getId()).orElse(null).getMembership().getId());
        }

        return userDTOList;

    }

    public List<User> listUsersByRoleId(Integer roleId) {
        return userRepository.findAllByRoleId(roleId);
    }
}
