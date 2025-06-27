package br.com.vinicius.prova2tri; 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private Double notaGeral;
    private String estado;
    private LocalDate dataEstreia;
    private LocalDate dataTermino;
    private String emissora;

    public Serie() {}

    public Serie(int id, String nome, String idioma, List<String> generos, Double notaGeral, String estado, LocalDate dataEstreia, LocalDate dataTermino, String emissora) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.notaGeral = notaGeral;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public List<String> getGeneros() { return generos; }
    public void setGeneros(List<String> generos) { this.generos = generos; }
    public Double getNotaGeral() { return notaGeral; }
    public void setNotaGeral(Double notaGeral) { this.notaGeral = notaGeral; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getDataEstreia() { return dataEstreia; }
    public void setDataEstreia(LocalDate dataEstreia) { this.dataEstreia = dataEstreia; }
    public LocalDate getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDate dataTermino) { this.dataTermino = dataTermino; }
    public String getEmissora() { return emissora; }
    public void setEmissora(String emissora) { this.emissora = emissora; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String generosStr = (generos != null && !generos.isEmpty()) ? String.join(", ", generos) : "N/A";
        String dataEstreiaStr = (dataEstreia != null) ? dataEstreia.format(formatter) : "N/A";
        String dataTerminoStr = (dataTermino != null) ? dataTermino.format(formatter) : "Em andamento";
        String notaStr = (notaGeral != null) ? String.format("%.1f", notaGeral) : "N/A";

        return "Nome: " + nome +
               "\n  Idioma: " + idioma +
               "\n  Gêneros: " + generosStr +
               "\n  Nota Geral: " + notaStr +
               "\n  Estado: " + estado +
               "\n  Estreia: " + dataEstreiaStr +
               "\n  Término: " + dataTerminoStr +
               "\n  Emissora: " + (emissora != null && !emissora.isEmpty() ? emissora : "N/A");
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
}