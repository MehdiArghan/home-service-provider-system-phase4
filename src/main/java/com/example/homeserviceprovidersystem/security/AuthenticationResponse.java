package com.example.homeserviceprovidersystem.security;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AuthenticationResponse {
    String token;
}
