package com.example.homeserviceprovidersystem.service.impl;

import com.example.homeserviceprovidersystem.customeException.CustomBadRequestException;
import com.example.homeserviceprovidersystem.customeException.CustomEntityNotFoundException;
import com.example.homeserviceprovidersystem.customeException.CustomResourceNotFoundException;
import com.example.homeserviceprovidersystem.dto.address.AddressRequest;
import com.example.homeserviceprovidersystem.dto.cardInformation.CardInformationRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.order.OrderRequest;
import com.example.homeserviceprovidersystem.dto.order.OrderSummaryRequest;
import com.example.homeserviceprovidersystem.dto.order.OrderSummaryRequestWithOrderStatus;
import com.example.homeserviceprovidersystem.dto.order.OrdersResponse;
import com.example.homeserviceprovidersystem.dto.person.PersonRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.subduty.SubDutyRequestWithName;
import com.example.homeserviceprovidersystem.entity.*;
import com.example.homeserviceprovidersystem.entity.enums.ExpertStatus;
import com.example.homeserviceprovidersystem.entity.enums.OrderStatus;
import com.example.homeserviceprovidersystem.mapper.OrdersMapper;
import com.example.homeserviceprovidersystem.repositroy.OrdersRepository;
import com.example.homeserviceprovidersystem.service.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.homeserviceprovidersystem.entity.enums.OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final CustomerService customerService;
    private final SubDutyService subDutyService;
    private final ExpertSuggestionsService expertSuggestionsService;
    private final WalletService walletService;
    private final ExpertService expertService;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final Validator validator;

    @Autowired
    public OrdersServiceImpl(
            OrdersRepository ordersRepository,
            CustomerService customerService,
            SubDutyService subDutyService,
            WalletService walletService, OrdersMapper ordersMapper,
            @Lazy ExpertSuggestionsService expertSuggestionsService,
            ExpertService expertService,
            Validator validator) {
        this.ordersRepository = ordersRepository;
        this.customerService = customerService;
        this.subDutyService = subDutyService;
        this.walletService = walletService;
        this.ordersMapper = ordersMapper;
        this.expertSuggestionsService = expertSuggestionsService;
        this.expertService = expertService;
        this.validator = validator;
    }

    @Override
    public OrdersResponse save(OrderRequest request) {
        Customer customer = customerService.findByEmail(request.getCustomerEmail());
        SubDuty subDuty = subDutyService.findByName(request.getSubDutyName());
        validateOrderRequest(subDuty, request);
        Address address = createAddress(request.getAddress());
        Orders orders = createOrders(request, customer, subDuty, address);
        return ordersMapper.orderToOrdersResponse(ordersRepository.save(orders));
    }

    private void validateOrderRequest(SubDuty subDuty, OrderRequest request) {
        if (subDuty.getBasePrice() > request.getProposedPrice()) {
            throw new CustomBadRequestException("Proposed price must be greater than or equal to the base price of the subDuty");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderTime = LocalDateTime.of(request.getDateOfWork(), request.getTimeOfWork());
        if (orderTime.isBefore(now)) {
            throw new CustomBadRequestException("The start date and time must be at a date and time after now");
        }
    }


    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    private Address createAddress(AddressRequest addressRequest) {
        return new Address(
                addressRequest.getProvince(),
                addressRequest.getCity(),
                addressRequest.getStreet(),
                addressRequest.getPostalCode()
        );
    }

    private Orders createOrders(OrderRequest request, Customer customer, SubDuty subDuty, Address address) {
        Orders orders = new Orders();
        orders.setProposedPrice(request.getProposedPrice());
        orders.setJobDescription(request.getJobDescription());
        orders.setDateOfWork(request.getDateOfWork());
        orders.setTimeOfWork(request.getTimeOfWork());
        orders.setAddress(address);
        orders.setCustomer(customer);
        orders.setSubDuty(subDuty);
        orders.setExpert(null);
        orders.setOrderStatus(ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
        return orders;
    }

    @Override
    public OrdersResponse selectStartWork(OrderSummaryRequest request) {
        Orders order = findOrderByOrderInformation(request, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);
        validateOrder(order);
        order.setOrderStatus(OrderStatus.ORDER_STARTED);
        return ordersMapper.orderToOrdersResponse(ordersRepository.save(order));
    }

    private Orders findOrderByOrderInformation(OrderSummaryRequest request, OrderStatus orderStatus) {
        return ordersRepository.findByOrderInformation(request.getSubDutyName(), request.getCustomerEmail(), request.getProposedPrice()
                , request.getJobDescription(), request.getDateOfWork(), request.getTimeOfWork(), request.getAddress().getProvince(),
                request.getAddress().getCity(), request.getAddress().getStreet(), request.getAddress().getPostalCode(),
                orderStatus).orElseThrow(() -> new CustomEntityNotFoundException("no order was found"));
    }

    private void validateOrder(Orders orders) {
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime timeOrder = LocalDateTime.of(orders.getDateOfWork(), orders.getTimeOfWork());
        if (timeNow.isBefore(timeOrder))
            throw new CustomBadRequestException("The start date and time must be on or after the order date and time");
    }

    @Override
    public OrdersResponse endOfOrder(OrderSummaryRequest request) {
        Orders order = findOrderByOrderInformation(request, OrderStatus.ORDER_STARTED);
        Expert expert = order.getExpert();
        ExpertSuggestions expertSuggestions = expertSuggestionsService.findByExpertEmailAndOrderId(expert.getEmail(), order.getId());
        LocalDateTime suggestionTimeOfStartWork = LocalDateTime.of(expertSuggestions.getDateOfStartWork(), expertSuggestions.getTimeOfStartWork());
        LocalDateTime suggestionTimeOfEndWork = suggestionTimeOfStartWork.plusHours(expertSuggestions.getDurationOfWorkPerHour());
        LocalDateTime timeNow = LocalDateTime.now();
        if (timeNow.isAfter(suggestionTimeOfEndWork)) {
            int delayedWorkingTime = (int) Duration.between(suggestionTimeOfEndWork, timeNow).toHours();
            int expertScore = expert.getScore();
            int finalScore = expertScore - delayedWorkingTime;
            expert.setScore(finalScore);
            if (finalScore < 0) expert.setExpertStatus(ExpertStatus.DISABLE);
            expertService.save(expert);
        }
        order.setOrderStatus(OrderStatus.ORDER_DONE);
        Orders saveOrder = ordersRepository.save(order);
        return ordersMapper.orderToOrdersResponse(saveOrder);
    }

    @Override
    public OrdersResponse orderPayment(OrderSummaryRequest request) {
        Orders order = findOrderByOrderInformation(request, OrderStatus.ORDER_DONE);
        Expert expert = order.getExpert();
        Customer customer = order.getCustomer();
        ExpertSuggestions expertSuggestions = expertSuggestionsService.findByExpertEmailAndOrderId(expert.getEmail(), order.getId());
        double expertProposedPrice = expertSuggestions.getProposedPrice();
        Wallet customerWallet = customer.getWallet();
        if (customerWallet.getPrice() < expertProposedPrice) {
            throw new CustomBadRequestException("The account balance is insufficient. Please top up your account");
        } else {
            Wallet expertWallet = expert.getWallet();
            double expertAccountBalance = expertWallet.getPrice() + expertProposedPrice;
            expertWallet.setPrice(expertAccountBalance);
            double customerAccountBalance = customerWallet.getPrice() - expertProposedPrice;
            customerWallet.setPrice(customerAccountBalance);
            order.setOrderStatus(OrderStatus.ORDER_PAID);
            walletService.save(expertWallet);
            walletService.save(customerWallet);
            Orders saveOrder = ordersRepository.save(order);
            return ordersMapper.orderToOrdersResponse(saveOrder);
        }
    }

    @Override
    public Orders findById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("orders with this id was not found"));
    }

    @Override
    public List<OrdersResponse> findAllOrderWaitingForSpecialistSuggestion(SubDutyRequestWithName request) {
        List<Orders> allOrdersByOrderStatus = ordersRepository.findAllOrdersByOrderStatus(
                request.getNameSubDuty(),
                ORDER_WAITING_FOR_SPECIALIST_SUGGESTION,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
        if (allOrdersByOrderStatus.isEmpty()) {
            throw new CustomResourceNotFoundException("There is no result");
        } else {
            return allOrdersByOrderStatus.stream().map(ordersMapper::orderToOrdersResponse).toList();
        }
    }

    @Override
    public List<OrdersResponse> findAllOrderWaitingForSpecialistToWorkPlace(CustomerRequestWithEmail request) {
        List<Orders> findAllOrder = ordersRepository.findAllByOrderStatusAndCustomerEmail
                (OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE, request.getCustomerEmail());
        if (findAllOrder.isEmpty()) {
            throw new CustomResourceNotFoundException("There is no result");
        } else {
            return findAllOrder.stream().map(ordersMapper::orderToOrdersResponse).toList();
        }
    }

    @Override
    public List<OrdersResponse> findAllDoneOrders(CustomerRequestWithEmail request) {
        List<Orders> findAllOrder = ordersRepository.findAllByOrderStatusAndCustomerEmail
                (OrderStatus.ORDER_DONE, request.getCustomerEmail());
        if (findAllOrder.isEmpty()) {
            throw new CustomResourceNotFoundException("There is no result");
        } else {
            return findAllOrder.stream().map(ordersMapper::orderToOrdersResponse).toList();
        }
    }

    @Override
    public List<OrdersResponse> findAllStartedOrders(CustomerRequestWithEmail request) {
        List<Orders> findAllOrder =
                ordersRepository.findAllByOrderStatusAndCustomerEmail(OrderStatus.ORDER_STARTED, request.getCustomerEmail());
        if (findAllOrder.isEmpty()) {
            throw new CustomResourceNotFoundException("There is no result");
        } else {
            return findAllOrder.stream().map(ordersMapper::orderToOrdersResponse).toList();
        }
    }

    @Override
    public List<OrdersResponse> findAllPaidOrders(CustomerRequestWithEmail request) {
        List<Orders> findAllOrder =
                ordersRepository.findAllByOrderStatusAndCustomerEmail(OrderStatus.ORDER_PAID, request.getCustomerEmail());
        if (findAllOrder.isEmpty()) {
            throw new CustomResourceNotFoundException("There is no result");
        } else {
            return findAllOrder.stream().map(ordersMapper::orderToOrdersResponse).toList();
        }
    }

    @Override
    public List<OrdersResponse> findAllOrders(PersonRequestWithEmail request, String personType) {

        List<Orders> ordersList = switch (personType) {
            case "customer" -> ordersRepository.findAllOrdersCustomer(request.getPersonEmail());
            case "expert" -> ordersRepository.findAllOrdersExpert(request.getPersonEmail());
            default -> throw new CustomBadRequestException("Invalid person type");
        };

        if (ordersList.isEmpty()) {
            throw new CustomBadRequestException("There is no result");
        }

        return ordersList.stream()
                .filter(orders -> findAllOrderStatus().contains(orders.getOrderStatus()))
                .map(ordersMapper::orderToOrdersResponse)
                .sorted(Comparator.comparing(OrdersResponse::getOrderStatus))
                .collect(Collectors.toList());
    }

    private List<OrderStatus> findAllOrderStatus() {
        return List.of(
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION,
                OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE,
                OrderStatus.ORDER_STARTED,
                OrderStatus.ORDER_DONE,
                OrderStatus.ORDER_PAID);
    }

    @Override
    public List<OrdersResponse> findOrdersByDynamicSearch(OrderSummaryRequestWithOrderStatus request) {
        Specification<Orders> specification = Specification.where(null);
        boolean isRequestValid = false;

        if (isNotEmpty(request.getDutyName())) {
            specification = specification.and(hasDutyName(request.getDutyName()));
            isRequestValid = true;
        }
        if (isNotEmpty(request.getSubDutyName())) {
            specification = specification.and(hasSubDutyName(request.getSubDutyName()));
            isRequestValid = true;
        }
        if (request.getDateOfWork() != null) {
            specification = specification.and(hasDateOfWork(request.getDateOfWork()));
            isRequestValid = true;
        }
        if (request.getTimeOfWork() != null) {
            specification = specification.and(hasTimeOfWork(request.getTimeOfWork()));
            isRequestValid = true;
        }
        if (request.getOrderStatus() != null) {
            specification = specification.and(hasOrderStatus(request.getOrderStatus()));
            isRequestValid = true;
        }
        if (!isRequestValid) {
            throw new CustomBadRequestException("There is no result");
        }
        List<Orders> ordersList = ordersRepository.findAll(specification);
        if (ordersList.isEmpty()) {
            throw new CustomBadRequestException("There is no result");
        } else {
            return ordersList.stream().map(ordersMapper::orderToOrdersResponse).toList();
        }
    }

    private boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private Specification<Orders> hasDutyName(String dutyName) {
        return ((root, query, criteriaBuilder) -> {
            Join<Orders, SubDuty> ordersSubDutyJoin = root.join("subDuty", JoinType.INNER);
            Join<SubDuty, Duty> subDutyDutyJoin = ordersSubDutyJoin.join("duty", JoinType.INNER);
            return criteriaBuilder.equal(subDutyDutyJoin.get("name"), dutyName);
        });
    }

    private Specification<Orders> hasSubDutyName(String subDutyName) {
        return ((root, query, criteriaBuilder) -> {
            Join<Orders, SubDuty> ordersSubDutyJoin = root.join("subDuty");
            return criteriaBuilder.equal(ordersSubDutyJoin.get("name"), subDutyName);
        });
    }

    private Specification<Orders> hasDateOfWork(LocalDate dateOfWork) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateOfWork"), dateOfWork);
    }

    private Specification<Orders> hasTimeOfWork(LocalTime timeOfWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("TimeOfWork"), timeOfWord);
    }


    private Specification<Orders> hasOrderStatus(OrderStatus orderStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderStatus"), orderStatus);
    }

    @Override
    public String onlinePaymentPortal(String customerEmail, Long orderId, HttpServletRequest request) {
        Optional<Orders> findOrder = ordersRepository.findById(orderId);
        if (
                findOrder.isPresent() &&
                        findOrder.get().getCustomer().getEmail().equals(customerEmail) &&
                        findOrder.get().getOrderStatus().equals(OrderStatus.ORDER_DONE)
        ) {
            Orders order = findOrder.get();
            Expert expert = order.getExpert();
            ExpertSuggestions expertSuggestions = expertSuggestionsService.findByExpertEmailAndOrderId(expert.getEmail(), order.getId());
            double expertProposedPrice = expertSuggestions.getProposedPrice();
            request.getSession().setAttribute("money", expertProposedPrice);
            request.getSession().setAttribute("orderId", order.getId());
            request.getSession().setAttribute("customerEmail", customerEmail);
            return "onlinePayment";
        } else {
            return "notFound";
        }
    }

    @Override
    public String onlinePayment(String customerEmail, Long orderId, HttpServletRequest request, CardInformationRequest cardInformationRequest) {
        Optional<Orders> findOrder = ordersRepository.findById(orderId);
        if (
                isValidPayment(request, cardInformationRequest) &&
                        findOrder.isPresent() &&
                        findOrder.get().getCustomer().getEmail().equals(customerEmail) &&
                        findOrder.get().getOrderStatus().equals(OrderStatus.ORDER_DONE)
        ) {
            Orders order = findOrder.get();
            Expert expert = order.getExpert();
            ExpertSuggestions expertSuggestions = expertSuggestionsService.findByExpertEmailAndOrderId(expert.getEmail(), order.getId());
            double expertProposedPrice = expertSuggestions.getProposedPrice();
            double depositAmount = Double.parseDouble(cardInformationRequest.getAmount());
            if (depositAmount != expertProposedPrice) return "failedPayment";
            Wallet expertWallet = expert.getWallet();
            double expertAccountBalance = expertWallet.getPrice() + ((70.0 / 100.0) * depositAmount);
            expertWallet.setPrice(expertAccountBalance);
            order.setOrderStatus(OrderStatus.ORDER_PAID);
            walletService.save(expertWallet);
            ordersRepository.save(order);
            return "successfullyPayment";
        } else {
            return "failedPayment";
        }
    }

    private boolean isValidPayment(HttpServletRequest request, CardInformationRequest cardInformationRequest) {
        if (!isValidCardInformation(cardInformationRequest)) return false;

        String captchaText = (String) request.getSession().getAttribute("captcha");
        String captchaRequest = cardInformationRequest.getCaptcha();
        if (!captchaText.equals(captchaRequest)) return false;

        int month = Integer.parseInt(cardInformationRequest.getMonth());
        int year = Integer.parseInt("20" + cardInformationRequest.getYear());
        if (month <= 0 || month > 12) return false;

        LocalDate expirationCard = LocalDate.of(year, month, 1);
        return !expirationCard.isBefore(LocalDate.now());
    }

    private boolean isValidCardInformation(CardInformationRequest cardInformationRequest) {
        Set<ConstraintViolation<CardInformationRequest>> violations = validator.validate(cardInformationRequest);
        return violations.isEmpty();
    }
}
