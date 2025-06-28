import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class Erros {
    private static final String CAMINHO = "dados.json";

    public static void salvarDados(Usuario usuario) {
        try (Writer writer = new FileWriter(CAMINHO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static Usuario carregarDados(String nome) {
        if (!Files.exists(Path.of(CAMINHO))) return new Usuario(nome);
        try (Reader reader = new FileReader(CAMINHO)) {
            Gson gson = new Gson();
            Usuario user = gson.fromJson(reader, Usuario.class);
            return user != null ? user : new Usuario(nome);
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            return new Usuario(nome);
        }
    }
}
