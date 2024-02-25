package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CartDetailDTO {

    private UUID id;
    private Integer version;

    private UUID userId;
    private UUID productId;
    private String productName;

    @NotNull
    @Positive
    private Integer itemQuantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
