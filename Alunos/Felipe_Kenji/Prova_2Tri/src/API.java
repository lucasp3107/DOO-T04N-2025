import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class API {
    private static final String BASE_URL = "https://api.tvmaze.com";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public API() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<Dados.Serie> searchShows(String query) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = BASE_URL + "/search/shows?q=" + encodedQuery;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Falha na requisição à API TVMaze. Status: " + response.statusCode() + ", Corpo: " + response.body());
        }

        JsonNode rootNode = objectMapper.readTree(response.body());
        List<Dados.Serie> results = new ArrayList<>();

        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                JsonNode showNode = node.get("show");
                if (showNode != null) {
                    results.add(objectMapper.treeToValue(showNode, Dados.Serie.class));
                }
            }
        }
        return results;
    }

    public Optional<Dados.Serie> getShowById(int id) throws IOException, InterruptedException {
        String url = BASE_URL + "/shows/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return Optional.empty();
        }
        if (response.statusCode() != 200) {
            throw new IOException("Falha na requisição à API TVMaze. Status: " + response.statusCode() + ", Corpo: " + response.body());
        }

        Dados.Serie serie = objectMapper.readValue(response.body(), Dados.Serie.class);
        return Optional.of(serie);
    }
}