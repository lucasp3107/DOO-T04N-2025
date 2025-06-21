package com.rastreadorseries.app;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TvMazeAPI {
    private static final String BASE_URL = "https://api.tvmaze.com";
    private final OkHttpClient httpClient;
    private final Gson gson;

    public TvMazeAPI() {
        this.httpClient = new OkHttpClient.Builder()
                .readTimeout(Duration.ofSeconds(15))
                .build();
        this.gson = new Gson();
    }

    public List<Serie> buscarSeriesPorNome(String nome) throws IOException, JsonParseException {
        String encodedName = URLEncoder.encode(nome, StandardCharsets.UTF_8.toString());
        String url = String.format("%s/search/shows?q=%s", BASE_URL, encodedName);

        System.out.println("Buscando séries na API TVMaze para: " + nome + "...");

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro na API TVMaze: " + response.code() + " - " + response.message());
            }

            if (response.body() == null) {
                throw new IOException("Resposta da API TVMaze vazia.");
            }

            String jsonResponse = response.body().string();
            JsonArray results = gson.fromJson(jsonResponse, JsonArray.class);

            List<Serie> seriesEncontradas = new ArrayList<>();
            for (JsonElement element : results) {
                JsonObject showObject = element.getAsJsonObject().getAsJsonObject("show");
                seriesEncontradas.add(Serie.fromJson(showObject));
            }
            return seriesEncontradas;

        } catch (IOException e) {
            System.err.println("Erro de comunicação ao buscar séries: " + e.getMessage());
            throw e;
        } catch (JsonParseException e) {
            System.err.println("Erro ao processar JSON da API TVMaze: " + e.getMessage());
            throw e;
        }
    }
}