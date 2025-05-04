package com.gateway.service;

import com.gateway.client.BookServiceHTTPClient;
import com.gateway.client.CartServiceHTTPClient;
import com.gateway.client.OrderServiceHTTPClient;
import com.gateway.client.UserServiceHTTPClient;
import com.gateway.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private CartServiceHTTPClient cartServiceHTTPClient;
    @Autowired
    private BookServiceHTTPClient bookServiceHTTPClient;
    @Autowired
    private UserServiceHTTPClient userServiceHTTPClient;
    @Autowired
    private OrderServiceHTTPClient orderServiceHTTPClient;

    public OrderDetailsDTO getOrderDetailsofUser(long userid) {

        // Obtém o carrinho de compras do usuário

        OrderResponseDTO order = orderServiceHTTPClient.getOrderByUserId(userid)
                .orElseThrow(() -> new IllegalArgumentException("Order não existe"));

        UserResponseDTO user = userServiceHTTPClient.getUserById(userid)
                .orElseThrow(() -> new IllegalArgumentException("User não existe"));

        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());

        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

        List<OrderdetailsDetailedDTO> orderdetailsDetailed = getOrderdetailsDetailedDTO(order);

        orderDetailsDTO.setId(order.getId());
        orderDetailsDTO.setOrderDate(order.getOrderDate());
        orderDetailsDTO.setUserDetailsDTO(userDetails);
        orderDetailsDTO.setTotalPrice(order.getTotalPrice());
        orderDetailsDTO.setOrderdetailsDetailedDTOList(orderdetailsDetailed);

        return orderDetailsDTO;
    }


    private List<OrderdetailsDetailedDTO> getOrderdetailsDetailedDTO (OrderResponseDTO orderResponseDTO) {

        // Cria uma lista vazia para armazenar os detalhes dos itens
        List<OrderdetailsDetailedDTO> orderdetailsDetailedDTOList = new ArrayList<>();

        // Preenche a lista com os detalhes dos itens do carrinho
        for (OrderdetailsResponseDTO orderdetailsResponseDTO : orderResponseDTO.getOrderDetails()) {
            // Obtém os detalhes do livro correspondente ao item do carrinho

            BookResponseDTO book = bookServiceHTTPClient.getBookbyId(orderdetailsResponseDTO.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("Book não existe"));

            // Cria o DTO para o item do carrinho
            OrderdetailsDetailedDTO orderdetailsDetailedDTO = new OrderdetailsDetailedDTO();
            BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();

            // Preenche os detalhes do livro
            bookDetailsDTO.setBookId(book.getId());
            bookDetailsDTO.setPrice(book.getPrice());
            bookDetailsDTO.setQuantity(book.getQuantity());
            bookDetailsDTO.setIsbnNumber(book.getIsbnNumber());
            bookDetailsDTO.setTitle(book.getTitle());
            //bookDetailsDTO.setSubcategory(book.getSubcategory()); // Se necessário
            //bookDetailsDTO.setCategory(book.getCategory()); // Se necessário

            // Preenche os detalhes do item do carrinho
            orderdetailsDetailedDTO.setQuantity(orderdetailsResponseDTO.getQuantity());
            orderdetailsDetailedDTO.setOrderDetailsId(orderdetailsResponseDTO.getId());
            orderdetailsDetailedDTO.setBookDetailsDTO(bookDetailsDTO);
            orderdetailsDetailedDTO.setSubTotal(orderdetailsResponseDTO.getSubTotal());

            // Adiciona o item à lista de detalhes do carrinho
            orderdetailsDetailedDTOList.add(orderdetailsDetailedDTO);
        }

        return orderdetailsDetailedDTOList;
    }

}
