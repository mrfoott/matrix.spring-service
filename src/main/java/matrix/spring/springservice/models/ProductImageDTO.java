package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import matrix.spring.springservice.entities.Product;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ProductImageDTO {

    private UUID id;
    private Integer version;

    private String imageLink;

    private String imageDescription;

    private LocalDateTime isDeleted;
//    private UUID productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//
    private ProductDTO productDTO;

}
