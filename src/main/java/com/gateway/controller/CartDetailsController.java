package com.gateway.controller;

import com.gateway.dto.CartDetailsDTO;
import com.gateway.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compose")

public class CartDetailsController {

    @Autowired
    private CartDetailService cartDetailService;

    @GetMapping("/cart/details/{userid}")
    public ResponseEntity<CartDetailsDTO> getUserDetailedCart(@PathVariable long userid){

        CartDetailsDTO cartDetails = cartDetailService.getCartDetailsofUser(userid);

        return new ResponseEntity<>(cartDetails,HttpStatus.OK);
    }

}
