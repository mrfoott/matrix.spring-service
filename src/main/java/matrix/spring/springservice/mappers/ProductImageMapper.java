package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.ProductImage;
import matrix.spring.springservice.models.ProductImageDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProductImageMapper {

    ProductImage productImageDtoToProductImage(ProductImageDTO productImageDTO);

    ProductImageDTO productImageToProductImageDto(ProductImage productImage);

}
