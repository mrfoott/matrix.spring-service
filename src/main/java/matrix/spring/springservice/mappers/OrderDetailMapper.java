package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.OrderDetail;
import matrix.spring.springservice.models.OrderDetailDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDetailMapper {

    OrderDetail orderDetailDtoToOrderDetail(OrderDetailDTO orderDetailDTO);

    OrderDetailDTO orderDetailToOrderDetailDto(OrderDetail orderDetail);

}
