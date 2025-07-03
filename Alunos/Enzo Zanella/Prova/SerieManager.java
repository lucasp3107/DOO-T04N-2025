import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.minhas_series_tv.model.Serie;
import com.minhas_series_tv.model.Usuario;

public class SerieManager {
    private Usuario usuario;
    private TVMazeApiService apiService;
    private ConcurrentMap<Integer, Serie> cacheSeries;

    public SerieManager(Usuario usuario, TVMazeApiService apiService) {
        this.usuario = usuario;
        this.apiService = apiService;
        this.cacheSeries = new ConcurrentHashMap<>();
        carregarSeriesDoCacheInicialmente();
    }

    private void carregarSeriesDoCacheInicialmente() {
        List<Integer> allSeriesIds = new ArrayList<>();
        allSeriesIds.addAll(usuario.getSeriesFavoritasIds());
        allSeriesIds.addAll(usuario.getSeriesAssistidasIds());
        allSeriesIds.addAll(usuario.getSeriesDesejaAssistirIds());

        for (Integer serieId : allSeriesIds) {
            if (!cacheSeries.containsKey(serieId)) {
                try {
                    Serie serie = apiService.buscarSeriePorId(serieId);
                    if (serie != null) {
                        cacheSeries.put(serie.getId(), serie);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar série ID " + serieId + " para o cache: " + e.getMessage());
                }
            }
        }
    }

    private Serie getSerieFromCacheOrApi(int serieId) {
        Serie serie = cacheSeries.get(serieId);
        if (serie != null) {
            return serie;
        }

        try {
            serie = apiService.buscarSeriePorId(serieId);
            if (serie != null) {
                cacheSeries.put(serie.getId(), serie);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar série ID " + serieId + " da API: " + e.getMessage());
            serie = null;
        }
        return serie;
    }

    public boolean adicionarSerieFavoritos(Serie serie) {
        if (serie == null) return false;
        if (!usuario.getSeriesFavoritasIds().contains(serie.getId())) {
            usuario.adicionarSerieFavorita(serie.getId());
            cacheSeries.put(serie.getId(), serie);
            return true;
        }
        return false;
    }

    public boolean removerSerieFavoritos(int serieId) {
        if (usuario.getSeriesFavoritasIds().remove(Integer.valueOf(serieId))) {
            System.out.println("Série removida dos favoritos.");
            return true;
        }
        System.out.println("Série não encontrada nos favoritos.");
        return false;
    }

    public boolean adicionarSerieAssistida(Serie serie) {
        if (serie == null) return false;
        if (!usuario.getSeriesAssistidasIds().contains(serie.getId())) {
            usuario.adicionarSerieAssistida(serie.getId());
            cacheSeries.put(serie.getId(), serie);
            return true;
        }
        return false;
    }

    public boolean removerSerieAssistida(int serieId) {
        if (usuario.getSeriesAssistidasIds().remove(Integer.valueOf(serieId))) {
            System.out.println("Série removida das assistidas.");
            return true;
        }
        System.out.println("Série não encontrada nas assistidas.");
        return false;
    }

    public boolean adicionarSerieDesejaAssistir(Serie serie) {
        if (serie == null) return false;
        if (!usuario.getSeriesDesejaAssistirIds().contains(serie.getId())) {
            usuario.adicionarSerieDesejaAssistir(serie.getId());
            cacheSeries.put(serie.getId(), serie);
            return true;
        }
        return false;
    }

    public boolean removerSerieDesejaAssistir(int serieId) {
        if (usuario.getSeriesDesejaAssistirIds().remove(Integer.valueOf(serieId))) {
            System.out.println("Série removida da lista 'Deseja Assistir'.");
            return true;
        }
        System.out.println("Série não encontrada na lista 'Deseja Assistir'.");
        return false;
    }

    public List<Serie> getListaFavoritos() {
        return getSeriesFromIds(usuario.getSeriesFavoritasIds());
    }

    public List<Serie> getListaAssistidas() {
        return getSeriesFromIds(usuario.getSeriesAssistidasIds());
    }

    public List<Serie> getListaDesejoAssistir() {
        return getSeriesFromIds(usuario.getSeriesDesejaAssistirIds());
    }

    private List<Serie> getSeriesFromIds(List<Integer> ids) {
        List<Serie> seriesList = new ArrayList<>();
        for (Integer id : ids) {
            Serie serie = getSerieFromCacheOrApi(id);
            if (serie != null) {
                seriesList.add(serie);
            }
        }
        return seriesList;
    }

    public void ordenarLista(List<Serie> lista, String criterio) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("A lista está vazia, não há o que ordenar.");
            return;
        }

        Comparator<Serie> comparator = null;
        switch (criterio.toLowerCase()) {
            case "nome":
                comparator = Comparator.comparing(Serie::getNome, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
            case "nota":
                comparator = Comparator.comparingDouble(Serie::getNotaGeral).reversed();
                break;
            case "status":
                comparator = Comparator.comparing(Serie::getStatus, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
            case "dataestreia":
                comparator = Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(LocalDate::compareTo));
                break;
            default:
                System.out.println("Critério de ordenação inválido. Ordenando por nome (padrão).");
                comparator = Comparator.comparing(Serie::getNome, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
        }
        Collections.sort(lista, comparator);
    }
}