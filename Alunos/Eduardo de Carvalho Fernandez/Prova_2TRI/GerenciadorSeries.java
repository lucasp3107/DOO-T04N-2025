

import com.google.gson.*;


import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class GerenciadorSeries {

    public static Serie buscarSerie(String nome) throws Exception {
        String urlStr = "https://api.tvmaze.com/singlesearch/shows?q=" + nome.replace(" ", "%20");
        URL url = new URL(urlStr);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        if (conexao.getResponseCode() != 200) {
            throw new RuntimeException("Erro ao buscar série: " + conexao.getResponseCode());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

        int id = json.get("id").getAsInt();
        String nomeSerie = json.get("name").getAsString();
        String idioma = json.get("language").getAsString();
        double nota = json.get("rating").getAsJsonObject().get("average").isJsonNull() ? 0 : json.get("rating").getAsJsonObject().get("average").getAsDouble();
        String status = json.get("status").getAsString();
        String dataEstreia = json.get("premiered").isJsonNull() ? "Desconhecida" : json.get("premiered").getAsString();
        String dataFim = json.get("ended").isJsonNull() ? null : json.get("ended").getAsString();

        List<String> generos = new ArrayList<>();
        JsonArray generosJson = json.getAsJsonArray("genres");
        for (JsonElement genero : generosJson) {
            generos.add(genero.getAsString());
        }

        String emissora = json.has("network") && !json.get("network").isJsonNull()
            ? json.get("network").getAsJsonObject().get("name").getAsString()
            : "Desconhecida";

        return new Serie(id, nomeSerie, idioma, generos, nota, status, dataEstreia, dataFim, emissora);
    }
}
