package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class AppClimaTempo {
        public static void main(String[] args) {
            Locale.setDefault(Locale.US);
            try {
                Dotenv dotenv = Dotenv.load();
                String apiKey = dotenv.get("VISUALCROSSING_API_KEY");

                Scanner leitor = new Scanner(System.in);

                System.out.print("Digite o nome da cidade: ");
                String cidade = leitor.nextLine();

                System.out.print("Agora digite a sigla do estado (ex: PR, SP, RJ): ");
                String estado = leitor.nextLine();

                leitor.close();

                String localizacaoCompleta = cidade + ", " + estado;

                System.out.println("\nBuscando informações para " + localizacaoCompleta + "...");

                String localizacaoCodificada = URLEncoder.encode(localizacaoCompleta, StandardCharsets.UTF_8);
                String urlApi = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + localizacaoCodificada + "?unitGroup=metric&lang=pt&key=" + apiKey;
                HttpClient cliente = HttpClient.newHttpClient();
                HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(urlApi)).build();
                HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

                if (resposta.statusCode() != 200) {
                    System.out.println("Opa, algo deu errado. Não consegui encontrar essa cidade/estado.");
                    return;
                }

                JSONObject json = new JSONObject(resposta.body());
                JSONObject climaAtual = json.getJSONObject("currentConditions");

                String endereco = json.getString("resolvedAddress");
                double temp = climaAtual.getDouble("temp");
                double maxima = json.getJSONArray("days").getJSONObject(0).getDouble("tempmax");
                double minima = json.getJSONArray("days").getJSONObject(0).getDouble("tempmin");
                double umidade = climaAtual.getDouble("humidity");
                String condicao = climaAtual.getString("conditions");
                double chuva = climaAtual.optDouble("precip", 0.0);
                double ventoVelocidade = climaAtual.getDouble("windspeed");
                int ventoDirecao = climaAtual.getInt("winddir");

                System.out.println("\n--- Clima para " + endereco + " ---");
                System.out.printf("Temperatura atual: %.1f°C\n", temp);
                System.out.printf("Condição: %s\n", condicao);
                System.out.printf("Máxima para hoje: %.1f°C\n", maxima);
                System.out.printf("Mínima para hoje: %.1f°C\n", minima);
                System.out.printf("Umidade do ar: %.1f%%\n", umidade);
                if (chuva > 0) {
                    System.out.printf("Precipitação: %.1f mm\n", chuva);
                }
                System.out.printf("Vento: %.1f km/h (direção %d°)\n", ventoVelocidade, ventoDirecao);
                System.out.println("------------------------------------------");

            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado.");
                e.printStackTrace();
            }
        }
}
