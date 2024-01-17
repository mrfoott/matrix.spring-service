package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Category;
import matrix.spring.springservice.models.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    Category categoryDtoToCategory(CategoryDTO categoryDTO);

    CategoryDTO categoryToCategoryDto(Category category);

}
