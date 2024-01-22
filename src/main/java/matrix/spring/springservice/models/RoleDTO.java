package matrix.spring.springservice.models;

import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class RoleDTO {

    private Integer id;
    private Integer version;

    @NotNull
    @NotBlank
    private String role_name;

    private LocalDateTime is_deleted;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
