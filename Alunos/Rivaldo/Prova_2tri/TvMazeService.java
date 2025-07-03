package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TvMazeService {
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";
    private final HttpClient client = HttpClient.newHttpClient();

    public List<Serie> searchSeries(String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + query))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();

        List<Serie> series = new ArrayList<>();
        String cleanedJson = jsonResponse.trim();
        if (cleanedJson.startsWith("[") && cleanedJson.endsWith("]")) {
            cleanedJson = cleanedJson.substring(1, cleanedJson.length() - 1);
        }

        String[] showWrappers = splitJsonArray(cleanedJson);

        for (String wrapperJson : showWrappers) {
            String showJson = extractObjectValue(wrapperJson, "show");
            if (showJson == null) continue;

            String name = extractValue(showJson, "name");
            String language = extractValue(showJson, "language");

            List<String> genres = new ArrayList<>();
            String genresArrayString = extractArrayValue(showJson, "genres");
            if (genresArrayString != null && !genresArrayString.isEmpty()) {
                String[] genreTokens = genresArrayString.split(",");
                for (String token : genreTokens) {
                    genres.add(token.trim().replace("\"", ""));
                }
            }

            Rating rating = null;
            String ratingJson = extractObjectValue(showJson, "rating");
            if (ratingJson != null) {
                String averageStr = extractValue(ratingJson, "average");
                try {
                    double average = (averageStr != null && !averageStr.equals("null")) ? Double.parseDouble(averageStr) : 0.0;
                    rating = new Rating();
                    rating.setAverage(average);
                } catch (NumberFormatException e) {
                }
            }

            String status = extractValue(showJson, "status");
            String premiered = extractValue(showJson, "premiered");
            String ended = extractValue(showJson, "ended");

            Network network = null;
            String networkJson = extractObjectValue(showJson, "network");
            if (networkJson != null) {
                String networkName = extractValue(networkJson, "name");
                network = new Network();
                network.setName(networkName);
            }

            series.add(new Serie(name, language, genres, rating, status, premiered, ended, network));
        }

        return series;
    }

    private String[] splitJsonArray(String jsonArrayContent) {
        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        int startIndex = 0;

        for (int i = 0; i < jsonArrayContent.length(); i++) {
            char c = jsonArrayContent.charAt(i);
            if (c == '{') {
                if (braceCount == 0) {
                    startIndex = i;
                }
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    objects.add(jsonArrayContent.substring(startIndex, i + 1));
                }
            }
        }
        return objects.toArray(new String[0]);
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
}
