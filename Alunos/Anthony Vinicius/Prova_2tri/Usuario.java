package com.rastreadorseries.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas;
    private List<Serie> jaAssistidas;
    private List<Serie> desejaAssistir;

    public Usuario() {
        this.favoritas = new ArrayList<>();
        this.jaAssistidas = new ArrayList<>();
        this.desejaAssistir = new ArrayList<>();
    }

    public Usuario(String nome) {
        this();
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Serie> getFavoritas() { return favoritas; }
    public List<Serie> getJaAssistidas() { return jaAssistidas; }
    public List<Serie> getDesejaAssistir() { return desejaAssistir; }

    public boolean adicionarSerie(List<Serie> lista, Serie serie) {
        if (!lista.contains(serie)) {
            return lista.add(serie);
        }
        return false;
    }

    public boolean removerSerie(List<Serie> lista, Serie serie) {
        return lista.remove(serie);
    }

    public void ordenarLista(List<Serie> lista, String criterio) {
        switch (criterio.toLowerCase()) {
            case "nome":
                Collections.sort(lista);
                break;
            case "nota":
                lista.sort(Comparator.comparing(Serie::getRating, Comparator.nullsLast(Double::compareTo)).reversed());
                break;
            case "status":
                lista.sort(Comparator.comparing(Serie::getStatus));
                break;
            case "estreia":
                lista.sort(Comparator.comparing(Serie::getPremieredDate, Comparator.nullsLast(LocalDate::compareTo)));
                break;
            default:
                System.out.println("Critério de ordenação inválido.");
        }
    }
}