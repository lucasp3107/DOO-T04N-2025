package service;

import model.Serie;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class TvMazeAPI {
    public List<Serie> buscarSerie(String nome) {
        List<Serie> series = new ArrayList<>();
        try {
            String urlStr = "https://api.tvmaze.com/search/shows?q=" + nome.replace(" ", "%20");
            URL url = URI.create(urlStr).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elem : jsonArray) {
                JsonObject show = elem.getAsJsonObject().getAsJsonObject("show");

                String idioma = show.has("language") && !show.get("language").isJsonNull()
                        ? show.get("language").getAsString()
                        : "Desconhecido";

                List<String> generos = show.has("genres") && !show.get("genres").isJsonNull()
                        ? new Gson().fromJson(show.get("genres"), new TypeToken<List<String>>() {}.getType())
                        : List.of();

                double nota = show.has("rating") && show.getAsJsonObject("rating").has("average")
                        && !show.getAsJsonObject("rating").get("average").isJsonNull()
                        ? show.getAsJsonObject("rating").get("average").getAsDouble()
                        : 0.0;

                String status = show.has("status") && !show.get("status").isJsonNull()
                        ? show.get("status").getAsString()
                        : "Desconhecido";

                String dataEstreia = show.has("premiered") && !show.get("premiered").isJsonNull()
                        ? show.get("premiered").getAsString()
                        : "Desconhecido";

                String dataFim = show.has("ended") && !show.get("ended").isJsonNull()
                        ? show.get("ended").getAsString()
                        : null;

                String emissora = "Desconhecido";
                if (show.has("network") && !show.get("network").isJsonNull()) {
                    emissora = show.getAsJsonObject("network").get("name").getAsString();
                } else if (show.has("webChannel") && !show.get("webChannel").isJsonNull()) {
                    emissora = show.getAsJsonObject("webChannel").get("name").getAsString();
                }

                Serie serie = new Serie(
                        show.get("id").getAsInt(),
                        show.get("name").getAsString(),
                        idioma,
                        generos,
                        nota,
                        status,
                        dataEstreia,
                        dataFim,
                        emissora
                );

                series.add(serie);
            }

        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return series;
    }
}
