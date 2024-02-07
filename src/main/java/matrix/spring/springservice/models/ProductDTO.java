package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ProductDTO {

    private Integer id;
    private Integer version;

    @NotNull
    @NotBlank
    private String productName;

    @NotNull
    @NotBlank
    private String productDescription;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer productQuantity;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    @PositiveOrZero
    private Integer soldQuantity;

    private LocalDateTime isDeleted;
    private Integer categoryId;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
