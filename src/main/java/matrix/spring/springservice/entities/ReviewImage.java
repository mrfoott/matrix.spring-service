package matrix.spring.springservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {

    private UUID id;
    private Integer version;
    private String user_review_image;
    private LocalDateTime is_deleted;
    private UUID review_id;

}
