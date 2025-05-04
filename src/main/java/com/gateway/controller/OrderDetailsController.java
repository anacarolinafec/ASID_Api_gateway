package com.gateway.controller;

import com.gateway.dto.CartDetailsDTO;
import com.gateway.dto.OrderDetailsDTO;
import com.gateway.service.CartDetailService;
import com.gateway.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compose")

public class OrderDetailsController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/order/details/{userid}")
    public ResponseEntity<OrderDetailsDTO> getUserDetailedOrder(@PathVariable long userid){

        OrderDetailsDTO orderDetails = orderDetailService.getOrderDetailsofUser(userid);

        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}
