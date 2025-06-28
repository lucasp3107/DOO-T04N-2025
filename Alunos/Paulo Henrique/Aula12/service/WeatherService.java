package Aula12.service;

import com.google.gson.*;
import Aula12.model.WeatherInfo;
import Aula12.util.ApiKey;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    public WeatherInfo getWeather(String city) {
        try {
            String urlString = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + city.replace(" ", "%20")
                    + "/today?unitGroup=metric&key=" + ApiKey.KEY + "&contentType=json";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro na conexão: " + conn.getResponseCode());
                return null;
            }

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            WeatherInfo info = new WeatherInfo();
            info.city = jsonObject.get("resolvedAddress").getAsString();

            JsonObject today = jsonObject.getAsJsonArray("days").get(0).getAsJsonObject();

            info.temperature = today.get("temp").getAsDouble();
            info.maxTemperature = today.get("tempmax").getAsDouble();
            info.minTemperature = today.get("tempmin").getAsDouble();
            info.humidity = today.get("humidity").getAsDouble();
            info.conditions = today.get("conditions").getAsString();
            info.precipitation = today.get("precip").getAsDouble();
            info.windSpeed = today.get("windspeed").getAsDouble();
            info.windDirection = today.get("winddir").getAsDouble() + "°";

            conn.disconnect();
            return info;

        } catch (Exception e) {
            System.out.println("Erro ao obter informações do tempo: " + e.getMessage());
            return null;
        }
    }
}
