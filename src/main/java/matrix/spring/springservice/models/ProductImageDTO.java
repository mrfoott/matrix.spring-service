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


    @NotBlank
    private String imageLink;

    @NotNull
    @NotBlank
    private String imageDescription;

    private LocalDateTime isDeleted;
    private UUID productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
