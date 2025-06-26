package PROVA_2TRI.Mappers.TvAmaze;

import PROVA_2TRI.Entities.Serie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerieMapper {
    public static Serie jsonToSerie(Map<String, Object> data){
        Map<String, Object> ratingMap = (Map<String, Object>) data.get("rating");
        Map<String, Object> networkMap = (Map<String, Object>) data.get("network");
        return new Serie(
                (int)Double.parseDouble(data.get("id").toString()),
                (String) data.get("name"),
                (String) data.get("type"),
                (List<String>) data.get("genres"),
                ratingMap != null && ratingMap.get("average") != null ?
                        ((Number) ratingMap.get("average")).doubleValue() : 0.0,
                networkMap != null && networkMap.get("country") != null ?
                        (String) ((Map<String, Object>) networkMap.get("country")).get("name") : "",
                data.get("premiered") != null ? LocalDate.parse((String) data.get("premiered")) : null,
                data.get("ended") != null ? LocalDate.parse((String) data.get("ended")) : null,
                (String) data.get("summary")
        );
    }
    public static List<Serie> jsonToSeries(Object[] data){
       List<Serie> series = new ArrayList<>();

       for (Object obj : data) {
           Map<String, Object> map = (Map<String, Object>) obj;
           series.add(jsonToSerie(map));
       }

         return series;
    }
}
