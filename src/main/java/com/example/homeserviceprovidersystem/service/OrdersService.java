package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.dto.cardInformation.CardInformationRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.order.OrderRequest;
import com.example.homeserviceprovidersystem.dto.order.OrderSummaryRequest;
import com.example.homeserviceprovidersystem.dto.order.OrderSummaryRequestWithOrderStatus;
import com.example.homeserviceprovidersystem.dto.order.OrdersResponse;
import com.example.homeserviceprovidersystem.dto.person.PersonRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.subduty.SubDutyRequestWithName;
import com.example.homeserviceprovidersystem.entity.Orders;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrdersService {
    OrdersResponse save(OrderRequest request);

    Orders save(Orders orders);

    OrdersResponse selectStartWork(OrderSummaryRequest request);

    OrdersResponse endOfOrder(OrderSummaryRequest request);

    OrdersResponse orderPayment(OrderSummaryRequest request);

    Orders findById(Long id);

    List<OrdersResponse> findAllOrderWaitingForSpecialistSuggestion(SubDutyRequestWithName request);

    List<OrdersResponse> findAllOrderWaitingForSpecialistToWorkPlace(CustomerRequestWithEmail request);

    List<OrdersResponse> findAllDoneOrders(CustomerRequestWithEmail request);

    List<OrdersResponse> findAllStartedOrders(CustomerRequestWithEmail request);

    List<OrdersResponse> findAllPaidOrders(CustomerRequestWithEmail request);

    List<OrdersResponse> findAllOrders(PersonRequestWithEmail request, String personType);

    List<OrdersResponse> findOrdersByDynamicSearch(OrderSummaryRequestWithOrderStatus request);

    String onlinePaymentPortal(String customerEmail, Long orderId, HttpServletRequest request);

    String onlinePayment(String customerEmail, Long orderId, HttpServletRequest request, CardInformationRequest cardInformationRequest);
}
