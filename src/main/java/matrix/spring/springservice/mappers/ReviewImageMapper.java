package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.ReviewImage;
import matrix.spring.springservice.models.ReviewImageDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ReviewImageMapper {

    ReviewImage reviewImageDtoToReviewImage(ReviewImageDTO reviewImageDTO);

    ReviewImageDTO reviewImageToReviewImageDto(ReviewImage reviewImage);

}
