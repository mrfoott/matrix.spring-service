package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.controllers.NotFoundException;
import matrix.spring.springservice.entities.*;
import matrix.spring.springservice.mappers.*;
import matrix.spring.springservice.models.*;
import matrix.spring.springservice.repositories.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        }

        return nonAdminUsers;

    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
//        return Optional.ofNullable(userMapper.userToUserDto(userRepository.findById(userId)
//                .orElse(null)));

        User user = userRepository.findById(userId).orElse(null);

        UserDTO userDTO = userMapper.userToUserDto(user);

        userDTO.setPassword(null);
        userDTO.setCreatedAt(null);
        userDTO.setUpdatedAt(null);

        return Optional.ofNullable(userDTO);

    }

    @Override
    public Optional<UserDTO> getInfoOfSelf(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);

        UserDTO userDTO = userMapper.userToUserDto(user);

        userDTO.setPassword(null);
        userDTO.setCreatedAt(null);
        userDTO.setUpdatedAt(null);

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

        return itemsInCartOfAUser
                .stream()
                .map(cartDetailMapper::cartDetailToCartDetailDto)
                .collect(Collectors.toList());
    }

    public List<CartDetail> cartDetailByUserId(UUID userId) {
        return cartDetailRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID userId, UserDTO userDTO) {

        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(userId).ifPresentOrElse(existingUser -> {
            existingUser.setUserEmail(userDTO.getUserEmail());
            existingUser.setFullName(userDTO.getFullName());
            existingUser.setUserPhone(userDTO.getUserPhone());
            existingUser.setUpdatedAt(LocalDateTime.now());

            atomicReference.set(Optional.of(userMapper.userToUserDto(userRepository.save(existingUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

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

        AtomicReference<Optional<CartDetailDTO>> atomicReference = new AtomicReference<>();

        cartDetailRepository.findById(cartDetailId).ifPresentOrElse(existingCartItem -> {

            existingCartItem.setItemQuantity(existingCartItem.getItemQuantity() + 1);

            atomicReference.set(Optional.of(cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public Optional<CartDetailDTO> minusOneItemInCart(UUID cartDetailId) {

        Optional<CartDetailDTO> cartDetailDTO = cartDetailRepository.findById(cartDetailId)
                .map(existingCartItem -> {
                    if (existingCartItem.getItemQuantity() > 1) {
                        existingCartItem.setItemQuantity(existingCartItem.getItemQuantity() - 1);
                        return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem));
                    } else {
                        return null;
                    }
                });
        return cartDetailDTO;

    }


    @Override
    public CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO) {

        Optional<CartDetail> existingCartDetail = cartDetailRepository.findByUserIdAndProductId(cartDetailDTO.getUserId(), cartDetailDTO.getProductId());

        if (existingCartDetail.isPresent()) {

            CartDetail cartDetail = existingCartDetail.get();

            cartDetail.setItemQuantity(cartDetail.getItemQuantity() + 1);

            return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(cartDetail));

        } else {
//            return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository
//                    .save(cartDetailMapper.cartDetailDtoToCartDetail(cartDetailDTO)));

            User user = userRepository.findById(cartDetailDTO.getUserId()).orElse(null);
            Product product = productRepository.findById(cartDetailDTO.getProductId()).orElse(null);

            CartDetail cartDetail = cartDetailMapper.cartDetailDtoToCartDetail(cartDetailDTO);
            cartDetail.setProduct(product);
            cartDetail.setUser(user);

            cartDetail = cartDetailRepository.save(cartDetail);

            return cartDetailMapper.cartDetailToCartDetailDto(cartDetail);

        }

    }

    @Override
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO) {

        User user = userRepository.findById(reviewDTO.getUserId()).orElse(null);
        Product product = productRepository.findById(reviewDTO.getProductId()).orElse(null);

        System.out.println(user);
        System.out.println(product);

        Review review = reviewMapper.reviewDtoToReview(reviewDTO);

        review.setProduct(product);
        review.setUser(user);

        review = reviewRepository.save(review);

        List<ReviewImageDTO> reviewImageDTOList = reviewDTO.getReviewImages();
        List<ReviewImage> reviewImages = new ArrayList<>();

        for (ReviewImageDTO reviewImageDTO : reviewImageDTOList) {

            ReviewImage reviewImage = reviewImageMapper.reviewImageDtoToReviewImage(reviewImageDTO);

            reviewImage.setReview(review);
            reviewImages.add(reviewImage);

        }

        reviewImageRepository.saveAll(reviewImages);

        return reviewMapper.reviewToReviewDto(review);

    }

    @Override
    public ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO) {
        return receiverInfoMapper.receiverInfoToReceiverInfoDto(receiverInfoRepository
                .save(receiverInfoMapper.receiverInfoDtoToReceiverInfo(receiverInfoDTO)));
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
    public List<UserDTO> getAllUsersByRoleId(Integer roleId) {
        List<User> allUsersByRoleId;

        if (roleId != null) {
            allUsersByRoleId = listUsersByRoleId(roleId);
        } else {
            return null;
        }

        return allUsersByRoleId
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public MembershipDTO createMembership(MembershipDTO membershipDTO) {
        return membershipMapper.membershipToMembershipDto(membershipRepository
                .save(membershipMapper.membershipDtoToMembership(membershipDTO)));
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        return membershipRepository.findAll()
                .stream()
                .map(membershipMapper::membershipToMembershipDto)
                .collect(Collectors.toList());
    }

    public List<User> listUsersByRoleId(Integer roleId) {
        return userRepository.findAllByRoleId(roleId);
    }
}
