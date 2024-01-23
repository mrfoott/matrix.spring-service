package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ReceiverInfoDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String receiverName;

    @NotNull
    @NotBlank
    private String receiverAddress;

    @NotNull
    @NotBlank
    private String receiverPhone;

    @NotNull
    private Integer isDefault;

    private LocalDateTime isDeleted;
    private UUID userId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
