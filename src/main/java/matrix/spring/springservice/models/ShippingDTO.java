package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ShippingDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String shippingStatus;

    @NotNull
    @NotBlank
    private String shippingLocation;

    private UUID orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
