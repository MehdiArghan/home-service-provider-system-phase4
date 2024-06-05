package com.example.homeserviceprovidersystem.dto.person;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class PersonRequestWithRegistrationTime {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate registrationDate;
    @DateTimeFormat(pattern = "HH:mm:ss")
    LocalTime registrationTime;
    @Pattern(regexp = "^$|^[+]?\\d+$", message = "Please enter the correct numberOfRegisteredOrders")
    String numberOfRegisteredOrders;
    @Pattern(regexp = "^$|^[+]?\\d+$", message = "Please enter the correct numberOfOrdersDone")
    String numberOfOrdersDone;
}
