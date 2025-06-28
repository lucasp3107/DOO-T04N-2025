import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejaAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarFavorito(Serie serie) {
        favoritos.add(serie);
    }

    public void adicionarAssistida(Serie serie) {
        assistidas.add(serie);
    }

    public void adicionarDesejaAssistir(Serie serie) {
        desejaAssistir.add(serie);
    }

    public void mostrarListas() {
        System.out.println("\n=== Favoritos ===");
        favoritos.forEach(s -> System.out.println(s.getNome()));
        System.out.println("\n=== Assistidas ===");
        assistidas.forEach(s -> System.out.println(s.getNome()));
        System.out.println("\n=== Deseja Assistir ===");
        desejaAssistir.forEach(s -> System.out.println(s.getNome()));
    }
}
