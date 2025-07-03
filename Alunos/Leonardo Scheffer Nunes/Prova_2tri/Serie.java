import java.util.List;

public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String estado;
    private String dataEstreia;
    private String dataFim;
    private String emissora;

    public Serie(String nome, String idioma, List<String> generos, double nota, String estado, String dataEstreia, String dataFim, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataFim = dataFim;
        this.emissora = emissora;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + generos +
                "\nNota: " + nota +
                "\nEstado: " + estado +
                "\nEstreia: " + dataEstreia +
                "\nFim: " + dataFim +
                "\nEmissora: " + emissora;
    }
}
