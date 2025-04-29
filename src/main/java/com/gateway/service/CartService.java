package com.gateway.service;

import com.gateway.client.BookServiceHTTPClient;
import com.gateway.client.CartServiceHTTPClient;
import com.gateway.dto.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartService {

    @Autowired
    private CartServiceHTTPClient cartServiceHTTPClient;
    @Autowired
    private BookServiceHTTPClient bookServiceHTTPClient;

    public CartDetailsDTO getCartDetailsofUser(long userid) {

        CartResponseDTO cart = cartServiceHTTPClient.getCartOfUserId(userid).orElseThrow(() -> new IllegalArgumentException("Cart nao existe"));

        List<CartItemDetailsDTO> cartitemsDetailsDTO = getcartItemDetailsDTO(cart);

        CartDetailsDTO cartDetailsDTO = new CartDetailsDTO();
        cartDetailsDTO.setId(cart.getId());
        cartDetailsDTO.setCreatedDate(cart.getCreatedDate());
        cartDetailsDTO.setUserId(userid);
        //cartDetailsDTO.setTotal(cart.get);
        cartDetailsDTO.setCartItemsDetails(cartitemsDetailsDTO);

        return cartDetailsDTO;

    }

    private List<CartItemDetailsDTO> getcartItemDetailsDTO(CartResponseDTO cart) {

        List<CartItemDetailsDTO> cartitemsDetailsDTOList = getcartItemDetailsDTO(cart);

        for (CartItemsResponseDTO cartItemsResponseDTO: cart.getCartItems()) {
            BookResponseDTO book = bookServiceHTTPClient.getBookbyId(cartItemsResponseDTO.getBookid()).orElseThrow(() -> new IllegalArgumentException("Book nao existe"));

            CartItemDetailsDTO cartItemDetailsDTO = new CartItemDetailsDTO();
            BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();

            // todo add the rest of the data to the dto
            bookDetailsDTO.setBookId(book.getId());
            //bookDetailsDTO.setCategory(book.get);
            bookDetailsDTO.setPrice(book.getPrice());
            bookDetailsDTO.setQuantity(book.getQuantity());
            bookDetailsDTO.setIsbnNumber(book.getIsbnNumber());
            bookDetailsDTO.setTitle(book.getTitle());
            //bookDetailsDTO.getSubcategory(book.get);

            cartItemDetailsDTO.setId(cartItemsResponseDTO.getId());
            cartItemDetailsDTO.setQuantity(cartItemsResponseDTO.getQuantity());
            cartItemDetailsDTO.setUnitPrice(cartItemsResponseDTO.getUnitPrice());
            cartItemDetailsDTO.setSubTotal(cartItemsResponseDTO.getSubTotal());
            cartItemDetailsDTO.setBookDetailsDTO(bookDetailsDTO);

            cartitemsDetailsDTOList.add(cartItemDetailsDTO);

        }

        return cartitemsDetailsDTOList;
    }

}