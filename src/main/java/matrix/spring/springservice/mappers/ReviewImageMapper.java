package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.ReviewImage;
import matrix.spring.springservice.models.ReviewImageDTO;

public interface ReviewImageMapper {

    ReviewImage reviewImageDtoToReviewImage(ReviewImageDTO reviewImageDTO);

    ReviewImageDTO reviewImageToReviewImageDto(ReviewImage reviewImage);

}
