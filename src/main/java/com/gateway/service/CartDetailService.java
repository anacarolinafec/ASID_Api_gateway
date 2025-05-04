package com.gateway.service;

import com.gateway.client.BookServiceHTTPClient;
import com.gateway.client.CartServiceHTTPClient;
import com.gateway.client.UserServiceHTTPClient;
import com.gateway.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CartDetailService {

    @Autowired
    private CartServiceHTTPClient cartServiceHTTPClient;
    @Autowired
    private BookServiceHTTPClient bookServiceHTTPClient;
    @Autowired
    private UserServiceHTTPClient userServiceHTTPClient;

    // retorna o cart detalhado
    public CartDetailsDTO getCartDetailsofUser(long userid) {

        // Obtém o carrinho de compras do usuário
        CartResponseDTO cart = cartServiceHTTPClient.getCartOfUserId(userid)
                .orElseThrow(() -> new IllegalArgumentException("Cart não existe"));

        UserResponseDTO user = userServiceHTTPClient.getUserById(userid)
                .orElseThrow(() -> new IllegalArgumentException("User não existe"));

        // Obtém os detalhes dos itens do carrinho
        List<CartItemDetailsDTO> cartitemsDetailsDTO = getCartItemDetailsDTO(cart);

        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());

        // Cria o DTO de detalhes do carrinho e preenche com os dados
        CartDetailsDTO cartDetailsDTO = new CartDetailsDTO();
        cartDetailsDTO.setCartId(cart.getId());
        cartDetailsDTO.setCreatedDate(cart.getCreatedDate());
        cartDetailsDTO.setUserDetailsDTO(userDetails);
        //cartDetailsDTO.setTotal(cart.get);
        cartDetailsDTO.setCartItemsDetails(cartitemsDetailsDTO);

        return cartDetailsDTO;
    }

    // retorna a Lista de cartItems
    private List<CartItemDetailsDTO> getCartItemDetailsDTO(CartResponseDTO cart) {
        // Cria uma lista vazia para armazenar os detalhes dos itens
        List<CartItemDetailsDTO> cartitemsDetailsDTOList = new ArrayList<>();

        // Preenche a lista com os detalhes dos itens do carrinho
        for (CartItemsResponseDTO cartItemsResponseDTO : cart.getCartItems()) {
            // Obtém os detalhes do livro correspondente ao item do carrinho
            log.info("Fetching book with ID: {}", cartItemsResponseDTO.getBookid());

            BookResponseDTO book = bookServiceHTTPClient.getBookbyId(cartItemsResponseDTO.getBookid())
                    .orElseThrow(() -> new IllegalArgumentException("Book não existe"));

            // Cria o DTO para o item do carrinho
            CartItemDetailsDTO cartItemDetailsDTO = new CartItemDetailsDTO();
            BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();

            // Preenche os detalhes do livro
            bookDetailsDTO.setBookId(book.getId());
            //bookDetailsDTO.setCategory(book.getCategory()); // Se necessário
            bookDetailsDTO.setPrice(book.getPrice());
            bookDetailsDTO.setQuantity(book.getQuantity());
            bookDetailsDTO.setIsbnNumber(book.getIsbnNumber());
            bookDetailsDTO.setTitle(book.getTitle());
            //bookDetailsDTO.setSubcategory(book.getSubcategory()); // Se necessário

            // Preenche os detalhes do item do carrinho
            cartItemDetailsDTO.setCartItemId(cartItemsResponseDTO.getId());
            cartItemDetailsDTO.setQuantity(cartItemsResponseDTO.getQuantity());
            cartItemDetailsDTO.setUnitPrice(cartItemsResponseDTO.getUnitPrice());
            cartItemDetailsDTO.setSubTotal(cartItemsResponseDTO.getSubTotal());
            cartItemDetailsDTO.setBookDetailsDTO(bookDetailsDTO);

            // Adiciona o item à lista de detalhes do carrinho
            cartitemsDetailsDTOList.add(cartItemDetailsDTO);
        }

        return cartitemsDetailsDTOList;
    }
}
