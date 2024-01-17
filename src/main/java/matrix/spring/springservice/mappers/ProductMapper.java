package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Product;
import matrix.spring.springservice.models.ProductDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product productDtoToProduct(ProductDTO productDTO);

    ProductDTO productToProductDto(Product product);

}
