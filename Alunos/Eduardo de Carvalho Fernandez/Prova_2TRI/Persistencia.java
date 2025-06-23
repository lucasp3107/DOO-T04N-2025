

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia {
    private static final String CAMINHO_ARQUIVO = "usuario.json";

    public static void salvar(Usuario usuario) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static Usuario carregar() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            return null;
        }
    }
}
