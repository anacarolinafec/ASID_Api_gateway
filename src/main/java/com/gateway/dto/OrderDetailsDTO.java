package com.gateway.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailsDTO {
    private Long id;
    private Date orderDate;
    private double totalPrice;
    private UserDetailsDTO userDetailsDTO;
    private List<OrderdetailsDetailedDTO> orderdetailsDetailedDTOList;
}
