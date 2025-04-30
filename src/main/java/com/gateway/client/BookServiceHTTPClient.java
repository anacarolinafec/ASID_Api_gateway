package com.gateway.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.dto.BookResponseDTO;
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
public class BookServiceHTTPClient {
    private final String bookServiceUrl;

    public BookServiceHTTPClient(@Value("${book.service.url}") String bookServiceUrl) {
        this.bookServiceUrl = bookServiceUrl;
    }

    public Optional<BookResponseDTO> getBookbyId(Long bookId) {
        log.info("Fetching book with ID: {}", bookId);
        String urlString = bookServiceUrl + "/books/" + bookId;

        try {
            // Criar URL a partir da string
            URL url = new URL(urlString);

            // Abrir uma conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);  // Tempo de timeout para a conexão
            connection.setReadTimeout(5000);     // Tempo de timeout para leitura da resposta

            // Verificar o código de resposta
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // Ler a resposta da requisição
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Converter a resposta JSON para BookResponseDTO (supondo que você use uma biblioteca como Jackson ou Gson)
                // Exemplo com Jackson
                BookResponseDTO book = new ObjectMapper().readValue(response.toString(), BookResponseDTO.class);
                return Optional.of(book);
            } else {
                log.error("Failed to fetch book: HTTP code {}", status);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error fetching book with ID {}: {}", bookId, e.getMessage());
            return Optional.empty(); // Retorna Optional.empty() em caso de erro
        }
    }
}
