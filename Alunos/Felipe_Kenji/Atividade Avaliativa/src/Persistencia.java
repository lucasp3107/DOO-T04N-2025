import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class Persistencia {
    private static final String CAMINHO_ARQUIVO = "user_data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void salvar(Dados.User usuario) {
        if (usuario == null) {
            System.err.println("Nenhum usuário para salvar.");
            return;
        }
        try {
            objectMapper.writeValue(new File(CAMINHO_ARQUIVO), usuario);
            System.out.println("Dados do usuário '" + usuario.getNickname() + "' salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do usuário: " + e.getMessage());
        }
    }

    public static Dados.User carregar() {
        File file = new File(CAMINHO_ARQUIVO);
        if (file.exists() && file.length() > 0) {
            try {
                return objectMapper.readValue(file, Dados.User.class);
            } catch (IOException e) {
                System.err.println("Erro ao carregar dados do usuário: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("Nenhum dado de usuário salvo encontrado.");
            return null;
        }
    }
}