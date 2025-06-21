package com.minhas_series_tv.util; // Pacote atualizado

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader; // Pacote atualizado
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.minhas_series_tv.model.Usuario;

public class JsonDataManager {
    private static final String FILE_PATH = "usuario_data.json";
    private Gson gson;

    public JsonDataManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void salvarUsuario(Usuario usuario) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(usuario, writer);
            System.out.println("Dados do usuário salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do usuário: " + e.getMessage());
        }
    }

    public Usuario carregarUsuario() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            System.out.println("Arquivo de dados não encontrado. Criando novo usuário...");
            return null;
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            System.out.println("Dados do usuário carregados com sucesso.");
            return usuario;
        } catch (JsonSyntaxException e) {
            System.err.println("Erro de sintaxe JSON ao carregar dados: " + e.getMessage());
            System.err.println("O arquivo de dados pode estar corrompido. Criando novo usuário...");
            return null;
        } catch (IOException e) {
            System.err.println("Erro de E/S ao carregar dados do usuário: " + e.getMessage());
            return null;
        }
    }
}