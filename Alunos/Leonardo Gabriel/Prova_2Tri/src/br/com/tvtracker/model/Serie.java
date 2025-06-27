package br.com.tvtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Serie {

    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String status;
    private String estreia;
    private String fim;
    private String emissora;

    public Serie() {
    }

    public Serie(int id, String nome, String idioma, List<String> generos, double nota, String status, String estreia, String fim, String emissora) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.estreia = estreia;
        this.fim = fim;
        this.emissora = emissora;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public double getNota() {
        return nota;
    }

    public String getStatus() {
        return status;
    }

    public String getEstreia() {
        return estreia;
    }

    public String getFim() {
        return fim;
    }

    public String getEmissora() {
        return emissora;
    }

    @Override
    public String toString() {
        return "╔══════════════════════════════════════════════╗\n" +
                "Série: " + nome + "\n" +
                "Idioma: " + idioma + "\n" +
                "Gêneros: " + generos + "\n" +
                "Nota: " + nota + "\n" +
                "Status: " + status + "\n" +
                "Estreia: " + estreia + "\n" +
                "Final: " + (fim == null ? "Ainda em exibição" : fim) + "\n" +
                "Emissora: " + emissora + "\n" +
                "╚══════════════════════════════════════════════╝";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Serie)) return false;
        Serie serie = (Serie) o;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
