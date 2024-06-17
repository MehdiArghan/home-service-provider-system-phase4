package com.example.homeserviceprovidersystem.dto.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ChangePasswordRequest {
    @NotBlank(message = "please enter an appropriate password")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one letter and one number")
    String previousPassword;
    @NotBlank(message = "please enter an appropriate password")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one letter and one number")
    String newPassword;
}
