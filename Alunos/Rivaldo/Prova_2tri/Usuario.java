package com.example;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> paraAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.paraAssistir = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = assistidas;
    }

    public List<Serie> getParaAssistir() {
        return paraAssistir;
    }

    public void setParaAssistir(List<Serie> paraAssistir) {
        this.paraAssistir = paraAssistir;
    }
}
