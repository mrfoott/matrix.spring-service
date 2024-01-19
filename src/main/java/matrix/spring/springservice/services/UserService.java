package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(UUID user_id);

    Boolean deleteUserById(UUID user_id);

    List<CategoryDTO> getCartInfo();

    Boolean updateUserById(UUID user_id, UserDTO userDTO);

    Boolean deleteItemInCart(UUID product_id);

    CartDetailDTO addProductToCart(ProductDTO productDTO);

    ReviewDTO reviewProduct(ReviewDTO reviewDTO);

    ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO);

}
