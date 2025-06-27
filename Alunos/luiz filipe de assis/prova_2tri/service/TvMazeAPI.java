package service;

import model.Serie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TvMazeAPI {
    private OkHttpClient client;

    public TvMazeAPI() {
        client = new OkHttpClient();
    }

    public List<Serie> buscarSeries(String nome) throws Exception {
        List<Serie> series = new ArrayList<>();
        String url = "https://api.tvmaze.com/search/shows?q=" + nome.replace(" ", "%20");

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Erro na requisição: " + response);
            }
            String jsonData = response.body().string();
            JSONArray resultados = new JSONArray(jsonData);

            for (int i = 0; i < resultados.length(); i++) {
                JSONObject obj = resultados.getJSONObject(i).getJSONObject("show");
                Serie serie = new Serie();

                serie.setNome(obj.optString("name", "N/A"));
                serie.setIdioma(obj.optString("language", "N/A"));

                JSONArray genresJson = obj.optJSONArray("genres");
                List<String> generos = new ArrayList<>();
                if (genresJson != null) {
                    for (int j = 0; j < genresJson.length(); j++) {
                        generos.add(genresJson.getString(j));
                    }
                }
                serie.setGeneros(generos);

                JSONObject rating = obj.optJSONObject("rating");
                double nota = 0.0;
                if (rating != null) {
                    nota = rating.optDouble("average", 0.0);
                }
                serie.setNota(nota);

                serie.setEstado(obj.optString("status", "N/A"));
                serie.setDataEstreia(obj.optString("premiered", "N/A"));
                serie.setDataTermino(obj.optString("ended", "N/A"));

                JSONObject network = obj.optJSONObject("network");
                if (network != null) {
                    serie.setEmissora(network.optString("name", "N/A"));
                } else {
                    serie.setEmissora("N/A");
                }

                serie.setSinopse(obj.optString("summary", "N/A").replaceAll("<[^>]*>", ""));

                series.add(serie);
            }
        }
        return series;
    }
}
