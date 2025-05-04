package com.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDetailsDTO {

    private long cartId;
    private String createdDate;
    //private long userId;
    private UserDetailsDTO userDetailsDTO;
    private Double total;
    private List<CartItemDetailsDTO> cartItemsDetails;
}
