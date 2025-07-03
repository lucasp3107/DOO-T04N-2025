package service;

import com.google.gson.Gson;
import model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class PersistenciaService {

    private static final String ARQUIVO_USUARIO = "usuario.json";
    private Gson gson = new Gson();

    public Usuario carregarUsuario() {
        try (Reader reader = new FileReader(ARQUIVO_USUARIO)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void salvarUsuario(Usuario usuario) {
        try (Writer writer = new FileWriter(ARQUIVO_USUARIO)) {
            gson.toJson(usuario, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuário.");
        }
    }
}