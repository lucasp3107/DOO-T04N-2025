import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class WeatherService {
    private static final String API_KEY = "TCVFEJTKA4XCPF4EXCVESSWW4";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    public Weather getWeather(String cidade) {
        try {
            String url = BASE_URL + cidade.replace(" ", "%20") + "/today?unitGroup=metric&key=" + API_KEY + "&include=current";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = gson.fromJson(response.body(), JsonObject.class);
                String resolvedAddress = json.get("resolvedAddress").getAsString();

                JsonObject currentConditions = json.getAsJsonObject("currentConditions");

                double tempAtual = getDoubleFromJson(currentConditions, "temp");
                double tempMax = getDoubleFromJson(currentConditions, "tempmax");
                double tempMin = getDoubleFromJson(currentConditions, "tempmin");
                double umidade = getDoubleFromJson(currentConditions, "humidity");
                String condicao = getStringFromJson(currentConditions, "conditions");
                double precipitacao = getDoubleFromJson(currentConditions, "precip");
                double velocidadeVento = getDoubleFromJson(currentConditions, "windspeed");
                String direcaoVento = getStringFromJson(currentConditions, "winddir");

                return new Weather(resolvedAddress, tempAtual, tempMax, tempMin, umidade, condicao, precipitacao, velocidadeVento, direcaoVento);
            } else {
                System.out.println("Erro ao buscar dados: Código HTTP " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar a API: " + e.getMessage());
            return null;
        }
    }

    private double getDoubleFromJson(JsonObject obj, String memberName) {
        return obj.has(memberName) && !obj.get(memberName).isJsonNull() ? obj.get(memberName).getAsDouble() : 0.0;
    }

    private String getStringFromJson(JsonObject obj, String memberName) {
        return obj.has(memberName) && !obj.get(memberName).isJsonNull() ? obj.get(memberName).getAsString() : "N/A";
    }
}
