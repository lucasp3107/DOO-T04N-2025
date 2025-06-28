package com.arthur.tvshowtracker.app;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Serie implements Comparable<Serie> {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private Double avaliacaoGeral;
    private String status;
    private LocalDate dataEstreia;
    private LocalDate dataFim;
    private String emissora;

    public Serie(int id, String nome, String idioma, List<String> generos, Double avaliacaoGeral,
                 String status, LocalDate dataEstreia, LocalDate dataFim, String emissora) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.avaliacaoGeral = avaliacaoGeral;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataFim = dataFim;
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

    public Double getAvaliacaoGeral() {
        return avaliacaoGeral;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDataEstreia() {
        return dataEstreia;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public void setAvaliacaoGeral(Double avaliacaoGeral) {
        this.avaliacaoGeral = avaliacaoGeral;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataEstreia(LocalDate dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    @Override
    public int compareTo(Serie outraSerie) {
        if (this.nome == null && outraSerie.nome == null) return 0;
        if (this.nome == null) return -1;
        if (outraSerie.nome == null) return 1;
        return this.nome.compareToIgnoreCase(outraSerie.nome);
    }

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

    @Override
    public String toString() {
        return "Série: " + nome + "\n" +
                "ID: " + id + "\n" +
                "Idioma: " + idioma + "\n" +
                "Gênero(s): " + (generos != null ? String.join(", ", generos) : "N/A") + "\n" +
                "Avaliação: " + (avaliacaoGeral != null ? String.format("%.1f", avaliacaoGeral) : "N/A") + "\n" +
                "Status: " + status + "\n" +
                "Data de Estreia: " + (dataEstreia != null ? dataEstreia.toString() : "N/A") + "\n" +
                "Data de Término: " + (dataFim != null ? dataFim.toString() : "N/A") + "\n" +
                "Emissora: " + (emissora != null ? emissora : "N/A");
    }

    public String toStringCurto() {
        return nome + " (" + (dataEstreia != null ? dataEstreia.getYear() : "N/A") + ")";
    }
}