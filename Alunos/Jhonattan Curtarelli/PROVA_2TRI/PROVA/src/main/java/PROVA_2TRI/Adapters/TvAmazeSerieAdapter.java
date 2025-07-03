package PROVA_2TRI.Adapters;

import PROVA_2TRI.Entities.Serie;
import PROVA_2TRI.Interfaces.ISeriesApi;
import PROVA_2TRI.Mappers.TvAmaze.SerieMapper;
import PROVA_2TRI.Interfaces.IPersist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class TvAmazeSerieAdapter implements ISeriesApi {
    // Aqui daria para usar variaveis de ambiente para não deixar a URL exposta, mas só para estudo então whatever
    private String URL = "https://api.tvmaze.com/shows";
    private HttpClient client;
    private Gson gson;
    private IPersist persist;
    public TvAmazeSerieAdapter(HttpClient httpClient, Gson gson, IPersist persist) {
        this.client = httpClient;
        this.gson = gson;
        this.persist = persist;
    }
    @Override
    public List<Serie> getAll() throws IOException, InterruptedException, Exception {
        // Prepara a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("accept", "application/json")
                .build();

        // Envia a requisição e obtém a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Desserializa a resposta JSON para uma lista de Mapas (cada item da lista é um Map<String, Object>)
        Type responseType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        String json = response.body();
        List<Map<String, Object>> items = gson.fromJson(json, responseType); // Desserializa diretamente para uma lista

        // Salva o JSON se necessário
        persist.save(json);

        // Converte a lista de Mapas para uma lista de Series usando o mapeamento
        return SerieMapper.jsonToSeries(items.toArray());  // Certifique-se que a função jsonToSeries funcione com um array
    }
}
