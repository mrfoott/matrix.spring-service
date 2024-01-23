package matrix.spring.springservice.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ViewHistoryDTO {

    private UUID id;
    private Integer version;
    private Integer view;
    private UUID userId;
    private UUID productId;

}
