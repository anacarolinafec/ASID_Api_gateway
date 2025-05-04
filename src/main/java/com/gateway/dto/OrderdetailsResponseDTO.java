package com.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderdetailsResponseDTO {
    private Long id;
    private int quantity;
    private double subTotal;
    private Long bookId;
    private Long userId;
}

