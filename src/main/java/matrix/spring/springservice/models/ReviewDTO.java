package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ReviewDTO {

    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String reviewContent;

    @NotNull
    @Positive
    private Double reviewRating;

    private LocalDateTime isDeleted;
    private UUID userId;
    private UUID productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
