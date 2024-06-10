package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.address.AddressResponse;
import com.example.homeserviceprovidersystem.dto.order.OrdersResponse;
import com.example.homeserviceprovidersystem.entity.Address;
import com.example.homeserviceprovidersystem.entity.Orders;
import com.example.homeserviceprovidersystem.entity.SubDuty;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T18:51:43+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class OrdersMapperImpl implements OrdersMapper {

    @Override
    public OrdersResponse orderToOrdersResponse(Orders order) {
        if ( order == null ) {
            return null;
        }

        OrdersResponse ordersResponse = new OrdersResponse();

        ordersResponse.setSubDutyName( orderSubDutyName( order ) );
        ordersResponse.setId( order.getId() );
        ordersResponse.setProposedPrice( order.getProposedPrice() );
        ordersResponse.setJobDescription( order.getJobDescription() );
        ordersResponse.setDateOfWork( order.getDateOfWork() );
        ordersResponse.setTimeOfWork( order.getTimeOfWork() );
        ordersResponse.setAddress( addressToAddressResponse( order.getAddress() ) );
        ordersResponse.setOrderStatus( order.getOrderStatus() );

        return ordersResponse;
    }

    private String orderSubDutyName(Orders orders) {
        if ( orders == null ) {
            return null;
        }
        SubDuty subDuty = orders.getSubDuty();
        if ( subDuty == null ) {
            return null;
        }
        String name = subDuty.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected AddressResponse addressToAddressResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse addressResponse = new AddressResponse();

        addressResponse.setProvince( address.getProvince() );
        addressResponse.setCity( address.getCity() );
        addressResponse.setStreet( address.getStreet() );
        addressResponse.setPostalCode( address.getPostalCode() );

        return addressResponse;
    }
}
