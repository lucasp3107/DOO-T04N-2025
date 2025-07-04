package com.rastreadorseries.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDados {
    private static final String NOME_ARQUIVO = "dados_usuario.json";
    private final Gson gson;
    private final Scanner scanner;

    public GerenciadorDados(Scanner scanner) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.scanner = scanner;
    }

    public Usuario carregarDados() {
        System.out.println("Tentando carregar dados do arquivo: " + NOME_ARQUIVO);
        try {
            if (Files.exists(Paths.get(NOME_ARQUIVO))) {
                try (FileReader reader = new FileReader(NOME_ARQUIVO)) {
                    Usuario usuario = gson.fromJson(reader, Usuario.class);
                    if (usuario == null || usuario.getNome() == null || usuario.getNome().isEmpty() || usuario.getNome().equals("UsuarioPadrao")) {
                        System.out.println("Arquivo de dados vazio, corrompido ou nome de usuário padrão. Criando/atualizando usuário.");
                        return criarUsuarioInicial();
                    }
                    System.out.println("Dados carregados com sucesso para o usuário: " + usuario.getNome());
                    return usuario;
                }
            } else {
                System.out.println("Arquivo de dados não encontrado. Criando novo usuário com dados pré-carregados.");
                return criarUsuarioInicial();
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura ao carregar dados: " + e.getMessage());
            System.out.println("Criando novo usuário devido ao erro de carregamento.");
            return criarUsuarioInicial();
        } catch (JsonParseException e) {
            System.err.println("Erro ao parsear JSON ao carregar dados: " + e.getMessage());
            System.out.println("Criando novo usuário devido a JSON corrompido.");
            return criarUsuarioInicial();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado ao carregar dados: " + e.getMessage());
            System.out.println("Criando novo usuário.");
            return criarUsuarioInicial();
        }
    }

    public void salvarDados(Usuario usuario) {
        System.out.println("Salvando dados para o arquivo: " + NOME_ARQUIVO);
        try (FileWriter writer = new FileWriter(NOME_ARQUIVO)) {
            gson.toJson(usuario, writer);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado ao salvar dados: " + e.getMessage());
        }
    }

    private Usuario criarUsuarioInicial() {
        Usuario novoUsuario = new Usuario("UsuarioPadrao");

        System.out.print("Bem-vindo! Por favor, digite seu nome ou apelido: ");
        String nomeDigitado = scanner.nextLine().trim();
        if (!nomeDigitado.isEmpty()) {
            novoUsuario.setNome(nomeDigitado);
        } else {
            System.out.println("Nome inválido. Usando 'UsuarioPadrao'.");
        }

        Serie got = new Serie(82, "Game of Thrones", "English", Arrays.asList("Drama", "Fantasy"), 9.0, "Ended", "2011-04-17", "2019-05-19", "HBO");
        Serie strangerThings = new Serie(1667, "Stranger Things", "English", Arrays.asList("Drama", "Science-Fiction", "Horror"), 8.8, "Running", "2016-07-15", null, "Netflix");
        Serie theOffice = new Serie(526, "The Office", "English", Arrays.asList("Comedy"), 8.7, "Ended", "2005-03-24", "2013-05-16", "NBC");

        novoUsuario.adicionarSerie(novoUsuario.getFavoritas(), got);
        novoUsuario.adicionarSerie(novoUsuario.getDesejaAssistir(), strangerThings);
        novoUsuario.adicionarSerie(novoUsuario.getJaAssistidas(), theOffice);

        System.out.println("Usuário inicial '" + novoUsuario.getNome() + "' criado com dados pré-carregados (3 séries de exemplo).");
        return novoUsuario;
    }
}