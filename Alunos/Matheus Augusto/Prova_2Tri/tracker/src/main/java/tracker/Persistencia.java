package tracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia {
    private static final String FILE_PATH = "usuario.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Usuario carregarUsuario() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static void salvarUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}
