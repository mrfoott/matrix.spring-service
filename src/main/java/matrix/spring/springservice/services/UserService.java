package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(UUID userId);

    UserDTO createUser(UserDTO userDTO);

    Optional<UserDTO> deleteUserById(UUID userId);

    List<CartDetailDTO> getCartInfo(UUID userId);

    Optional<UserDTO> updateUserById(UUID userId, UserDTO userDTO);

    Boolean deleteItemInCart(UUID cartdetail_id);

    Optional<CartDetailDTO> updateItemInCart(UUID cartdetail_id, Integer itemQuantity);

    CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO);

    ReviewDTO reviewProduct(ReviewDTO reviewDTO);

    ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO);

}
