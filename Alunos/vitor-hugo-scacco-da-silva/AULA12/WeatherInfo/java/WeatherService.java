import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class WeatherService {
    private final String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeather(String cidade) throws Exception {
        String urlStr = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" 
                      + cidade + "/today?unitGroup=metric&key=" + apiKey + "&include=current";
        URL url = new URL(urlStr);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            resposta.append(linha);
        }
        reader.close();

        JSONObject json = new JSONObject(resposta.toString());
        JSONObject current = json.getJSONObject("currentConditions");
        JSONObject dia = json.getJSONArray("days").getJSONObject(0);

        double temp = current.getDouble("temp");
        double tempMax = dia.getDouble("tempmax");
        double tempMin = dia.getDouble("tempmin");
        double umidade = current.getDouble("humidity");
        String condicao = current.getString("conditions");
        double precip = current.has("precip") ? current.getDouble("precip") : 0;
        double ventoVel = current.getDouble("windspeed");
        String ventoDir = current.getString("winddir") + "°";

        return new WeatherInfo(temp, tempMax, tempMin, umidade, condicao, precip, ventoVel, ventoDir);
    }
}
