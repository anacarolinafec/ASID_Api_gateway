package com.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderResponseDTO {
    private Long id;
    private Date orderDate;
    private double totalPrice;
    private long userId;
    @JsonProperty("orderDetails") // <- nome da propriedade no JSON vindo da API de orders
    private List<OrderdetailsResponseDTO> orderDetails;

}
