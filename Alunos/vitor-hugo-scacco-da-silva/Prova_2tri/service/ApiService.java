package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Serie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiService {

    private static final String BASE_URL = "https://api.tvmaze.com/search/shows?q=";
    private Gson gson = new Gson();

    public List<Serie> buscarSeriePorNome(String nome) throws Exception {
        String urlStr = BASE_URL + nome.replace(" ", "%20");
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        List<ApiResponse> resultados = gson.fromJson(in, new TypeToken<List<ApiResponse>>(){}.getType());
        in.close();

        return resultados.stream().map(ApiResponse::getShow).toList();
    }

    private class ApiResponse {
        private Serie show;

        public Serie getShow() {
            return show;
        }
    }
}