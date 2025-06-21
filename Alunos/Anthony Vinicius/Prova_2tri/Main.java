package com.rastreadorseries.app;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuario;
    private static TvMazeAPI tvMazeAPI = new TvMazeAPI();
    private static GerenciadorDados gerenciadorDados;

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Rastreador de Séries!");

        gerenciadorDados = new GerenciadorDados(scanner);
        usuario = gerenciadorDados.carregarDados();

        if (usuario == null) {
            System.err.println("Não foi possível carregar ou criar os dados do usuário. Encerrando.");
            scanner.close();
            return;
        }

        System.out.println("Olá, " + usuario.getNome() + "!");

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        buscarSerie();
                        break;
                    case 2:
                        gerenciarListas();
                        break;
                    case 3:
                        exibirListas();
                        break;
                    case 4:
                        System.out.println("Salvando dados e saindo. Até mais!");
                        gerenciadorDados.salvarDados(usuario);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }

        } while (opcao != 4);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Buscar Séries");
        System.out.println("2. Gerenciar Minhas Listas");
        System.out.println("3. Exibir Minhas Listas");
        System.out.println("4. Sair (Salvar Dados)");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.next();
            System.out.print("Escolha uma opção: ");
        }
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    private static void buscarSerie() {
        System.out.print("\nDigite o nome da série para buscar: ");
        String nomeBusca = scanner.nextLine().trim();
        if (nomeBusca.isEmpty()) {
            System.out.println("Nome da série não pode ser vazio.");
            return;
        }

        try {
            List<Serie> resultados = tvMazeAPI.buscarSeriesPorNome(nomeBusca);
            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada com esse nome na TVMaze.");
                return;
            }

            System.out.println("\n--- Resultados da Busca ---");
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println((i + 1) + ". " + resultados.get(i).getName());
            }

            System.out.print("Digite o número da série para ver detalhes e adicionar, ou '0' para voltar: ");
            int escolhaSerie = lerOpcao();

            if (escolhaSerie > 0 && escolhaSerie <= resultados.size()) {
                Serie serieEscolhida = resultados.get(escolhaSerie - 1);
                System.out.println("\n--- Detalhes da Série ---");
                System.out.println(serieEscolhida);

                System.out.println("\nO que deseja fazer com esta série?");
                System.out.println("1. Adicionar à Favoritas");
                System.out.println("2. Adicionar à Já Assistidas");
                System.out.println("3. Adicionar à Deseja Assistir");
                System.out.println("0. Voltar");
                System.out.print("Escolha uma opção: ");
                int acao = lerOpcao();

                switch (acao) {
                    case 1:
                        if (usuario.adicionarSerie(usuario.getFavoritas(), serieEscolhida)) {
                            System.out.println(serieEscolhida.getName() + " adicionada às Favoritas.");
                        } else {
                            System.out.println(serieEscolhida.getName() + " já está nas Favoritas.");
                        }
                        break;
                    case 2:
                        if (usuario.adicionarSerie(usuario.getJaAssistidas(), serieEscolhida)) {
                            System.out.println(serieEscolhida.getName() + " adicionada às Séries Já Assistidas.");
                        } else {
                            System.out.println(serieEscolhida.getName() + " já está nas Séries Já Assistidas.");
                        }
                        break;
                    case 3:
                        if (usuario.adicionarSerie(usuario.getDesejaAssistir(), serieEscolhida)) {
                            System.out.println(serieEscolhida.getName() + " adicionada às Séries que Deseja Assistir.");
                        } else {
                            System.out.println(serieEscolhida.getName() + " já está nas Séries que Deseja Assistir.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else if (escolhaSerie != 0) {
                System.out.println("Número de série inválido.");
            }
        } catch (IOException e) {
            System.err.println("Erro na busca: Não foi possível conectar à API ou houve um problema de rede.");
            System.err.println("Detalhes: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado durante a busca de séries: " + e.getMessage());
        }
    }

    private static void gerenciarListas() {
        System.out.println("\n--- Gerenciar Minhas Listas ---");
        System.out.println("1. Favoritas");
        System.out.println("2. Já Assistidas");
        System.out.println("3. Deseja Assistir");
        System.out.println("0. Voltar");
        System.out.print("Qual lista deseja gerenciar? ");

        int escolhaLista = lerOpcao();
        List<Serie> listaSelecionada = null;
        String nomeLista = "";

        switch (escolhaLista) {
            case 1: listaSelecionada = usuario.getFavoritas(); nomeLista = "Favoritas"; break;
            case 2: listaSelecionada = usuario.getJaAssistidas(); nomeLista = "Já Assistidas"; break;
            case 3: listaSelecionada = usuario.getDesejaAssistir(); nomeLista = "Deseja Assistir"; break;
            case 0: return;
            default: System.out.println("Opção inválida."); return;
        }

        if (listaSelecionada.isEmpty()) {
            System.out.println("A lista '" + nomeLista + "' está vazia. Adicione séries primeiro!");
            return;
        }

        System.out.println("\n--- Séries em " + nomeLista + " ---");
        for (int i = 0; i < listaSelecionada.size(); i++) {
            System.out.println((i + 1) + ". " + listaSelecionada.get(i).getName());
        }

        System.out.println("\nO que deseja fazer?");
        System.out.println("1. Ver detalhes de uma série");
        System.out.println("2. Remover uma série");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int acao = lerOpcao();

        if (acao == 1 || acao == 2) {
            System.out.print("Digite o número da série: ");
            int indiceSerie = lerOpcao();
            if (indiceSerie > 0 && indiceSerie <= listaSelecionada.size()) {
                Serie serieAlvo = listaSelecionada.get(indiceSerie - 1);
                if (acao == 1) {
                    System.out.println("\n--- Detalhes da Série ---");
                    System.out.println(serieAlvo);
                } else {
                    if (usuario.removerSerie(listaSelecionada, serieAlvo)) {
                        System.out.println(serieAlvo.getName() + " removida de '" + nomeLista + "'.");
                    } else {
                        System.out.println("Erro ao remover " + serieAlvo.getName() + " de '" + nomeLista + "'.");
                    }
                }
            } else {
                System.out.println("Número de série inválido.");
            }
        } else if (acao != 0) {
            System.out.println("Opção inválida.");
        }
    }

    private static void exibirListas() {
        System.out.println("\n--- Exibir Minhas Listas ---");
        System.out.println("1. Favoritas (" + usuario.getFavoritas().size() + " séries)");
        System.out.println("2. Já Assistidas (" + usuario.getJaAssistidas().size() + " séries)");
        System.out.println("3. Deseja Assistir (" + usuario.getDesejaAssistir().size() + " séries)");
        System.out.println("0. Voltar");
        System.out.print("Qual lista deseja exibir? ");

        int escolhaLista = lerOpcao();
        List<Serie> listaSelecionada = null;
        String nomeLista = "";

        switch (escolhaLista) {
            case 1: listaSelecionada = usuario.getFavoritas(); nomeLista = "Favoritas"; break;
            case 2: listaSelecionada = usuario.getJaAssistidas(); nomeLista = "Já Assistidas"; break;
            case 3: listaSelecionada = usuario.getDesejaAssistir(); nomeLista = "Deseja Assistir"; break;
            case 0: return;
            default: System.out.println("Opção inválida."); return;
        }

        if (listaSelecionada.isEmpty()) {
            System.out.println("A lista '" + nomeLista + "' está vazia.");
            return;
        }

        System.out.println("\n--- Opções de Ordenação ---");
        System.out.println("1. Nome (A-Z)");
        System.out.println("2. Nota Geral (Maior primeiro)");
        System.out.println("3. Estado da Série (Running, Ended, etc.)");
        System.out.println("4. Data de Estreia (Mais antiga primeiro)");
        System.out.println("0. Voltar (Não ordenar)");
        System.out.print("Escolha como ordenar a lista '" + nomeLista + "': ");
        int opcaoOrdenacao = lerOpcao();
        String criterioOrdenacao = "";

        switch (opcaoOrdenacao) {
            case 1: criterioOrdenacao = "nome"; break;
            case 2: criterioOrdenacao = "nota"; break;
            case 3: criterioOrdenacao = "status"; break;
            case 4: criterioOrdenacao = "estreia"; break;
            case 0: break;
            default: System.out.println("Opção de ordenação inválida."); return;
        }

        if (!criterioOrdenacao.isEmpty()) {
            usuario.ordenarLista(listaSelecionada, criterioOrdenacao);
            System.out.println("Lista '" + nomeLista + "' ordenada por " + criterioOrdenacao + ".");
        }

        System.out.println("\n--- Séries em " + nomeLista + " (Ordenadas por " + (criterioOrdenacao.isEmpty() ? "Padrão" : criterioOrdenacao) + ") ---");
        for (int i = 0; i < listaSelecionada.size(); i++) {
            System.out.println((i + 1) + ". " + listaSelecionada.get(i).getName());
        }

        System.out.print("Digite o número da série para ver detalhes, ou '0' para voltar: ");
        int verDetalhes = lerOpcao();
        if (verDetalhes > 0 && verDetalhes <= listaSelecionada.size()) {
            System.out.println("\n--- Detalhes da Série ---");
            System.out.println(listaSelecionada.get(verDetalhes - 1));
        } else if (verDetalhes != 0) {
            System.out.println("Número de série inválido.");
        }
    }
}