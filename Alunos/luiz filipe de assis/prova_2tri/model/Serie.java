package model;

import java.util.List;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;
    private String sinopse;

    public Serie() {}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }
    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }
    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public String getDataTermino() {
        return dataTermino;
    }
    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }
    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    public String getSinopse() {
        return sinopse;
    }
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}
