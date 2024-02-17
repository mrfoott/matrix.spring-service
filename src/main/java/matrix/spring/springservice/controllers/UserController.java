package matrix.spring.springservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matrix.spring.springservice.models.CartDetailDTO;
import matrix.spring.springservice.models.UserDTO;
import matrix.spring.springservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    UserService userService;

    private final String USER_PATH = "/api/v1/user";

    private final String USER_ID = USER_PATH + "/{userId}";

    public Boolean deleteItemInCart(UUID cartDetailId) {

        return userService.deleteItemInCart(cartDetailId);

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

    public Optional<UserDTO> getUserById(UUID userId) {

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

}
