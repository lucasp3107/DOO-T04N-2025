package PROVA_2TRI.Repository;

import PROVA_2TRI.Entities.Serie;
import PROVA_2TRI.Interfaces.ISeriesApi;
import PROVA_2TRI.Mappers.TvAmaze.SerieMapper;
import PROVA_2TRI.Interfaces.IPersist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class SerieRepository {
    IPersist persist;
    ISeriesApi serieApi;
    Gson gson;
    public SerieRepository(IPersist persist, ISeriesApi seriesApi,Gson gson) {
        this.persist = persist;
        this.serieApi = seriesApi;
        this.gson = gson;
    }
    public List<Serie> load() throws Exception {
        String json = persist.load();
        if (json == null || json.isEmpty()) {
            throw new Exception("No data found");
        }
        Type responseType = new TypeToken<List<Map<String, Object>>>(){}.getType();

        List<Map<String, Object>> items = gson.fromJson(json, responseType); // Desserializa diretamente para uma lista
        return SerieMapper.jsonToSeries(items.toArray());
    }
}
