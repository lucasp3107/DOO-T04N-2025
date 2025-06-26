
import java.net.*;
import java.util.Scanner;
import java.io.*;
import org.json.*;

public class ClimaApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Aplicativo de Clima ===");
        System.out.print("Digite sua chave da API: ");
        String key = scanner.nextLine().trim();
        if (key.isEmpty()) {
            System.out.println("Insira uma chave de API."); // default para testes
        }

        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine().trim();

        try {
            buscarClima(cidade, key);
        } catch (Exception e) {
            System.out.println("Erro ao buscar dados do clima: " + e.getMessage());
        }

        scanner.close();
    }

    public static void buscarClima(String cidade, String apiKey) throws Exception {
        String urlStr = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
                URLEncoder.encode(cidade, "UTF-8"), apiKey
        );

        HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            response.append(linha);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());
        JSONArray days = json.getJSONArray("days");
        JSONObject hoje = days.getJSONObject(0);

        double temp = hoje.getDouble("temp");
        double tempMax = hoje.getDouble("tempmax");
        double tempMin = hoje.getDouble("tempmin");
        double humidade = hoje.getDouble("humidity");
        String condicao = hoje.getString("conditions");
        double precipitacao = hoje.getDouble("precip");
        double ventoVel = hoje.getDouble("windspeed");
        double ventoDir = hoje.getDouble("winddir");

        System.out.println("\n--- Clima em " + cidade + " ---");
        System.out.printf("Temperatura atual: %.1f°C\n", temp);
        System.out.printf("Temperatura máxima: %.1f°C\n", tempMax);
        System.out.printf("Temperatura mínima: %.1f°C\n", tempMin);
        System.out.printf("Umidade: %.0f%%\n", humidade);
        System.out.println("Condição do tempo: " + condicao);
        System.out.printf("Precipitação: %.1f mm\n", precipitacao);
        System.out.printf("Vento: %.1f km/h, direção %.1f°\n", ventoVel, ventoDir);

    }
}
