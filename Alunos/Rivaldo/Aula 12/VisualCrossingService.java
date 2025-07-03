package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VisualCrossingService {
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private final HttpClient client = HttpClient.newHttpClient();

    public Clima getClima(String cidade, String apiKey) throws IOException, InterruptedException {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.ISO_DATE);

        String url = BASE_URL + cidade + "/" + dateStr + "?unitGroup=metric&include=days,current&key=" + apiKey + "&contentType=json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();

        double tempAtual = 0.0;
        double tempMax = 0.0;
        double tempMin = 0.0;
        double umidade = 0.0;
        String condicaoTempo = "N/A";
        double precipitacao = 0.0;
        double velocidadeVento = 0.0;
        String direcaoVento = "N/A";

        String currentConditionsJson = extractObjectValue(jsonResponse, "currentConditions");
        if (currentConditionsJson != null) {
            tempAtual = parseDouble(extractValue(currentConditionsJson, "temp"));
            umidade = parseDouble(extractValue(currentConditionsJson, "humidity"));
            condicaoTempo = extractValue(currentConditionsJson, "conditions");
            velocidadeVento = parseDouble(extractValue(currentConditionsJson, "windspeed"));
            direcaoVento = extractValue(currentConditionsJson, "winddir");
        }

        String daysArrayJson = extractArrayValue(jsonResponse, "days");
        if (daysArrayJson != null && !daysArrayJson.isEmpty()) {
            String firstDayJson = extractFirstObjectFromArray(daysArrayJson);
            if (firstDayJson != null) {
                tempMax = parseDouble(extractValue(firstDayJson, "tempmax"));
                tempMin = parseDouble(extractValue(firstDayJson, "tempmin"));
                precipitacao = parseDouble(extractValue(firstDayJson, "precip"));
            }
        }

        return new Clima(tempAtual, tempMax, tempMin, umidade, condicaoTempo, precipitacao, velocidadeVento, direcaoVento);
    }

    private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return null;

        startIndex += searchKey.length();

        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }

        if (startIndex < json.length() && json.charAt(startIndex) == '"') {
            startIndex++;
            int endIndex = json.indexOf('"', startIndex);
            if (endIndex == -1) return null;
            return json.substring(startIndex, endIndex);
        } else {
            int endIndex = startIndex;
            while (endIndex < json.length() && json.charAt(endIndex) != ',' && json.charAt(endIndex) != '}' && json.charAt(endIndex) != ']') {
                endIndex++;
            }
            String value = json.substring(startIndex, endIndex).trim();
            if (value.equals("null")) return null;
            return value;
        }
    }

    private String extractObjectValue(String json, String key) {
        String searchKey = "\"" + key + "\":{";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return null;

        startIndex += searchKey.length();
        int braceCount = 1;
        int endIndex = startIndex;

        while (endIndex < json.length() && braceCount > 0) {
            char c = json.charAt(endIndex);
            if (c == '{') braceCount++;
            else if (c == '}') braceCount--;
            endIndex++;
        }
        if (braceCount != 0) return null;

        return json.substring(startIndex - 1, endIndex);
    }

    private String extractArrayValue(String json, String key) {
        String searchKey = "\"" + key + "\":[";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return null;

        startIndex += searchKey.length();
        int bracketCount = 1;
        int endIndex = startIndex;

        while (endIndex < json.length() && bracketCount > 0) {
            char c = json.charAt(endIndex);
            if (c == '[') bracketCount++;
            else if (c == ']') bracketCount--;
            endIndex++;
        }
        if (bracketCount != 0) return null;

        return json.substring(startIndex, endIndex - 1);
    }

    private String extractFirstObjectFromArray(String jsonArrayContent) {
        int braceCount = 0;
        int startIndex = -1;

        for (int i = 0; i < jsonArrayContent.length(); i++) {
            char c = jsonArrayContent.charAt(i);
            if (c == '{') {
                if (braceCount == 0) {
                    startIndex = i;
                }
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0 && startIndex != -1) {
                    return jsonArrayContent.substring(startIndex, i + 1);
                }
            }
        }
        return null;
    }

    private double parseDouble(String value) {
        if (value == null || value.equals("null") || value.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
