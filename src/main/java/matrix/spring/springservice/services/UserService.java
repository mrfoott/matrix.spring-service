package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(UUID user_id);

    UserDTO createUser(UserDTO userDTO);

    Optional<UserDTO> deleteUserById(UUID user_id);

    List<CartDetailDTO> getCartInfo(UUID user_id);

    Optional<UserDTO> updateUserById(UUID user_id, UserDTO userDTO);

    Boolean deleteItemInCart(UUID cartdetail_id);

    Optional<CartDetailDTO> updateItemInCart(UUID cartdetail_id, Integer item_quantity);

    CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO);

    ReviewDTO reviewProduct(ReviewDTO reviewDTO);

    ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO);

}
