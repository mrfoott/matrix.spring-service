package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.User;
import matrix.spring.springservice.models.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userDtoToUser(UserDTO userDTO);

    UserDTO userToUserDto(User user);

}
