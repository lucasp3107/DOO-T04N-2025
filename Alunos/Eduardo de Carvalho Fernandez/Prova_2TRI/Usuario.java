

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> desejadas;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejadas = new ArrayList<>();
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

    public List<Serie> getDesejadas() {
        return desejadas;
    }

    public void adicionar(List<Serie> lista, Serie serie) {
        if (!lista.contains(serie)) {
            lista.add(serie);
        }
    }

    public void remover(List<Serie> lista, Serie serie) {
        lista.remove(serie);
    }
}
