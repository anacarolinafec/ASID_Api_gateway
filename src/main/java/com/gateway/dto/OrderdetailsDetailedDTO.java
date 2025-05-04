package com.gateway.dto;

import lombok.Data;

@Data
public class OrderdetailsDetailedDTO {
    private Long orderDetailsId;
    private int quantity;
    private double subTotal;
    //private UserDetailsDTO userDetailsDTO;
    private BookDetailsDTO bookDetailsDTO;

}
