package com.example.homeserviceprovidersystem.dto.order;

import com.example.homeserviceprovidersystem.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class OrderSummaryRequestWithOrderStatus {
    @NotBlank(message = "please enter an appropriate nameDuty")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "dutyName must contain only letters")
    String dutyName;
    @NotBlank(message = "please enter an appropriate nameSubDuty")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "subDutyName must contain only letters")
    String subDutyName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please enter the appropriate date")
    LocalDate dateOfWork;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull(message = "Please enter an appropriate time")
    LocalTime TimeOfWord;
    @NotNull(message = "please enter the order status approach")
    OrderStatus orderStatus;
}
