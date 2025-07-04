import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RespostaBuscaShow {
    public static final Show show = null;
    private int id;
    private String nome;
    private String idioma;
    private String[] generos;
    private Double nota;
    private String status;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;
    private String resumo;

    public boolean favorita = false;
    public boolean jaAssistida = false;
    public boolean assistirDepois = false;

    public RespostaBuscaShow(int id, String nome, String idioma, String[] generos, Double nota, String status, String dataEstreia, String dataTermino, String emissora, String resumo) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
        this.resumo = resumo;
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
    public String[] getGeneros() {
        return generos;
    }
    public Double getNota() {
        return nota;
    }
    public String getStatus() {
        return status;
    }
    public String getEmissora() {
        return emissora;
    }
    public String getResumo() {
        return resumo;
    }

    public LocalDate getDataPublicacaoComoData() {
    try {
        if (dataEstreia == null || dataEstreia.isEmpty()) {
            return LocalDate.MIN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.dataEstreia, formatter);
    } catch (Exception e) {
        return LocalDate.MIN;
    }
}


    public void setFavorita(boolean favorita) {
        this.favorita = favorita;
    }
    public void setJaAssistida(boolean jaAssistida) {
        this.jaAssistida = jaAssistida;
    }
    public void setAssistirDepois(boolean assistirDepois) {
        this.assistirDepois = assistirDepois;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Título: " + nome + "\n" +
               "Idioma: " + idioma + "\n" +
               "Gêneros: " + String.join(", ", generos) + "\n" +
               "Nota: " + (nota != null ? nota : "Sem nota") + "\n" +
               "Status: " + status + "\n" +
               "Data de Estreia: " + (dataEstreia != null ? dataEstreia : "Indefinida") + "\n" +
               "Data de Término: " + (dataTermino != null ? dataTermino : "Indefinida") + "\n" +
               "Emissora: " + (emissora != null ? emissora : "Indefinida") + "\n" +
               "Resumo: " + resumo + "\n" +
               "Favorita: " + favorita + "\n" +
               "Já Assistida: " + jaAssistida + "\n" +
               "Assistir Depois: " + assistirDepois + "\n" +
               "----------------------------------------";
    }
}