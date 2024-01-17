package matrix.spring.springservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    public static final String USER_PATH = "/api/v1/user";
    public static final String USER_ID_PATH = USER_PATH + "/{userId}";

}
