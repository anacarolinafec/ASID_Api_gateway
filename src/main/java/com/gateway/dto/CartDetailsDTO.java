package com.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDetailsDTO {

    private long id;
    private String createdDate;
    private long userId;
    private Double total;
    private List<CartItemDetailsDTO> cartItemsDetails;
}
