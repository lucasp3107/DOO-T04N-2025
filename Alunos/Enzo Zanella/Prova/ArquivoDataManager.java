
import com.minhas_series_tv.model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoDataManager {
    private static final String FILE_PATH = "usuario_data.txt";

    public void salvarUsuario(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(usuario.getNomeOuApelido());
            writer.newLine();
            writer.write(listToString(usuario.getSeriesFavoritasIds()));
            writer.newLine();
            writer.write(listToString(usuario.getSeriesAssistidasIds()));
            writer.newLine();
            writer.write(listToString(usuario.getSeriesDesejaAssistirIds()));
            writer.newLine();
            System.out.println("Dados do usuário salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados do usuário: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Nenhum arquivo de usuário encontrado. Um novo será criado.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String nome = reader.readLine();
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Arquivo de usuário vazio ou corrompido.");
                return null;
            }

            Usuario usuario = new Usuario(nome);
            usuario.getSeriesFavoritasIds().addAll(stringToList(reader.readLine()));
            usuario.getSeriesAssistidasIds().addAll(stringToList(reader.readLine()));
            usuario.getSeriesDesejaAssistirIds().addAll(stringToList(reader.readLine()));

            System.out.println("Dados do usuário carregados com sucesso.");
            return usuario;
        } catch (IOException e) {
            System.err.println("Erro ao carregar os dados do usuário: " + e.getMessage());
            return null;
        }
    }

    private String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int id : list) {
            sb.append(id).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove última vírgula
        }
        return sb.toString();
    }

    private List<Integer> stringToList(String line) {
        List<Integer> list = new ArrayList<>();
        if (line != null && !line.trim().isEmpty()) {
            String[] parts = line.split(",");
            for (String part : parts) {
                try {
                    list.add(Integer.parseInt(part.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("ID inválido no arquivo: " + part);
                }
            }
        }
        return list;
    }
}
