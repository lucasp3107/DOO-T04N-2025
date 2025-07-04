package com.meuprojeto.climaap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.IOException;

public class ServicoClima {
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public ServicoClima(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient.Builder()
                .readTimeout(java.time.Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    public Clima obterDadosClimaticos(String cidade) {
        String url = String.format("%s/%s?unitGroup=metric&key=%s&include=current,days&contentType=json",
                                   BASE_URL, cidade, apiKey);

        System.out.println("Consultando informações do clima para: " + cidade + "...");

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                return processarResposta(jsonResponse);
            } else {
                System.err.println("Erro na resposta da API: " + response.code() + " - " + response.message());
                if (response.code() == 401) {
                    System.err.println("Dica: Verifique sua API Key. Pode estar inválida ou expirada.");
                } else if (response.code() == 400) {
                    System.err.println("Dica: Erro na requisição. Verifique o nome da cidade digitado.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de comunicação com a API: " + e.getMessage());
            System.err.println("Dica: Verifique sua conexão com a internet ou se a API está online.");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
        return null;
    }

    private Clima processarResposta(String jsonResponse) {
        try {
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            String localidade = jsonObject.get("resolvedAddress").getAsString();

            JsonObject currentConditions = jsonObject.getAsJsonObject("currentConditions");
            double temperaturaAtual = currentConditions.get("temp").getAsDouble();
            double umidadeAtual = currentConditions.get("humidity").getAsDouble();
            String condicaoTempoAtual = currentConditions.get("conditions").getAsString();
            double velocidadeVentoAtual = currentConditions.get("windspeed").getAsDouble();
            int direcaoVentoGraus = currentConditions.get("winddir").getAsInt();
            String direcaoVentoStr = obterDirecaoVentoString(direcaoVentoGraus);

            JsonArray daysArray = jsonObject.getAsJsonArray("days");
            double tempMaxDia = 0.0;
            double tempMinDia = 0.0;
            double precipitacaoDia = 0.0;

            if (daysArray != null && daysArray.size() > 0) {
                JsonObject todayData = daysArray.get(0).getAsJsonObject();
                tempMaxDia = todayData.get("tempmax").getAsDouble();
                tempMinDia = todayData.get("tempmin").getAsDouble();
                precipitacaoDia = todayData.get("precip").getAsDouble();
            } else {
                tempMaxDia = temperaturaAtual;
                tempMinDia = temperaturaAtual;
            }

            return new Clima(localidade, temperaturaAtual, tempMaxDia, tempMinDia,
                             umidadeAtual, condicaoTempoAtual, precipitacaoDia,
                             velocidadeVentoAtual, direcaoVentoStr);

        } catch (Exception e) {
            System.err.println("Erro ao processar os dados JSON da API: " + e.getMessage());
            return null;
        }
    }

    private String obterDirecaoVentoString(int graus) {
        String[] direcoes = {"Norte", "Nordeste", "Leste", "Sudeste", "Sul", "Sudoeste", "Oeste", "Noroeste"};
        int indice = (int) ((graus + 22.5) / 45) % 8;
        return direcoes[indice];
    }
}