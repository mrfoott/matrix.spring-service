package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class CategoryDTO {

    private Integer id;
    private Integer version;

    private String categoryName;

    private LocalDateTime isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ProductDTO> productList;

}
