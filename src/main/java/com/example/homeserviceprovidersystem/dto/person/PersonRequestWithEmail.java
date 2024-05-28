package com.example.homeserviceprovidersystem.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class PersonRequestWithEmail {
    @NotNull(message = "email cannot be null")
    @Email(message = "please enter an appropriate Email")
    String personEmail;
}
