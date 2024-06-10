package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.subduty.SubDutyResponse;
import com.example.homeserviceprovidersystem.entity.SubDuty;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T18:51:42+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class SubDutyMapperImpl implements SubDutyMapper {

    @Override
    public SubDutyResponse subDutyToSubDutyResponse(SubDuty subDuty) {
        if ( subDuty == null ) {
            return null;
        }

        SubDutyResponse subDutyResponse = new SubDutyResponse();

        subDutyResponse.setId( subDuty.getId() );
        subDutyResponse.setName( subDuty.getName() );
        subDutyResponse.setBasePrice( subDuty.getBasePrice() );
        subDutyResponse.setDescription( subDuty.getDescription() );
        subDutyResponse.setDuty( subDuty.getDuty() );

        return subDutyResponse;
    }
}
