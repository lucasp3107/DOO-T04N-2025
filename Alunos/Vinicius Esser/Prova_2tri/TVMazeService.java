package br.com.seunome.prova2tri; 

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TVMazeService {
    private static final String API_SEARCH_URL = "https://api.tvmaze.com/search/shows?q=";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TVMazeService() {
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Serie> buscarSeriesPorNome(String nomeSerie) throws IOException, InterruptedException {
        if (nomeSerie == null || nomeSerie.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String encodedNomeSerie = URLEncoder.encode(nomeSerie.trim(), StandardCharsets.UTF_8.toString());
        String fullUrl = API_SEARCH_URL + encodedNomeSerie;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return parseSeries(response.body());
        } else {
            System.err.println("Erro ao buscar séries na API TVMaze: Status " + response.statusCode() + " - " + response.body());
            throw new IOException("Falha na comunicação com a API da TVMaze. Status: " + response.statusCode());
        }
    }

    private List<Serie> parseSeries(String jsonResponse) {
        List<Serie> series = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            if (rootNode.isArray()) {
                for (JsonNode itemNode : rootNode) {
                    JsonNode showNode = itemNode.path("show");

                    if (showNode.isMissingNode() || showNode.isNull()) {
                        continue;
                    }

                    Serie serie = new Serie();
                    serie.setId(showNode.path("id").asInt());
                    serie.setNome(showNode.path("name").asText());
                    serie.setIdioma(showNode.path("language").asText());

                    List<String> generos = new ArrayList<>();
                    if (showNode.path("genres").isArray()) {
                        for (JsonNode genreNode : showNode.path("genres")) {
                            generos.add(genreNode.asText());
                        }
                    }
                    serie.setGeneros(generos);

                    JsonNode ratingNode = showNode.path("rating");
                    if (ratingNode.has("average") && !ratingNode.path("average").isNull()) {
                        try {
                            serie.setNotaGeral(ratingNode.path("average").asDouble());
                        } catch (Exception e) {
                            System.err.println("Erro ao parsear nota geral: " + ratingNode.path("average").asText() + ". Erro: " + e.getMessage());
                            serie.setNotaGeral(null);
                        }
                    } else {
                        serie.setNotaGeral(null);
                    }

                    serie.setEstado(showNode.path("status").asText());

                    DateTimeFormatter apiDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; 
                    try {
                        String premieredStr = showNode.path("premiered").asText();
                        if (premieredStr != null && !premieredStr.isEmpty()) {
                            serie.setDataEstreia(LocalDate.parse(premieredStr, apiDateFormatter));
                        }
                    } catch (Exception e) {
                        serie.setDataEstreia(null);
                    }

                    try {
                        String endedStr = showNode.path("ended").asText();
                        if (endedStr != null && !endedStr.isEmpty()) {
                            serie.setDataTermino(LocalDate.parse(endedStr, apiDateFormatter));
                        }
                    } catch (Exception e) {
                        serie.setDataTermino(null);
                    }

                    JsonNode networkNode = showNode.path("network");
                    if (networkNode.has("name") && !networkNode.path("name").isNull()) {
                        serie.setEmissora(networkNode.path("name").asText());
                    } else {
                        serie.setEmissora("N/A");
                    }

                    series.add(serie);
                }
            } else {
                System.err.println("A resposta da API de busca não é um array esperado. Verifique a estrutura do JSON.");
            }
        } catch (Exception e) {
            System.err.println("Erro crítico ao parsear JSON de séries: " + e.getMessage());
            e.printStackTrace();
        }
        return series;
    }
}