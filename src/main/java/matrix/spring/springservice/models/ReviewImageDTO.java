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
    private String userReviewImage;

    private LocalDateTime isDeleted;
    private UUID reviewId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
