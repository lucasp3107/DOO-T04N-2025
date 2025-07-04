import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String status;
    private String premiered; // data em String para facilitar
    private String ended;     // idem
    private String nomeEmissora;

    public Serie() {
        this.generos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(double notaGeral) {
        this.notaGeral = notaGeral;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataEstreia() {
        if (premiered == null || premiered.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(premiered);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public LocalDate getDataTermino() {
        if (ended == null || ended.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(ended);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getNomeEmissora() {
        return nomeEmissora;
    }

    public void setNomeEmissora(String nomeEmissora) {
        this.nomeEmissora = nomeEmissora;
    }
}
