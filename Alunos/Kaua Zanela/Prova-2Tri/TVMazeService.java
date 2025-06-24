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

  
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";

    
    private final HttpClient client = HttpClient.newHttpClient();
    
  
    private final ObjectMapper mapper = new ObjectMapper();

    public TVMazeService() {
       
        mapper.registerModule(new JavaTimeModule());
    }

    public List<Show> searchShows(String query) {
        
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

      
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + encodedQuery))
                .build();

        try {
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

         
            if (response.statusCode() == 200) {
                
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
            
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }
}
