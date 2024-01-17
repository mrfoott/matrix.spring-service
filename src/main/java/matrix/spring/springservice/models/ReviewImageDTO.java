package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ReviewImageDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String user_review_image;

    private LocalDateTime is_deleted;
    private UUID review_id;

}
