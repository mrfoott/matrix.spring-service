package matrix.spring.springservice.models;

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

    private UUID user_id;

    @NotNull
    @Positive
    private Integer item_quantity;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
