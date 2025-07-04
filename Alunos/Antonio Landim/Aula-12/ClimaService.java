package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.ClimaObj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ClimaService {
    private static final String API_KEY = System.getenv("VISUALCROSSING_API_KEY");

    public ClimaObj buscarClima(String cidade) throws Exception {
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new IllegalStateException("A variável de ambiente VISUALCROSSING_API_KEY não está definida.");
        }

        String urlString = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + cidade + "?unitGroup=metric&key=" + API_KEY + "&include=current";

        URI uri = new URI(urlString);
        URL url = uri.toURL();

        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }
        leitor.close();

        System.out.println("Resposta da API: " + resposta.toString());

        JsonObject json = JsonParser.parseString(resposta.toString()).getAsJsonObject();

        if (!json.has("currentConditions") || json.get("currentConditions").isJsonNull()) {
            throw new IllegalArgumentException("Dados atuais do tempo não disponíveis para a cidade: " + cidade);
        }

        JsonObject currentConditions = json.getAsJsonObject("currentConditions");

        if (!json.has("days") || json.get("days").getAsJsonArray().size() == 0) {
            throw new IllegalArgumentException("Dados do dia não disponíveis para a cidade: " + cidade);
        }

        JsonObject today = json.getAsJsonArray("days").get(0).getAsJsonObject();

        String temp = currentConditions.get("temp").getAsString();
        String tempMax = today.get("tempmax").getAsString();
        String tempMin = today.get("tempmin").getAsString();
        String umidade = currentConditions.get("humidity").getAsString();
        String condicao = currentConditions.get("conditions").getAsString();
        String vento = currentConditions.get("windspeed").getAsString();

        // Tratamento para campo 'precip' que pode ser nulo
        String chuva = "0";
        if (currentConditions.has("precip") && !currentConditions.get("precip").isJsonNull()) {
            chuva = currentConditions.get("precip").getAsString();
        }

        return new ClimaObj(temp, tempMax, tempMin, umidade, condicao, chuva, vento);
    }


}
