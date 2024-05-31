package com.example.homeserviceprovidersystem.dto.order;

import com.example.homeserviceprovidersystem.entity.enums.OrderStatus;
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
    String dutyName;
    String subDutyName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfWork;
    @DateTimeFormat(pattern = "HH:mm:ss")
    LocalTime timeOfWork;
    OrderStatus orderStatus;
}
