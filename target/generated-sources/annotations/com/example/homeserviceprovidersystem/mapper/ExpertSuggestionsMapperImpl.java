package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.expertsuggestion.ExpertSuggestionsResponse;
import com.example.homeserviceprovidersystem.entity.ExpertSuggestions;
import com.example.homeserviceprovidersystem.entity.Orders;
import com.example.homeserviceprovidersystem.entity.SubDuty;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T19:18:38+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ExpertSuggestionsMapperImpl implements ExpertSuggestionsMapper {

    @Override
    public ExpertSuggestionsResponse expertSuggestionToExpertSuggestionsResponse(ExpertSuggestions expertSuggestions) {
        if ( expertSuggestions == null ) {
            return null;
        }

        ExpertSuggestionsResponse expertSuggestionsResponse = new ExpertSuggestionsResponse();

        expertSuggestionsResponse.setNameSubDuty( expertSuggestionsOrdersSubDutyName( expertSuggestions ) );
        expertSuggestionsResponse.setId( expertSuggestions.getId() );
        expertSuggestionsResponse.setOfferDate( expertSuggestions.getOfferDate() );
        expertSuggestionsResponse.setOfferTime( expertSuggestions.getOfferTime() );
        expertSuggestionsResponse.setProposedPrice( expertSuggestions.getProposedPrice() );
        expertSuggestionsResponse.setDateOfStartWork( expertSuggestions.getDateOfStartWork() );
        expertSuggestionsResponse.setTimeOfStartWork( expertSuggestions.getTimeOfStartWork() );
        expertSuggestionsResponse.setDurationOfWorkPerHour( expertSuggestions.getDurationOfWorkPerHour() );

        return expertSuggestionsResponse;
    }

    private String expertSuggestionsOrdersSubDutyName(ExpertSuggestions expertSuggestions) {
        if ( expertSuggestions == null ) {
            return null;
        }
        Orders orders = expertSuggestions.getOrders();
        if ( orders == null ) {
            return null;
        }
        SubDuty subDuty = orders.getSubDuty();
        if ( subDuty == null ) {
            return null;
        }
        String name = subDuty.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
