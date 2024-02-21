package matrix.spring.springservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.CartDetailDTO;
import matrix.spring.springservice.models.ReceiverInfoDTO;
import matrix.spring.springservice.models.RoleDTO;
import matrix.spring.springservice.models.UserDTO;
import matrix.spring.springservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final String USERS_PATH = "/api/v1/users";

    private final String USER_ID = USERS_PATH + "/{userId}";

    private final String USERS_RECEIVER = USERS_PATH + "/receivers";
//    private final String USER_RECEIVER_ID = USERS_RECEIVER + "/{receiverId}";

    public Boolean deleteItemInCart(UUID cartDetailId) {

        return userService.deleteItemInCart(cartDetailId);

    }

//    /api/v1/users/receivers
    @PostMapping(USERS_RECEIVER)
    public ResponseEntity addReceiverInfo(@Validated @RequestBody ReceiverInfoDTO receiverInfoDTO) {

        ReceiverInfoDTO newReceiverInfo = userService.addReceiverInfo(receiverInfoDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);

    }


    public ResponseEntity updateUserById(@PathVariable("userId") UUID userId, @RequestBody UserDTO userDTO) {

        if (userService.updateUserById(userId, userDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    public List<CartDetailDTO> getCartInfo(UUID userId) {

        return userService.getCartInfo(userId);

    }

    public Optional<UserDTO> getInfoOfSelf(UUID userId) {

        if (userService.getUserById(userId).isEmpty()) {
            throw new NotFoundException();
        }

        return userService.getUserById(userId);
    }

    public Optional<CartDetailDTO> plusOneItemInCart(UUID cartDetailId) {
        return userService.plusOneItemInCart(cartDetailId);
    }

    public Optional<CartDetailDTO> minusOneItemInCart(UUID cartDetailId) {
        return userService.minusOneItemInCart(cartDetailId);
    }

//    POST MAPPING



}
