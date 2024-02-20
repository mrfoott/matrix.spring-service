package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.entities.ReviewImage;
import matrix.spring.springservice.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private Map<UUID, UserDTO> userDTOMap;

    private Map<UUID, CartDetailDTO> cartDetailDTOMap;

    public UserServiceImpl() {
        this.userDTOMap = new HashMap<>();
        this.cartDetailDTOMap = new HashMap<>();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return new ArrayList<>(userDTOMap.values());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        return Optional.of(userDTOMap.get(userId));
    }

    @Override
    public Optional<UserDTO> getInfoOfSelf(UUID userId) {
        return Optional.of(userDTOMap.get(userId));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        UserDTO newUser = UserDTO.builder()
                .id(userDTO.getId())
                .userEmail(userDTO.getUserEmail())
                .userPhone(userDTO.getUserPhone())
                .password(userDTO.getPassword())
                .fullName(userDTO.getFullName())
                .avatar(userDTO.getAvatar())
                .membershipPoint(0.0)
                .roleId(0)
                .membershipId(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        userDTOMap.put(newUser.getId(), newUser);

        return newUser;
    }

    @Override
    public Optional<UserDTO> deleteUserById(UUID userId) {

        UserDTO existingUser = userDTOMap.get(userId);

        existingUser.setIsDeleted(LocalDateTime.now());

        userDTOMap.put(existingUser.getId(), existingUser);

        return Optional.empty();

    }

    @Override
    public List<CartDetailDTO> getCartInfo(UUID userId) {
        return new ArrayList<>(cartDetailDTOMap.values());
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public Boolean deleteItemInCart(UUID cartDetailId) {

        cartDetailDTOMap.remove(cartDetailId);
        return true;

    }

    @Override
    public Optional<CartDetailDTO> plusOneItemInCart(UUID cartDetailId) {
        CartDetailDTO exsisting = cartDetailDTOMap.get(cartDetailId);

        exsisting.setItemQuantity(exsisting.getItemQuantity() + 1);

        cartDetailDTOMap.put(cartDetailId, exsisting);

        return Optional.of(exsisting);

    }

    @Override
    public Optional<CartDetailDTO> minusOneItemInCart(UUID cartDetailId) {

        CartDetailDTO exsisting = cartDetailDTOMap.get(cartDetailId);

        exsisting.setItemQuantity(exsisting.getItemQuantity() + 1);

        cartDetailDTOMap.put(cartDetailId, exsisting);

        return Optional.of(exsisting);

    }


    @Override
    public CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO) {

        CartDetailDTO newCartDetail = CartDetailDTO.builder()
                .userId(cartDetailDTO.getUserId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .itemQuantity(1)
                .build();

        cartDetailDTOMap.put(newCartDetail.getId(), newCartDetail);

        return newCartDetail;

    }

    @Override
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO, List<ReviewImageDTO> reviewImageDTOList) {
        ReviewDTO newReview = ReviewDTO.builder()
                .productId(reviewDTO.getProductId())
                .reviewContent(reviewDTO.getReviewContent())
                .reviewRating(reviewDTO.getReviewRating())
                .userId(reviewDTO.getUserId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<ReviewImageDTO> newReviewImages = reviewImageDTOList.stream()
                .map(reviewImageDTO -> {
                    ReviewImageDTO newReviewImage = ReviewImageDTO.builder()
                            .reviewId(newReview.getId())
                            .userReviewImage(reviewImageDTO.getUserReviewImage())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return newReviewImage;
                }).collect(Collectors.toList());

        return null;

    }

    @Override
    public ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO) {
        return null;
    }
}
