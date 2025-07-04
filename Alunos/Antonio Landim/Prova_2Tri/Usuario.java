package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> paraAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.paraAssistir = new ArrayList<>();
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

    public List<Serie> getParaAssistir() {
        return paraAssistir;
    }
}
