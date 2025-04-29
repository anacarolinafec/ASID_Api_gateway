package com.gateway.controller;

import com.gateway.dto.CartDetailsDTO;
import com.gateway.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compose")

public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart/details/{userid}")
    public ResponseEntity<CartDetailsDTO> getUserDetailedCart(@PathVariable long userid){

        CartDetailsDTO cartDetails = cartService.getCartDetailsofUser(userid);

        return new ResponseEntity<>(cartDetails,HttpStatus.OK);
    }

}
