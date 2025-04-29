package com.gateway.dto;

import lombok.Data;

@Data
public class BookDetailsDTO {
    private long bookId;
    private String title;
    private String isbnNumber;
    private String description;
    private double  price;
    private int quantity;
    private String author;
    private String category;
    private String subcategory;
}
