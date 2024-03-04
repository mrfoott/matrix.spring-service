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

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        return Optional.ofNullable(userMapper.userToUserDto(userRepository.findById(userId)
                .orElse(null)));
    }

    @Override
    public Optional<UserDTO> getInfoOfSelf(UUID userId) {
        return Optional.empty();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDTO)));
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

        AtomicReference<Optional<CartDetailDTO>> atomicReference = new AtomicReference<>();

        cartDetailRepository.findById(cartDetailId).ifPresentOrElse(existingCartItem -> {

            existingCartItem.setItemQuantity(existingCartItem.getItemQuantity() - 1);

            atomicReference.set(Optional.of(cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }


    @Override
    public CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO) {

        Optional<CartDetail> existingCartDetail = cartDetailRepository.findByUserIdAndProductId(cartDetailDTO.getUserId(), cartDetailDTO.getProductId());

        if (existingCartDetail.isPresent()) {

            CartDetail cartDetail = existingCartDetail.get();

            cartDetail.setItemQuantity(cartDetail.getItemQuantity() + 1);

            return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(cartDetail));

        } else {
            return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository
                    .save(cartDetailMapper.cartDetailDtoToCartDetail(cartDetailDTO)));
        }

    }

    @Override
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO, List<ReviewImageDTO> reviewImageDTOList) {
        Review review = reviewMapper.reviewDtoToReview(reviewDTO);

        Review savedReview = reviewRepository.save(review);

        // Map ReviewImageDTO list to ReviewImage entity list
        List<ReviewImage> reviewImages = reviewImageDTOList
                .stream()
                .map(reviewImageMapper::reviewImageDtoToReviewImage)
                .collect(Collectors.toList());

//        // Set review id for each review image
//        reviewImages.forEach(reviewImage -> reviewImage.setReview(savedReview));

        // Save review images to database
        reviewImageRepository.saveAll(reviewImages);

        // Map saved review back to DTO and return
        return reviewMapper.reviewToReviewDto(savedReview);

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
