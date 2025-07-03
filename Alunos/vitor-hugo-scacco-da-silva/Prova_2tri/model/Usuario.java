package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> jaAssistidas;
    private List<Serie> desejoAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritos = new ArrayList<>();
        this.jaAssistidas = new ArrayList<>();
        this.desejoAssistir = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public List<Serie> getJaAssistidas() {
        return jaAssistidas;
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }
}