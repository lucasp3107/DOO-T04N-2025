package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> desejoAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejoAssistir = new ArrayList<>();
    }

    // Getters para as listas (retornam uma visão não modificável)
    public String getNome() { return nome; }
    public List<Serie> getFavoritas() { return Collections.unmodifiableList(favoritas); }
    public List<Serie> getAssistidas() { return Collections.unmodifiableList(assistidas); }
    public List<Serie> getDesejoAssistir() { return Collections.unmodifiableList(desejoAssistir); }

    // Métodos para ADICIONAR
    public void adicionarFavorita(Serie serie) {
        if (serie != null && !favoritas.contains(serie)) favoritas.add(serie);
    }
    public void adicionarAssistida(Serie serie) {
        if (serie != null && !assistidas.contains(serie)) assistidas.add(serie);
    }
    public void adicionarDesejoAssistir(Serie serie) {
        if (serie != null && !desejoAssistir.contains(serie)) desejoAssistir.add(serie);
    }

    // Métodos para REMOVER
    public void removerFavorita(Serie serie) { favoritas.remove(serie); }
    public void removerAssistida(Serie serie) { assistidas.remove(serie); }
    public void removerDesejoAssistir(Serie serie) { desejoAssistir.remove(serie); }

    // Métodos para ORDENAR
    public void ordenarFavoritas(Comparator<Serie> comparator) { favoritas.sort(comparator); }
    public void ordenarAssistidas(Comparator<Serie> comparator) { assistidas.sort(comparator); }
    public void ordenarDesejoAssistir(Comparator<Serie> comparator) { desejoAssistir.sort(comparator); }


    public void inicializarListasSeNulas() {
        if (favoritas == null) favoritas = new ArrayList<>();
        if (assistidas == null) assistidas = new ArrayList<>();
        if (desejoAssistir == null) desejoAssistir = new ArrayList<>();
    }
}
