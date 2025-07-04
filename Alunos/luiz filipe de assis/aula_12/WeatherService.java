//WeatherService.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class WeatherService {
 private static final String API_KEY = "CE7JEMX2KQFK9X978S2PSA6VZ";
 private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

 public WeatherData getWeatherData(String city) throws Exception {
     String jsonResponse = fetchWeatherFromAPI(city);
     return parseWeatherData(jsonResponse);
 }

 private String fetchWeatherFromAPI(String city) throws Exception {
     // Codificar o nome da cidade para URL
     String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
     
     // Construir URL da API
     String urlString = BASE_URL + encodedCity + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";
     
     URL url = new URL(urlString);
     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
     connection.setRequestMethod("GET");
     connection.setConnectTimeout(10000);
     connection.setReadTimeout(10000);
     
     int responseCode = connection.getResponseCode();
     if (responseCode != HttpURLConnection.HTTP_OK) {
         throw new Exception("Erro HTTP: " + responseCode);
     }
     
     BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
     StringBuilder response = new StringBuilder();
     String line;
     
     while ((line = reader.readLine()) != null) {
         response.append(line);
     }
     reader.close();
     
     return response.toString();
 }

 private WeatherData parseWeatherData(String jsonResponse) throws Exception {
     JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
     
     // Informações gerais
     String address = root.get("address").getAsString();
     String description = root.has("description") ? root.get("description").getAsString() : "";
     
     // Dados do dia atual
     JsonArray days = root.getAsJsonArray("days");
     JsonObject today = days.get(0).getAsJsonObject();
     
     // Condições atuais (se disponível)
     JsonObject currentConditions = null;
     if (root.has("currentConditions")) {
         currentConditions = root.getAsJsonObject("currentConditions");
     }
     
     WeatherData weather = new WeatherData();
     weather.setLocation(address);
     weather.setDescription(description);
     
     // Temperatura atual
     if (currentConditions != null && currentConditions.has("temp")) {
         weather.setCurrentTemp(currentConditions.get("temp").getAsDouble());
     } else {
         weather.setCurrentTemp(today.get("temp").getAsDouble());
     }
     
     // Temperaturas máxima e mínima
     weather.setMaxTemp(today.get("tempmax").getAsDouble());
     weather.setMinTemp(today.get("tempmin").getAsDouble());
     
     // Umidade
     if (currentConditions != null && currentConditions.has("humidity")) {
         weather.setHumidity(currentConditions.get("humidity").getAsDouble());
     } else {
         weather.setHumidity(today.get("humidity").getAsDouble());
     }
     
     // Condições do tempo
     if (currentConditions != null && currentConditions.has("conditions")) {
         weather.setConditions(currentConditions.get("conditions").getAsString());
     } else {
         weather.setConditions(today.get("conditions").getAsString());
     }
     
     // Precipitação
     if (today.has("precip") && !today.get("precip").isJsonNull()) {
         weather.setPrecipitation(today.get("precip").getAsDouble());
     }
     
     // Vento
     if (currentConditions != null && currentConditions.has("windspeed")) {
         weather.setWindSpeed(currentConditions.get("windspeed").getAsDouble());
         weather.setWindDirection(currentConditions.get("winddir").getAsDouble());
     } else {
         weather.setWindSpeed(today.get("windspeed").getAsDouble());
         weather.setWindDirection(today.get("winddir").getAsDouble());
     }
     
     return weather;
 }
}