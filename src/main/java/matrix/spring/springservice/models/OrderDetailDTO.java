package matrix.spring.springservice.models;

import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class OrderDetailDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @Positive
    private Integer order_quantity;

    @NotNull
    @Positive
    private BigDecimal price_at_order;

    @NotNull
    @NotBlank
    private String product_name_at_order;

    private UUID order_id;
    private UUID product_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
