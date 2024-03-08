package matrix.spring.springservice.services.tasks;

import lombok.RequiredArgsConstructor;
import matrix.spring.springservice.entities.User;
import matrix.spring.springservice.repositories.MembershipRepository;
import matrix.spring.springservice.repositories.OrderRepository;
import matrix.spring.springservice.repositories.UserRepository;
import matrix.spring.springservice.services.OrderService;
import matrix.spring.springservice.services.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MembershipUpdateTask {

    private final UserService userService;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Scheduled
    public void updateMembership() {
        List<User> userList = userRepository.findAll();
    }

}
