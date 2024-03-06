package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class RoleDTO {

    private Integer id;
    private Integer version;

    private String roleName;

    private LocalDateTime isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<UserDTO> userList;

}
