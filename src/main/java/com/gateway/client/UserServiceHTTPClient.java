package com.gateway.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.dto.UserResponseDTO;
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
public class UserServiceHTTPClient {

    private final String userServiceUrl;

    public UserServiceHTTPClient(@Value("${user.service.url}") String userServiceUrl) {
        this.userServiceUrl = userServiceUrl;
    }

    public Optional<UserResponseDTO> getUserById(Long userId) {
        log.info("Fetching user with ID: {}", userId);
        String urlString = userServiceUrl + "/id/" + userId;

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

                UserResponseDTO user = new ObjectMapper().readValue(response.toString(), UserResponseDTO.class);
                return Optional.of(user);
            } else {
                log.error("Failed to fetch user: HTTP code {}", status);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error fetching user with ID {}: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}


