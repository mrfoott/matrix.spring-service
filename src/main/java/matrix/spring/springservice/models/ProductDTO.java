package matrix.spring.springservice.models;

import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ProductDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String product_name;

    @NotNull
    @NotBlank
    private String product_description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer product_quantity;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    @PositiveOrZero
    private Integer sold_quantity;

    private LocalDateTime is_deleted;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
