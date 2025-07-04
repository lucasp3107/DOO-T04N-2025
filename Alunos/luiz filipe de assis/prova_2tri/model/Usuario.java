package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejoAssistir = new ArrayList<>();

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Serie> getFavoritos() { return favoritos; }
    public List<Serie> getAssistidas() { return assistidas; }
    public List<Serie> getDesejoAssistir() { return desejoAssistir; }

    public void adicionarSerie(String lista, Serie serie) {
        if (lista.equalsIgnoreCase("favoritos")) favoritos.add(serie);
        else if (lista.equalsIgnoreCase("assistidas")) assistidas.add(serie);
        else if (lista.equalsIgnoreCase("desejo")) desejoAssistir.add(serie);
    }

    public List<Serie> getLista(String nome) {
        if (nome.equalsIgnoreCase("favoritos")) return favoritos;
        if (nome.equalsIgnoreCase("assistidas")) return assistidas;
        if (nome.equalsIgnoreCase("desejo")) return desejoAssistir;
        return new ArrayList<>();
    }
}