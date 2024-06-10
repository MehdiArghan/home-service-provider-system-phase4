package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.expert.ExpertResponse;
import com.example.homeserviceprovidersystem.dto.expert.ExpertSummaryResponse;
import com.example.homeserviceprovidersystem.entity.Expert;
import com.example.homeserviceprovidersystem.entity.SubDuty;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T19:18:38+0330",
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

    @Override
    public ExpertResponse expertToExpertResponse(Expert expert) {
        if ( expert == null ) {
            return null;
        }

        ExpertResponse expertResponse = new ExpertResponse();

        expertResponse.setId( expert.getId() );
        expertResponse.setFirstName( expert.getFirstName() );
        expertResponse.setLastName( expert.getLastName() );
        expertResponse.setEmail( expert.getEmail() );
        expertResponse.setRegistrationDate( expert.getRegistrationDate() );
        expertResponse.setRegistrationTime( expert.getRegistrationTime() );

        return expertResponse;
    }
}
