package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.expert.ExpertSummaryResponse;
import com.example.homeserviceprovidersystem.entity.Expert;
import com.example.homeserviceprovidersystem.entity.SubDuty;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T16:29:56+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ExpertMapperImpl implements ExpertMapper {

    @Override
    public ExpertSummaryResponse expertToExpertSummaryResponse(Expert expert) {
        if ( expert == null ) {
            return null;
        }

        ExpertSummaryResponse expertSummaryResponse = new ExpertSummaryResponse();

        expertSummaryResponse.setId( expert.getId() );
        expertSummaryResponse.setFirstName( expert.getFirstName() );
        expertSummaryResponse.setLastName( expert.getLastName() );
        expertSummaryResponse.setEmail( expert.getEmail() );
        expertSummaryResponse.setRegistrationDate( expert.getRegistrationDate() );
        expertSummaryResponse.setRegistrationTime( expert.getRegistrationTime() );
        expertSummaryResponse.setExpertStatus( expert.getExpertStatus() );
        expertSummaryResponse.setPictureData( expert.getPictureData() );
        expertSummaryResponse.setScore( expert.getScore() );
        Set<SubDuty> set = expert.getSubDuties();
        if ( set != null ) {
            expertSummaryResponse.setSubDuties( new LinkedHashSet<SubDuty>( set ) );
        }

        return expertSummaryResponse;
    }
}
