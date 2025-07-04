import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GerenciadorDeDados {
    private static final String NOME_ARQUIVO = "dados_series.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        try (FileReader reader = new FileReader(NOME_ARQUIVO)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            System.out.println("Criando um novo perfil...");
            return criarUsuarioComDadosPrecarregados();
        }
    }

    public boolean limparDados() {
        try {
            Files.deleteIfExists(Paths.get(NOME_ARQUIVO));
            System.out.println("Dados do perfil limpos com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao tentar limpar os dados: " + e.getMessage());
            return false;
        }
    }

    private Usuario criarUsuarioComDadosPrecarregados() {
        Usuario novoUsuario = new Usuario("Novo Usuário");
        return novoUsuario;
    }
}