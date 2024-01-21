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
public class ReceiverInfoDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String receiver_name;

    @NotNull
    @NotBlank
    private String receiver_address;

    @NotNull
    @NotBlank
    private String receiver_phone;

    @NotNull
    private Integer is_default;

    private LocalDateTime is_deleted;
    private UUID user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
