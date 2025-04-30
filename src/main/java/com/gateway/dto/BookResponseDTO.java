package com.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponseDTO {
    private long id;
    private String title;
    private String isbnNumber;
    private double price;
    private int quantity;
}
