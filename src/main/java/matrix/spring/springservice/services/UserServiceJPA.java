package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.CartDetail;
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
    public Optional<CartDetailDTO> plusOneItemInCart(UUID cartDetailId, CartDetailDTO cartDetailDTO) {

        AtomicReference<Optional<CartDetailDTO>> atomicReference = new AtomicReference<>();

        cartDetailRepository.findById(cartDetailId).ifPresentOrElse(existingCartItem -> {

            existingCartItem.setItemQuantity(cartDetailDTO.getItemQuantity() + 1);

            atomicReference.set(Optional.of(cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public Optional<CartDetailDTO> minusOneItemInCart(UUID cartDetailId, CartDetailDTO cartDetailDTO) {

        AtomicReference<Optional<CartDetailDTO>> atomicReference = new AtomicReference<>();

        cartDetailRepository.findById(cartDetailId).ifPresentOrElse(existingCartItem -> {

            existingCartItem.setItemQuantity(cartDetailDTO.getItemQuantity() - 1);

            atomicReference.set(Optional.of(cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository.save(existingCartItem))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }


    @Override
    public CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO) {
        return cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository
                .save(cartDetailMapper.cartDetailDtoToCartDetail(cartDetailDTO)));
    }

    @Override
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO, List<ReviewDTO> reviewDTOList) {
        return reviewMapper.reviewToReviewDto(reviewRepository.save(reviewMapper.reviewDtoToReview(reviewDTO)));
    }

    @Override
    public ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO) {
        return receiverInfoMapper.receiverInfoToReceiverInfoDto(receiverInfoRepository
                .save(receiverInfoMapper.receiverInfoDtoToReceiverInfo(receiverInfoDTO)));
    }
}
