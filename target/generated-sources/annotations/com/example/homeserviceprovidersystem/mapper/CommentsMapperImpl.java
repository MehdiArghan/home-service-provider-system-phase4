package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.address.AddressResponse;
import com.example.homeserviceprovidersystem.dto.comments.CommentResponse;
import com.example.homeserviceprovidersystem.dto.comments.CommentSummaryResponse;
import com.example.homeserviceprovidersystem.entity.Address;
import com.example.homeserviceprovidersystem.entity.Comments;
import com.example.homeserviceprovidersystem.entity.Orders;
import com.example.homeserviceprovidersystem.entity.SubDuty;
import com.example.homeserviceprovidersystem.entity.enums.OrderStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T16:29:56+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class CommentsMapperImpl implements CommentsMapper {

    @Override
    public CommentResponse commentsToCommentResponse(Comments comments) {
        if ( comments == null ) {
            return null;
        }

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setProposedPrice( commentsOrdersProposedPrice( comments ) );
        commentResponse.setJobDescription( commentsOrdersJobDescription( comments ) );
        commentResponse.setDateOfWork( commentsOrdersDateOfWork( comments ) );
        commentResponse.setTimeOfWork( commentsOrdersTimeOfWork( comments ) );
        commentResponse.setAddress( addressToAddressResponse( commentsOrdersAddress( comments ) ) );
        commentResponse.setOrderStatus( commentsOrdersOrderStatus( comments ) );
        commentResponse.setSubDutyName( commentsOrdersSubDutyName( comments ) );
        commentResponse.setId( comments.getId() );
        commentResponse.setScore( comments.getScore() );
        commentResponse.setComment( comments.getComment() );

        return commentResponse;
    }

    @Override
    public CommentSummaryResponse commentsToCommentSummaryResponse(Comments comments) {
        if ( comments == null ) {
            return null;
        }

        CommentSummaryResponse commentSummaryResponse = new CommentSummaryResponse();

        commentSummaryResponse.setScore( comments.getScore() );

        return commentSummaryResponse;
    }

    private double commentsOrdersProposedPrice(Comments comments) {
        if ( comments == null ) {
            return 0.0d;
        }
        Orders orders = comments.getOrders();
        if ( orders == null ) {
            return 0.0d;
        }
        double proposedPrice = orders.getProposedPrice();
        return proposedPrice;
    }

    private String commentsOrdersJobDescription(Comments comments) {
        if ( comments == null ) {
            return null;
        }
        Orders orders = comments.getOrders();
        if ( orders == null ) {
            return null;
        }
        String jobDescription = orders.getJobDescription();
        if ( jobDescription == null ) {
            return null;
        }
        return jobDescription;
    }

    private LocalDate commentsOrdersDateOfWork(Comments comments) {
        if ( comments == null ) {
            return null;
        }
        Orders orders = comments.getOrders();
        if ( orders == null ) {
            return null;
        }
        LocalDate dateOfWork = orders.getDateOfWork();
        if ( dateOfWork == null ) {
            return null;
        }
        return dateOfWork;
    }

    private LocalTime commentsOrdersTimeOfWork(Comments comments) {
        if ( comments == null ) {
            return null;
        }
        Orders orders = comments.getOrders();
        if ( orders == null ) {
            return null;
        }
        LocalTime timeOfWork = orders.getTimeOfWork();
        if ( timeOfWork == null ) {
            return null;
        }
        return timeOfWork;
    }

    private Address commentsOrdersAddress(Comments comments) {
        if ( comments == null ) {
            return null;
        }
        Orders orders = comments.getOrders();
        if ( orders == null ) {
            return null;
        }
        Address address = orders.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
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

    private OrderStatus commentsOrdersOrderStatus(Comments comments) {
        if ( comments == null ) {
            return null;
        }
        Orders orders = comments.getOrders();
        if ( orders == null ) {
            return null;
        }
        OrderStatus orderStatus = orders.getOrderStatus();
        if ( orderStatus == null ) {
            return null;
        }
        return orderStatus;
    }

    private String commentsOrdersSubDutyName(Comments comments) {
        if ( comments == null ) {
            return null;
        }
        Orders orders = comments.getOrders();
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
}
