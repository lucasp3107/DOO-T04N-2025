package br.com.vinicius.clima;

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

public class WeatherService {

    private static final String API_KEY = "WYQWJETFVYSXP6SBQL445J2BR";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); 
    }

    public Clima getWeatherData(String city) throws IOException, InterruptedException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da cidade não pode ser vazio.");
        }

        String encodedCity = URLEncoder.encode(city.trim(), StandardCharsets.UTF_8.toString());
        String fullUrl = BASE_URL + encodedCity + "/today" +
                         "?unitGroup=metric&include=current,days&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir&key=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return parseWeatherJson(response.body(), city);
        } else {
            String errorMessage = "Erro ao buscar dados do clima: Status " + response.statusCode();
            System.err.println(errorMessage + " - Resposta: " + response.body());
            throw new IOException(errorMessage + ". Verifique a cidade digitada ou sua chave da API.");
        }
    }

    private Clima parseWeatherJson(String jsonResponse, String city) {
        Clima clima = new Clima();
        clima.setCidade(city);

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode currentConditions = rootNode.path("currentConditions");
            if (!currentConditions.isMissingNode() && !currentConditions.isNull()) {
                clima.setTemperaturaAtual(currentConditions.path("temp").asDouble());
                clima.setUmidade(currentConditions.path("humidity").asDouble());
                clima.setCondicaoTempo(currentConditions.path("conditions").asText());
                clima.setVelocidadeVento(currentConditions.path("windspeed").asDouble());
                clima.setDirecaoVento(convertWindDirection(currentConditions.path("winddir").asDouble())); 
            }

            JsonNode days = rootNode.path("days");
            if (days.isArray() && days.size() > 0) {
                JsonNode todayData = days.get(0); 
                clima.setTemperaturaMaxima(todayData.path("tempmax").asDouble());
                clima.setTemperaturaMinima(todayData.path("tempmin").asDouble());
                clima.setPrecipitacao(todayData.path("precip").asDouble(0.0)); 
            } else {
                System.err.println("Aviso: Dados diários não encontrados no JSON.");
            }

        } catch (Exception e) {
            System.err.println("Erro crítico ao parsear JSON do clima: " + e.getMessage());
            e.printStackTrace();
            return null; 
        }
        return clima;
    }

    private String convertWindDirection(double degrees) {
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int index = (int) ((degrees + 11.25) / 22.5) % 16;
        return directions[index];
    }
}