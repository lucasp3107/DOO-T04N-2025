import java.util.*;

public class Usuario {
    public String nome;
    public List<Serie> favoritas = new ArrayList<>();
    public List<Serie> assistidas = new ArrayList<>();
    public List<Serie> desejoAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }
}