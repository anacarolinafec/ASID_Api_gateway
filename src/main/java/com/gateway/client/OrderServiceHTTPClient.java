package com.gateway.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.dto.OrderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceHTTPClient {
    private final String orderServiceUrl;

    public OrderServiceHTTPClient(@Value("${order.service.url}") String orderServiceUrl) {
        this.orderServiceUrl = orderServiceUrl;
    }

    public Optional<OrderResponseDTO> getOrderByUserId(Long userId) {
        log.info("Fetching order of user with ID: {}", userId);
        String urlString = orderServiceUrl + "/user/" + userId;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                OrderResponseDTO order = new ObjectMapper().readValue(response.toString(), OrderResponseDTO.class);
                return Optional.of(order);
            } else {
                log.error("Failed to fetch order: HTTP code {}", status);
                return Optional.empty();
            }

        } catch (Exception e) {
            log.error("Error fetching order of user with ID {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}

