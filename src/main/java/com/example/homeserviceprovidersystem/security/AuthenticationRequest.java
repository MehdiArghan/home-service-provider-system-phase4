package com.example.homeserviceprovidersystem.security;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "Please enter the username correctly")
    String username;
    @NotBlank(message = "please enter the password correctly")
    String password;
}