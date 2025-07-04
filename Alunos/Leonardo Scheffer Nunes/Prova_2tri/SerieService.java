import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class SerieService {
    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    public Serie buscarSeriePorNome(String nome) {
        try {
            String url = "https://api.tvmaze.com/singlesearch/shows?q=" + nome.replace(" ", "%20");
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = gson.fromJson(response.body(), JsonObject.class);
                String nomeSerie = json.get("name").getAsString();
                String idioma = json.get("language").getAsString();

                JsonArray genresJson = json.getAsJsonArray("genres");
                List<String> generos = gson.fromJson(genresJson, new TypeToken<List<String>>(){}.getType());

                double nota = json.getAsJsonObject("rating").get("average").isJsonNull() ? 0.0 :
                        json.getAsJsonObject("rating").get("average").getAsDouble();

                String estado = json.get("status").getAsString();
                String dataEstreia = json.get("premiered").isJsonNull() ? "N/A" : json.get("premiered").getAsString();
                String dataFim = json.get("ended").isJsonNull() ? "N/A" : json.get("ended").getAsString();

                String emissora = json.has("network") && !json.get("network").isJsonNull()
                        ? json.getAsJsonObject("network").get("name").getAsString()
                        : "N/A";

                return new Serie(nomeSerie, idioma, generos, nota, estado, dataEstreia, dataFim, emissora);
            } else {
                System.out.println("Série não encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar série: " + e.getMessage());
            return null;
        }
    }
}
