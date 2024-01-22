package matrix.spring.springservice.services;

import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return Optional.of(userDTOMap.get(user_id));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        UserDTO newUser = UserDTO.builder()
                .id(userDTO.getId())
                .user_email(userDTO.getUser_email())
                .password(userDTO.getPassword())
                .full_name(userDTO.getFull_name())
                .avatar(userDTO.getAvatar())
                .membership_point(0.0)
                .role_id(0)
                .membership_id(0)
                .membership_point(0.0)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now()).build();

        userDTOMap.put(newUser.getId(), newUser);

        return newUser;
    }

    @Override
    public Optional<UserDTO> deleteUserById(UUID user_id) {

        UserDTO existingUser = userDTOMap.get(user_id);

        existingUser.setIs_deleted(LocalDateTime.now());

        userDTOMap.put(existingUser.getId(), existingUser);

        return Optional.empty();

    }

    @Override
    public List<CartDetailDTO> getCartInfo(UUID user_id) {
        return new ArrayList<>(cartDetailDTOMap.values());
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
        return null;
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
