
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static Serie buscarSeriePorNome(String nomeBusca) throws Exception {
        String urlStr = "https://api.tvmaze.com/singlesearch/shows?q=" + nomeBusca.replace(" ", "%20");
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonNode json = new ObjectMapper().readTree(in);
        in.close();

        return new Serie(
                json.get("name").asText(),
                json.get("language").asText(),
                new ObjectMapper().convertValue(json.get("genres"), List.class),
                json.get("rating").get("average").asDouble(),
                json.get("status").asText(),
                json.get("premiered").asText(),
                json.hasNonNull("ended") ? json.get("ended").asText() : null,
                json.get("network") != null && json.get("network").get("name") != null
                ? json.get("network").get("name").asText()
                : "N/A"
        );
    }
}
