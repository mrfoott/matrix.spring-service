package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Review;
import matrix.spring.springservice.models.ReviewDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ReviewMapper {

    Review reviewDtoToReview(ReviewDTO reviewDTO);

    ReviewDTO reviewToReviewDto(Review review);

}
