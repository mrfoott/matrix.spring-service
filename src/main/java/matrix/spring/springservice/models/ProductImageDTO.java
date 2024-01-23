package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ProductImageDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String imageLink;

    @NotNull
    @NotBlank
    private String imageDescription;

    private LocalDateTime isDeleted;
    private UUID productId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
