import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nomeOuApelido;
    private List<Integer> seriesFavoritasIds;
    private List<Integer> seriesAssistidasIds;
    private List<Integer> seriesDesejaAssistirIds;

    public Usuario(String nomeOuApelido) {
        this.nomeOuApelido = nomeOuApelido;
        this.seriesFavoritasIds = new ArrayList<>();
        this.seriesAssistidasIds = new ArrayList<>();
        this.seriesDesejaAssistirIds = new ArrayList<>();
    }

    public Usuario() {
        this.seriesFavoritasIds = new ArrayList<>();
        this.seriesAssistidasIds = new ArrayList<>();
        this.seriesDesejaAssistirIds = new ArrayList<>();
    }

    public String getNomeOuApelido() {
        return nomeOuApelido;
    }

    public void setNomeOuApelido(String nomeOuApelido) {
        this.nomeOuApelido = nomeOuApelido;
    }

    public List<Integer> getSeriesFavoritasIds() {
        return seriesFavoritasIds;
    }

    public List<Integer> getSeriesAssistidasIds() {
        return seriesAssistidasIds;
    }

    public List<Integer> getSeriesDesejaAssistirIds() {
        return seriesDesejaAssistirIds;
    }

    public void adicionarSerieFavorita(int serieId) {
        if (!seriesFavoritasIds.contains(serieId)) {
            seriesFavoritasIds.add(serieId);
        }
    }

    public void removerSerieFavorita(int serieId) {
        seriesFavoritasIds.remove(Integer.valueOf(serieId));
    }

    public void adicionarSerieAssistida(int serieId) {
        if (!seriesAssistidasIds.contains(serieId)) {
            seriesAssistidasIds.add(serieId);
        }
    }

    public void removerSerieAssistida(int serieId) {
        seriesAssistidasIds.remove(Integer.valueOf(serieId));
    }

    public void adicionarSerieDesejaAssistir(int serieId) {
        if (!seriesDesejaAssistirIds.contains(serieId)) {
            seriesDesejaAssistirIds.add(serieId);
        }
    }

    public void removerSerieDesejaAssistir(int serieId) {
        seriesDesejaAssistirIds.remove(Integer.valueOf(serieId));
    }
}