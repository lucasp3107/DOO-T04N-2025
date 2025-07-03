import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.minhas_series_tv.model.Serie;

public class TVMazeApiService {

    private static final String BASE_URL = "https://api.tvmaze.com";

    public List<Serie> buscarSeriesPorNome(String query) throws Exception {
        List<Serie> resultado = new ArrayList<>();
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + "/search/shows?q=" + encodedQuery;

        String response = fazerRequisicaoGet(urlString);
        if (response == null || response.isEmpty()) {
            return resultado;
        }

        String[] partes = response.split("\\},\\{");

        for (String parte : partes) {
            String showJson = extrairSubJsonShow(parte);
            if (showJson != null) {
                Serie serie = parseSerieFromJson(showJson);
                if (serie != null) {
                    resultado.add(serie);
                }
            }
        }

        return resultado;
    }

    public Serie buscarSeriePorId(int id) throws Exception {
        String urlString = BASE_URL + "/shows/" + id;
        String response = fazerRequisicaoGet(urlString);
        if (response == null || response.isEmpty()) {
            return null;
        }

        return parseSerieFromJson(response);
    }

    private String fazerRequisicaoGet(String urlString) throws Exception {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "MinhasSeriesTvApp/1.0");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    sb.append(linha);
                }
                return sb.toString();
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Recurso não encontrado (404): " + urlString);
                return null;
            } else {
                System.err.println("Erro HTTP " + responseCode + " ao acessar: " + urlString);
                return null;
            }
        } finally {
            if (reader != null) try { reader.close(); } catch (Exception ignored) {}
            if (connection != null) connection.disconnect();
        }
    }

    private String extrairSubJsonShow(String texto) {
        int idxShow = texto.indexOf("\"show\":");
        if (idxShow < 0) {
            return null;
        }
        int start = texto.indexOf("{", idxShow);
        int end = texto.lastIndexOf("}");
        if (start < 0 || end < 0 || end <= start) {
            return null;
        }
        return texto.substring(start, end + 1);
    }

    private Serie parseSerieFromJson(String json) {
        try {
            Serie s = new Serie();

            s.setId(extrairInt(json, "\"id\":"));
            s.setNome(extrairString(json, "\"name\":\""));
            s.setIdioma(extrairString(json, "\"language\":\""));
            s.setStatus(extrairString(json, "\"status\":\""));
            s.setPremiered(extrairString(json, "\"premiered\":\""));
            s.setEnded(extrairString(json, "\"ended\":\""));
            s.setNotaGeral(extrairDouble(json, "\"average\":"));
            s.setGeneros(extrairArrayStrings(json, "\"genres\":"));
            s.setNomeEmissora(extrairEmissora(json));

            return s;
        } catch (Exception e) {
            System.err.println("Erro ao parsear JSON da série: " + e.getMessage());
            return null;
        }
    }

    private String extrairString(String json, String chave) {
        int idx = json.indexOf(chave);
        if (idx < 0) return null;
        int start = idx + chave.length();
        int end = json.indexOf("\"", start);
        if (end < 0) return null;
        return json.substring(start, end);
    }

    private int extrairInt(String json, String chave) {
        int idx = json.indexOf(chave);
        if (idx < 0) return 0;
        int start = idx + chave.length();
        int end = json.indexOf(",", start);
        if (end < 0) end = json.indexOf("}", start);
        String valor = json.substring(start, end).trim();
        return Integer.parseInt(valor);
    }

    private double extrairDouble(String json, String chave) {
        int idx = json.indexOf(chave);
        if (idx < 0) return 0.0;
        int start = idx + chave.length();
        int end = json.indexOf(",", start);
        if (end < 0) end = json.indexOf("}", start);
        String valor = json.substring(start, end).trim();
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private List<String> extrairArrayStrings(String json, String chave) {
        List<String> lista = new ArrayList<>();
        int idx = json.indexOf(chave);
        if (idx < 0) return lista;

        int start = json.indexOf("[", idx);
        int end = json.indexOf("]", start);
        if (start < 0 || end < 0) return lista;

        String arrayConteudo = json.substring(start + 1, end);
        String[] items = arrayConteudo.split(",");
        for (String item : items) {
            item = item.trim();
            if (item.startsWith("\"") && item.endsWith("\"") && item.length() >= 2) {
                lista.add(item.substring(1, item.length() -1));
            }
        }

        return lista;
    }

    private String extrairEmissora(String json) {
        int idxNetwork = json.indexOf("\"network\":");
        if (idxNetwork >= 0) {
            int idxName = json.indexOf("\"name\":\"", idxNetwork);
            if (idxName >= 0) {
                return extrairString(json.substring(idxNetwork), "\"name\":\"");
            }
        }

        int idxWeb = json.indexOf("\"webChannel\":");
        if (idxWeb >= 0) {
            int idxName = json.indexOf("\"name\":\"", idxWeb);
            if (idxName >= 0) {
                return extrairString(json.substring(idxWeb), "\"name\":\"");
            }
        }

        return "N/A";
    }
}
