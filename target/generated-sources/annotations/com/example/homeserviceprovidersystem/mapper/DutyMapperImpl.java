package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.duty.DutyRequest;
import com.example.homeserviceprovidersystem.dto.duty.DutyResponse;
import com.example.homeserviceprovidersystem.entity.Duty;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T18:51:42+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class DutyMapperImpl implements DutyMapper {

    @Override
    public Duty dutyRequestToDuty(DutyRequest dutyRequest) {
        if ( dutyRequest == null ) {
            return null;
        }

        Duty duty = new Duty();

        duty.setName( dutyRequest.getName() );

        return duty;
    }

    @Override
    public DutyResponse dutyToDutyResponse(Duty duty) {
        if ( duty == null ) {
            return null;
        }

        DutyResponse dutyResponse = new DutyResponse();

        dutyResponse.setId( duty.getId() );
        dutyResponse.setName( duty.getName() );

        return dutyResponse;
    }
}
