import java.net.*;
import java.io.*;
import java.util.*;
import org.json.*;

public class TvMazeAPI {
    public static Serie buscarSeriePorNome(String nome) throws Exception {
        String query = nome.replace(" ", "%20");
        String urlStr = "https://api.tvmaze.com/singlesearch/shows?q=" + query;
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder json = new StringBuilder();
        String linha;
        while ((linha = in.readLine()) != null) json.append(linha);
        in.close();

        JSONObject obj = new JSONObject(json.toString());

        Serie s = new Serie();
        s.id = obj.getInt("id");
        s.nome = obj.getString("name");
        s.idioma = obj.getString("language");
        s.nota = obj.getJSONObject("rating").optDouble("average", 0);
        s.status = obj.getString("status");
        s.dataEstreia = obj.optString("premiered", "N/A");
        s.dataTermino = obj.optString("ended", "N/A");
        s.emissora = obj.has("network") && !obj.isNull("network") ?
                     obj.getJSONObject("network").optString("name", "N/A") : "N/A";

        JSONArray generos = obj.getJSONArray("genres");
        List<String> genList = new ArrayList<>();
        for (int i = 0; i < generos.length(); i++) genList.add(generos.getString(i));
        s.generos = genList;

        return s;
    }
}