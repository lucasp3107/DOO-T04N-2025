package br.com.tvtracker.data;

import br.com.tvtracker.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GerenciadorDados {

    private final String pastaUsuarios = "data";
    private final ObjectMapper mapper = new ObjectMapper(); // CORREÇÃO: instanciado aqui

    public GerenciadorDados() {
        try {
            Files.createDirectories(Paths.get(pastaUsuarios));
        } catch (Exception e) {
            System.out.println("Erro ao criar diretório de dados: " + e.getMessage());
        }
    }

    public Usuario carregarOuCriarUsuario(String nome) {
        String arquivo = pastaUsuarios + "/usuario_" + nome.toLowerCase() + ".json";
        File f = new File(arquivo);
        if (f.exists()) {
            try {
                return mapper.readValue(f, Usuario.class);
            } catch (Exception e) {
                System.out.println("Erro ao carregar dados do usuário: " + e.getMessage());
            }
        }

        return new Usuario(nome);
    }

    public void salvarUsuario(Usuario usuario) {
        String arquivo = pastaUsuarios + "/usuario_" + usuario.getNome().toLowerCase() + ".json";
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(arquivo), usuario);
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados do usuário: " + e.getMessage());
        }
    }
}
