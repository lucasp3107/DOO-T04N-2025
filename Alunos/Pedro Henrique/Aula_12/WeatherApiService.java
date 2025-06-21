package com.previsao_do_tempo.service;

import com.google.gson.Gson;
import com.previsao_do_tempo.model.Clima;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherApiService {
    private static final String API_KEY = "UQU6G58GXDHZGM2Y4RRJGUD5H";

    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private Gson gson;

    public WeatherApiService() {
        this.gson = new Gson();
        if ("UQU6G58GXDHZGM2Y4RRJGUD5H".equals(API_KEY)) {
            System.err.println("ATENÇÃO: A CHAVE DE API PODE SER A PADRÃO. VERIFIQUE WeatherApiService.java.");
        }
    }

    public Clima getClimaParaCidade(String cidade) throws Exception {
        String encodedCity = URLEncoder.encode(cidade, StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + encodedCity + "/today?unitGroup=metric&include=current%2Cdays&key=" + API_KEY + "&contentType=json";

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "PrevisaoDoTempoApp/1.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return gson.fromJson(response.toString(), Clima.class);
            } else {
                String errorMsg = "Erro ao consultar clima. Código de resposta HTTP: " + responseCode;
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    if (!errorResponse.isEmpty()) {
                        errorMsg += "\nMensagem da API: " + errorResponse.toString();
                    }
                } catch (Exception e) { /* Ignorar */ }
                throw new Exception(errorMsg);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) { /* Ignorar */ }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}