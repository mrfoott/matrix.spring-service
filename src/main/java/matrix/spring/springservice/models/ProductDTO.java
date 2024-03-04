package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import matrix.spring.springservice.entities.Category;
import matrix.spring.springservice.entities.ProductImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ProductDTO {

    private UUID id;
    private Integer version;

    private String productName;
    private String productDescription;
    private BigDecimal price;
    private Integer productQuantity;
    private String brand;

    private Integer soldQuantity;

    private LocalDateTime isDeleted;
    private Integer categoryId;
    private Category category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    List<ProductImageDTO> productImages;

}
