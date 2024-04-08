package matrix.spring.springservice.services;

import matrix.spring.springservice.models.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(UUID userId);

    Optional<UserDTO> getInfoOfSelf(UUID userId);

    UserDTO createUser(UserDTO userDTO);

    Optional<UserDTO> deleteUserById(UUID userId);

    List<CartDetailDTO> getCartInfo(UUID userId);

    Optional<UserDTO> updateUserById(UUID userId, UserDTO userDTO);

    Optional<UserDTO> updateAvatar(UserDTO userDTO);

    Boolean deleteItemInCart(UUID cartDetailId);

    Optional<CartDetailDTO> plusOneItemInCart(UUID cartDetailId);

    Optional<Object> minusOneItemInCart(UUID cartDetailId);

    Optional<UserDTO> addProductToCart(CartDetailDTO cartDetailDTO);

    ReviewDTO reviewProduct(ReviewDTO reviewDTO);

    ReceiverInfoDTO addReceiverInfo(ReceiverInfoDTO receiverInfoDTO);

    List<ReceiverInfoDTO> getAllReceiverInfoOfAUserByUserId(UUID userId);

    List<RoleDTO> getAllRoles();

    RoleDTO getAllUsersByRoleId(Integer roleId);

    MembershipDTO createMembership(MembershipDTO membershipDTO);

    List<MembershipDTO> getAllMemberships();

    MembershipDTO getAllUsersByMembershipId(Integer membershipId);

    Optional<UserDTO> undeleteUserByUserId(UUID userId);

    List<UserDTO> getAllActiveUsers();

}
