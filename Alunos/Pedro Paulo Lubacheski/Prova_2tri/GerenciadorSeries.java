
import java.util.*;

public class GerenciadorSeries {

    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejoAssistir = new ArrayList<>();

    public void adicionar(Serie serie, String lista) {
        switch (lista) {
            case "favoritas":
                favoritas.add(serie);
                break;
            case "assistidas":
                assistidas.add(serie);
                break;
            case "desejo":
                desejoAssistir.add(serie);
                break;
        }
    }

    public void remover(String nome, String lista) {
        List<Serie> target = getListaPorNome(lista);
        target.removeIf(s -> s.getNome().equalsIgnoreCase(nome));
    }

    public List<Serie> getListaPorNome(String nome) {
        switch (nome) {
            case "favoritas":
                return favoritas;
            case "assistidas":
                return assistidas;
            case "desejo":
                return desejoAssistir;
            default:
                return new ArrayList<>();
        }
    }

    public void ordenar(List<Serie> lista, String criterio) {
        switch (criterio) {
            case "nome":
                lista.sort(Comparator.comparing(Serie::getNome));
                break;
            case "nota":
                lista.sort(Comparator.comparing(Serie::getNota).reversed());
                break;
            case "status":
                lista.sort(Comparator.comparing(Serie::getStatus));
                break;
            case "estreia":
                lista.sort(Comparator.comparing(Serie::getDataEstreia));
                break;
        }
    }
}
