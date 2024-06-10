package com.example.homeserviceprovidersystem.dto.expert;

import com.example.homeserviceprovidersystem.dto.person.PersonSummaryResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ExpertResponse extends PersonSummaryResponse {
}
