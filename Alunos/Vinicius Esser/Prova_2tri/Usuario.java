package br.com.vinicius.prova2tri; 

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejaAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejaAssistir = new ArrayList<>();
    }

    public Usuario() { 
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejaAssistir = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Serie> getFavoritos() { return favoritos; }
    public void setFavoritos(List<Serie> favoritos) { this.favoritos = favoritos; }
    public List<Serie> getAssistidas() { return assistidas; }
    public void setAssistidas(List<Serie> assistidas) { this.assistidas = assistidas; }
    public List<Serie> getDesejaAssistir() { return desejaAssistir; }
    public void setDesejaAssistir(List<Serie> desejaAssistir) { this.desejaAssistir = desejaAssistir; }

    public void adicionarFavorito(Serie serie) { if (!favoritos.contains(serie)) { favoritos.add(serie); } }
    public void removerFavorito(Serie serie) { favoritos.remove(serie); }
    public void adicionarAssistida(Serie serie) { if (!assistidas.contains(serie)) { assistidas.add(serie); } }
    public void removerAssistida(Serie serie) { assistidas.remove(serie); }
    public void adicionarDesejaAssistir(Serie serie) { if (!desejaAssistir.contains(serie)) { desejaAssistir.add(serie); } }
    public void removerDesejaAssistir(Serie serie) { desejaAssistir.remove(serie); }
}