package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class PersistenciaService {

    private static final String CAMINHO_ARQUIVO = "usuario_series.json";
    private static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    public static void salvarUsuario(Usuario usuario) {
        try (Writer writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(usuario, writer);
            System.out.println("Progresso salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("ERRO: Falha ao salvar os dados: " + e.getMessage());
        }
    }

    public static Usuario carregarUsuario() {
        if (!Files.exists(Paths.get(CAMINHO_ARQUIVO))) {
            return null;
        }
        try (Reader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            if (usuario != null) {
                usuario.inicializarListasSeNulas();
            }
            return usuario;
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao carregar dados salvos. Um novo perfil será criado. Motivo: " + e.getMessage());
            return null;
        }
    }
}