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
public class ProductImage {

    private UUID id;
    private Integer version;
    private String image_link;
    private String image_description;
    private LocalDateTime is_deleted;
    private UUID product_id;

}
