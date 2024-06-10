package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.customer.CustomerRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerResponse;
import com.example.homeserviceprovidersystem.dto.customer.CustomerSummaryResponse;
import com.example.homeserviceprovidersystem.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T19:01:59+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer customerRequestTocustomer(CustomerRequest request) {
        if ( request == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setFirstName( request.getFirstName() );
        customer.setLastName( request.getLastName() );
        customer.setEmail( request.getEmail() );
        customer.setPassword( request.getPassword() );

        return customer;
    }

    @Override
    public CustomerResponse customerToCustomerResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setId( customer.getId() );
        customerResponse.setFirstName( customer.getFirstName() );
        customerResponse.setLastName( customer.getLastName() );
        customerResponse.setEmail( customer.getEmail() );
        customerResponse.setPassword( customer.getPassword() );
        customerResponse.setRegistrationDate( customer.getRegistrationDate() );
        customerResponse.setRegistrationTime( customer.getRegistrationTime() );
        customerResponse.setWallet( customer.getWallet() );

        return customerResponse;
    }

    @Override
    public CustomerSummaryResponse customerToCustomerSummaryResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerSummaryResponse customerSummaryResponse = new CustomerSummaryResponse();

        customerSummaryResponse.setId( customer.getId() );
        customerSummaryResponse.setFirstName( customer.getFirstName() );
        customerSummaryResponse.setLastName( customer.getLastName() );
        customerSummaryResponse.setEmail( customer.getEmail() );
        customerSummaryResponse.setRegistrationDate( customer.getRegistrationDate() );
        customerSummaryResponse.setRegistrationTime( customer.getRegistrationTime() );

        return customerSummaryResponse;
    }
}
