
import java.util.regex.Pattern;

public class Usuario {

    private String nome;

    public Usuario(String nome) {
        if (!Pattern.matches("^[A-Za-zÀ-ÿ\\s]{3,30}$", nome)) {
            throw new IllegalArgumentException("Nome inválido. Use apenas letras (3-30 caracteres).");
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Usuário: " + nome;
    }
}
