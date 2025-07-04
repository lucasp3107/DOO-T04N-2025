package com.serie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos = new ArrayList<>();
    private List<Serie> jaAssistidas = new ArrayList<>();
    private List<Serie> desejoAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    // ---------- FAVORITOS ----------
    public void adicionarFavorito(Serie serie) {
        if (!favoritos.contains(serie)) favoritos.add(serie);
    }

    public void removerFavorito(Serie serie) {
        favoritos.remove(serie);
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    // ---------- JÁ ASSISTIDAS ----------
    public void marcarComoAssistida(Serie serie) {
        if (!jaAssistidas.contains(serie)) jaAssistidas.add(serie);
    }

    public void removerAssistida(Serie serie) {
        jaAssistidas.remove(serie);
    }

    public List<Serie> getJaAssistidas() {
        return jaAssistidas;
    }

    // ---------- DESEJO ASSISTIR ----------
    public void adicionarDesejoAssistir(Serie serie) {
        if (!desejoAssistir.contains(serie)) desejoAssistir.add(serie);
    }

    public void removerDesejoAssistir(Serie serie) {
        desejoAssistir.remove(serie);
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }

    public void ordenarLista(List<Serie> lista, String criterio) {
        switch (criterio.toLowerCase()) {
            case "nome":
                lista.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
                break;
            case "nota":
                lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
                break;
            case "status":
                lista.sort(Comparator.comparing(Serie::getStatus, String.CASE_INSENSITIVE_ORDER));
                break;
            case "estreia":
                lista.sort(Comparator.comparing(Serie::getDataEstreia));
                break;
            default:
                System.out.println("Critério inválido. Sem ordenação.");
        }
    }

}

