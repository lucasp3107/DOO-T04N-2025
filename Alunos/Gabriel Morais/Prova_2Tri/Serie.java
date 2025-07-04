import java.util.List;

public class Serie {
    public int id;
    public String nome;
    public String idioma;
    public List<String> generos;
    public double nota;
    public String status;
    public String dataEstreia;
    public String dataTermino;
    public String emissora;

    public String toString() {
        return String.format("""
Nome: %s
Idioma: %s
Generos: %s
Nota: %.1f
Status: %s
Estreia: %s
Termino: %s
Emissora: %s
""", nome, idioma, generos, nota, status,
            dataEstreia != null ? dataEstreia : "N/A",
            dataTermino != null ? dataTermino : "N/A",
            emissora != null ? emissora : "N/A");
    }
}