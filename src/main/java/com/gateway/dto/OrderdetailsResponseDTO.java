package com.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderdetailsResponse {
    private Long id;
    private int quantity;
    private double subTotal;
    private Long bookId;
    private Long userId;
    private OrderResponseDTO orderResponseDTO;
}
