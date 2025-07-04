import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerieRepository {
    private final String FILE = "usuario.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter(FILE)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        try (FileReader reader = new FileReader(FILE)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            System.out.println("Nenhum usuário salvo encontrado, criando novo.");
            return null;
        }
    }
}
