package servico;

import modelo.Usuario;
import com.google.gson.*;
import java.io.*;

public class PersistenciaServico {
    private static final String CAMINHO = "dados/series.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter(CAMINHO)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        try (Reader reader = new FileReader(CAMINHO)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado, criando novo usuário.");
            return null;
        }
    }
}
