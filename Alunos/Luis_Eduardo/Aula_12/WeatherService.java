import com.google.gson.*;
import java.io.*;
import java.net.*;

public class WeatherService {
    private static final String API_KEY = "4PDL6CAZ5ESLJU4ZEWPWZD9ZA";

    public static WeatherInfo buscarClima(String cidade) throws IOException {
        String urlStr = String.format(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
            URLEncoder.encode(cidade, "UTF-8"), API_KEY
        );

        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            resposta.append(linha);
        }
        reader.close();

        JsonObject json = JsonParser.parseString(resposta.toString()).getAsJsonObject();
        JsonObject hoje = json.getAsJsonArray("days").get(0).getAsJsonObject();

        double tempAtual = json.get("currentConditions").getAsJsonObject().get("temp").getAsDouble();
        double tempMax = hoje.get("tempmax").getAsDouble();
        double tempMin = hoje.get("tempmin").getAsDouble();
        double umidade = json.get("currentConditions").getAsJsonObject().get("humidity").getAsDouble();
        String condicao = json.get("currentConditions").getAsJsonObject().get("conditions").getAsString();
        double precipitacao = hoje.has("precip") ? hoje.get("precip").getAsDouble() : 0.0;
        double ventoVel = json.get("currentConditions").getAsJsonObject().get("windspeed").getAsDouble();
        String ventoDir = json.get("currentConditions").getAsJsonObject().get("winddir").getAsString();

        return new WeatherInfo(tempAtual, tempMax, tempMin, umidade, condicao, precipitacao, ventoVel, ventoDir);
    }
}