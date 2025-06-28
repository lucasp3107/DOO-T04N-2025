package tracker;

import java.util.List;
import java.util.Objects;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;

    public Serie(String nome, String idioma, List<String> generos, double notaGeral,
                 String estado, String dataEstreia, String dataTermino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.notaGeral = notaGeral;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public double getNotaGeral() {
        return notaGeral;
    }

    public String getEstado() {
        return estado;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Serie)) return false;
        Serie serie = (Serie) o;
        return Objects.equals(nome.toLowerCase(), serie.nome.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome.toLowerCase());
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + (generos.isEmpty() ? "Indisponível" : String.join(", ", generos)) +
                "\nNota Geral: " + notaGeral +
                "\nEstado: " + estado +
                "\nData de Estreia: " + dataEstreia +
                "\nData de Término: " + dataTermino +
                "\nEmissora: " + emissora;
    }
}
