package com.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderdetailsDetailed {

    private Long orderId;
    private int quantity;
    private double subTotal;
    private Long bookId;
    private Long userId;
    private BookDetailsDTO bookDetailsDTO;

}
