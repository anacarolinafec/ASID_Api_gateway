package com.gateway.dto;

import lombok.Data;

@Data
public class CartItemDetailsDTO {

private long cartItemId;
private int quantity;
private Double unitPrice;
private Double subTotal;
private BookDetailsDTO bookDetailsDTO;
}
