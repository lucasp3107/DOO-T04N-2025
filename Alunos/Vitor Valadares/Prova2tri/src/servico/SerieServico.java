package servico;

import modelo.Serie;
import com.google.gson.*;
import java.net.*;
import java.io.*;
import java.util.*;
import modelo.TipoLista;
import modelo.Usuario;
import servico.SerieServico;
import servico.PersistenciaServico;
import java.util.Scanner;

public class SerieServico {
    public Serie buscarSeriePorNome(String nome) throws IOException {
        String urlStr = "https://api.tvmaze.com/singlesearch/shows?q=" + URLEncoder.encode(nome, "UTF-8");
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() != 200) {
            throw new IOException("Erro na conexão: " + con.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String resposta = in.readLine();
        in.close();

        JsonObject json = JsonParser.parseString(resposta).getAsJsonObject();
        Serie serie = new Serie();
        serie.setNome(json.get("name").getAsString());
        serie.setIdioma(json.get("language").getAsString());
        serie.setNota(json.get("rating").getAsJsonObject().get("average").isJsonNull() ? 0.0 : json.get("rating").getAsJsonObject().get("average").getAsDouble());
        serie.setStatus(json.get("status").getAsString());
        serie.setEstreia(json.get("premiered").isJsonNull() ? "N/A" : json.get("premiered").getAsString());
        serie.setFim(json.get("ended").isJsonNull() ? "N/A" : json.get("ended").getAsString());
        serie.setEmissora(json.get("network") != null ? json.get("network").getAsJsonObject().get("name").getAsString() : "N/A");

        List<String> generos = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("genres")) {
            generos.add(e.getAsString());
        }
        serie.setGeneros(generos);

        return serie;
    }
}
