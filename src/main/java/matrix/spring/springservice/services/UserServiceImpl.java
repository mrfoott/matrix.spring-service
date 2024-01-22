package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.*;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Optional<UserDTO> getUserById(UUID user_id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> deleteUserById(UUID user_id) {
        return null;
    }

    @Override
    public List<CartDetailDTO> getCartInfo(UUID user_id) {
        return null;
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID user_id, UserDTO userDTO) {
        return null;
    }

    @Override
    public Boolean deleteItemInCart(UUID cartdetail_id) {
        return null;
    }

    @Override
    public Optional<CartDetailDTO> updateItemInCart(UUID cartdetail_id, Integer item_quantity) {

    }

    @Override
    public CartDetailDTO addProductToCart(CartDetailDTO cartDetailDTO) {
        return null;
    }

    @Override
    public ReviewDTO reviewProduct(ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO) {
        return null;
    }
}
