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
public class OrderDTO {

    private UUID uuid;
    private Integer version;

    @NotNull
    @Positive
    private BigDecimal total_price;

    @NotNull
    @Positive
    private BigDecimal shipping_fee;

    @NotNull
    @NotBlank
    private String payment_method;

    @NotNull
    @NotBlank
    private String payment_status;

    private UUID user_id;
    private UUID receiver_info_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
