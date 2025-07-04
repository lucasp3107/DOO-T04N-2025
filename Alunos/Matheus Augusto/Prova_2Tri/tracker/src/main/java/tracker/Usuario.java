package tracker;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejaAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejaAssistir() {
        return desejaAssistir;
    }

    public void adicionarSerie(Serie serie, String lista) {
        switch (lista.toLowerCase()) {
            case "favoritas" -> favoritas.add(serie);
            case "assistidas" -> assistidas.add(serie);
            case "deseja assistir" -> desejaAssistir.add(serie);
            default -> System.out.println("Lista inválida!");
        }
    }

    public void removerSerie(String nomeSerie, String lista) {
        switch (lista.toLowerCase()) {
            case "favoritas" -> favoritas.removeIf(s -> s.getNome().equalsIgnoreCase(nomeSerie));
            case "assistidas" -> assistidas.removeIf(s -> s.getNome().equalsIgnoreCase(nomeSerie));
            case "deseja assistir" -> desejaAssistir.removeIf(s -> s.getNome().equalsIgnoreCase(nomeSerie));
            default -> System.out.println("Lista inválida!");
        }
    }

    public void exibirLista(String nomeLista) {
        List<Serie> lista = switch (nomeLista.toLowerCase()) {
            case "favoritas" -> favoritas;
            case "assistidas" -> assistidas;
            case "deseja assistir" -> desejaAssistir;
            default -> null;
        };

        if (lista == null) {
            System.out.println("Lista inválida!");
            return;
        }

        if (lista.isEmpty()) {
            System.out.println("A lista " + nomeLista + " está vazia.");
            return;
        }

        System.out.println("Exibindo a lista: " + nomeLista);
        for (Serie serie : lista) {
            System.out.println("\n" + serie.toString());
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", favoritas=" + favoritas +
                ", assistidas=" + assistidas +
                ", desejaAssistir=" + desejaAssistir +
                '}';
    }
}

