package matrix.spring.springservice.mappers;

import matrix.spring.springservice.entities.Membership;
import matrix.spring.springservice.models.MembershipDTO;
import org.mapstruct.Mapper;

@Mapper
public interface MembershipMapper {

    Membership membershipDtoToMembership(MembershipDTO membershipDTO);

    MembershipDTO membershipToMembershipDto(Membership membership);

}
