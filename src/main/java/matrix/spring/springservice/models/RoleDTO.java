package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RoleDTO {

    private Integer id;
    private Integer version;

    @NotNull
    @NotBlank
    private String roleName;

    private LocalDateTime isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
