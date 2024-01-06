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
public class Review {

    private UUID id;
    private Integer version;
    private String review_content;
    private Double review_rating;
    private LocalDateTime is_deleted;
    private UUID user_id;
    private UUID product_id;

}
