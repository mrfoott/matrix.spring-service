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
public class UserDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String user_email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String full_name;

    @NotNull
    @NotBlank
    private String user_phone;

    @NotNull
    @NotBlank
    private String avatar;

    @NotNull
    private Double membership_point;

    private LocalDateTime membership_promoted_day;
    private LocalDateTime membership_expired_day;
    private LocalDateTime is_deleted;
    private UUID role_id;
    private UUID membership_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
