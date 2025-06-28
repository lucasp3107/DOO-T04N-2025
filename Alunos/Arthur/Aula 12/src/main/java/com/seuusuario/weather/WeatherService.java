package com.seuusuario.weather;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;

public class WeatherService {
    private static final String URL_TEMPLATE =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        + "%s/today?unitGroup=metric&include=current,days&elements="
        + "temp,tempmin,tempmax,humidity,conditions,precip,windspeed,winddir"
        + "&key=%s";

    private final String apiKey;
    private final HttpClient http;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.http = HttpClient.newHttpClient();
    }

    public WeatherData getWeather(String location) throws IOException, InterruptedException {
        String locEncoded = URLEncoder.encode(location, "UTF-8");
        String url = String.format(URL_TEMPLATE, locEncoded, apiKey);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response =
            http.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error fetching weather: HTTP " + response.statusCode());
        }

        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

        JsonObject current = root.getAsJsonObject("currentConditions");
        double temp          = current.get("temp").getAsDouble();
        double humidity      = current.get("humidity").getAsDouble();
        double windSpeed     = current.get("windspeed").getAsDouble();
        double windDir       = current.get("winddir").getAsDouble();
        String conditions    = current.get("conditions").getAsString();
        double precipitation = current.get("precip").getAsDouble();

        JsonObject today     = root.getAsJsonArray("days")
                                   .get(0).getAsJsonObject();
        double tempMin       = today.get("tempmin").getAsDouble();
        double tempMax       = today.get("tempmax").getAsDouble();

        return new WeatherData(
            temp, tempMin, tempMax,
            humidity, precipitation,
            windSpeed, windDir,
            conditions
        );
    }
}
