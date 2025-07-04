import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TvMazeApiClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private static class ShowWrapper {
        Serie show;
    }

    public List<Serie> buscarSeries(String nome) throws Exception {
        String query = URLEncoder.encode(nome, StandardCharsets.UTF_8);
        String url = "https://api.tvmaze.com/search/shows?q=" + query;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Falha na busca da API: " + response.statusCode());
        }

        List<ShowWrapper> showWrappers = gson.fromJson(response.body(), new TypeToken<List<ShowWrapper>>(){}.getType());

        List<Serie> series = new ArrayList<>();
        for (ShowWrapper wrapper : showWrappers) {
            series.add(wrapper.show);
        }
        return series;
    }
}