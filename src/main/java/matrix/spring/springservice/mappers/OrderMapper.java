package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Order;
import matrix.spring.springservice.models.OrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    Order orderDtoToOrder(OrderDTO orderDTO);

    OrderDTO orderToOrderDto(Order order);

}
