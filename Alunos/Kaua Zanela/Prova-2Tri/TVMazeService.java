package com.seriestracker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TVMazeService {

    // URL base da API do TVMaze
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";

    // Cliente HTTP para fazer as requisições. É eficiente criar um e reutilizá-lo.
    private final HttpClient client = HttpClient.newHttpClient();
    
    // ObjectMapper para converter JSON em objetos Java.
    private final ObjectMapper mapper = new ObjectMapper();

    public TVMazeService() {
        // Registra o módulo que ensina o ObjectMapper a trabalhar com LocalDate
        mapper.registerModule(new JavaTimeModule());
    }

    public List<Show> searchShows(String query) {
        // Codifica a busca do usuário para ser segura em uma URL (ex: "Breaking Bad" -> "Breaking%20Bad")
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // Monta o request para a API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + encodedQuery))
                .build();

        try {
            // Envia a requisição e espera a resposta como uma String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se a requisição foi bem-sucedida (código 200)
            if (response.statusCode() == 200) {
                // A resposta da API é uma lista de objetos, onde cada um tem uma chave "show"
                // Ex: [ { "score": 0.9, "show": { ... } }, { "score": 0.8, "show": { ... } } ]
                // Vamos extrair apenas o conteúdo de "show" de cada item.
                
                List<Show> shows = new ArrayList<>();
                JsonNode rootNode = mapper.readTree(response.body());

                for (JsonNode resultNode : rootNode) {
                    JsonNode showNode = resultNode.path("show");
                    Show show = mapper.treeToValue(showNode, Show.class);
                    shows.add(show);
                }
                return shows;
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar dados da API: " + e.getMessage());
            // Em caso de erro, retorna uma lista vazia para não quebrar o programa
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }
}
