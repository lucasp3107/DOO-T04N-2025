package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class Serie {
    private final int id;
    private final String nome;
    private final String idioma;
    private final List<String> generos;
    private final Double nota; 
    private final String status;
    private final LocalDate dataEstreia; 
    private final LocalDate dataTermino; 
    private final String emissora; 

    public Serie(int id, String nome, String idioma, List<String> generos, Double nota, String status, LocalDate dataEstreia, LocalDate dataTermino, String emissora) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
    }


    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getIdioma() { return idioma; }
    public List<String> getGeneros() { return generos; }
    public Double getNota() { return nota; }
    public String getStatus() { return status; }
    public LocalDate getDataEstreia() { return dataEstreia; }
    public LocalDate getDataTermino() { return dataTermino; }
    public String getEmissora() { return emissora; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}