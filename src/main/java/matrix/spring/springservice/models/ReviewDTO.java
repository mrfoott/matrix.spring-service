package matrix.spring.springservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import matrix.spring.springservice.entities.Product;
import matrix.spring.springservice.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ReviewDTO {

    private UUID id;
    private Integer version;

    private String reviewContent;

    private Double reviewRating;

    private LocalDateTime isDeleted;
    private UUID userId;
    private UUID productId;
    private String userAvatar;

    private String userFullName;

//    private User user;
//    private Product product;

    private List<ReviewImageDTO> reviewImages;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
