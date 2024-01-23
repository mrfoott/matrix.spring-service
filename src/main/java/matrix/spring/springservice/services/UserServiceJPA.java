package matrix.spring.springservice.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public Optional<UserDTO> getUserById(UUID user_id) {
        return Optional.ofNullable(userMapper.userToUserDto(userRepository.findById(user_id)
                .orElse(null)));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public Optional<UserDTO> deleteUserById(UUID user_id) {
        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(user_id).ifPresentOrElse(existingUser -> {
            existingUser.setIsDeleted(LocalDateTime.now());

            atomicReference.set(Optional.of(userMapper.userToUserDto(userRepository.save(existingUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public List<CartDetailDTO> getCartInfo(UUID user_id) {
        return cartDetailRepository.findAll()
                .stream()
                .map(cartDetailMapper::cartDetailToCartDetailDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID user_id, UserDTO userDTO) {

        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(user_id).ifPresentOrElse(existingUser -> {
            existingUser.setUserEmail(userDTO.getUser_email());
            existingUser.setFullName(userDTO.getFull_name());
            existingUser.setUserPhone(userDTO.getUser_phone());

            atomicReference.set(Optional.of(userMapper.userToUserDto(userRepository.save(existingUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public Boolean deleteItemInCart(UUID cartdetail_id) {
        if (cartDetailRepository.existsById(cartdetail_id)) {
            cartDetailRepository.deleteById(cartdetail_id);
            return true;
        }

        return false;
    }

    @Override
    public Optional<CartDetailDTO> updateItemInCart(UUID cartdetail_id, Integer item_quantity) {

        AtomicReference<Optional<CartDetailDTO>> atomicReference = new AtomicReference<>();

        cartDetailRepository.findById(cartdetail_id).ifPresentOrElse(existingCartDetail -> {
            existingCartDetail.setItemQuantity(existingCartDetail.getItemQuantity() + item_quantity);

            atomicReference.set(Optional.of(cartDetailMapper.cartDetailToCartDetailDto(cartDetailRepository
                    .save(existingCartDetail))));
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
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO) {
        return reviewMapper.reviewToReviewDto(reviewRepository.save(reviewMapper.reviewDtoToReview(reviewDTO)));
    }

    @Override
    public ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO) {
        return receiverInfoMapper.receiverInfoToReceiverInfoDto(receiverInfoRepository
                .save(receiverInfoMapper.receiverInfoDtoToReceiverInfo(receiverInfoDTO)));
    }
}
