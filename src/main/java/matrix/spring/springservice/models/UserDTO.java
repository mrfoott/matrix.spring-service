package matrix.spring.springservice.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class UserDTO {

    private UUID id;
    private Integer version;


    private String userEmail;

    private String password;


    private String fullName;

    private String userPhone;

    private String avatar;


    private Double membershipPoint;

    private LocalDateTime membershipPromotedDay;
    private LocalDateTime membershipExpiredDay;
    private LocalDateTime isDeleted;
    private Integer roleId;
    private Integer membershipId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderDTO> orderList;
    private List<ReceiverInfoDTO> receiverInfoList;
    private List<CartDetailDTO> cartDetails;
    private List<ReviewDTO> reviewList;

}
