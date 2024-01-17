package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Role;
import matrix.spring.springservice.models.RoleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    Role roleDtoToRole(RoleDTO roleDTO);

    RoleDTO roleToRoleDto(Role role);

}
