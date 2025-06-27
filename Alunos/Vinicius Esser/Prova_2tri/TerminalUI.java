package br.com.vinicius.prova2tri; 

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TerminalUI {
    private SerieManager serieManager;
    private Scanner scanner;

    public TerminalUI(SerieManager serieManager) {
        this.serieManager = serieManager;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bem-vindo(a) ao seu Rastreador de Séries de TV!");
        System.out.println("Usuário atual: " + serieManager.getNomeUsuario());

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        buscarSeries();
                        break;
                    case 2:
                        gerenciarFavoritos();
                        break;
                    case 3:
                        gerenciarAssistidas();
                        break;
                    case 4:
                        gerenciarDesejaAssistir();
                        break;
                    case 0:
                        System.out.println("Saindo... Salvando dados.");
                        serieManager.salvarDadosUsuario();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        } while (opcao != 0);

        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Buscar Séries");
        System.out.println("2. Ver e Gerenciar Favoritos");
        System.out.println("3. Ver Séries Já Assistidas");
        System.out.println("4. Ver Séries Que Deseja Assistir");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        while (true) {
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                return opcao;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }
        }
    }

    private void buscarSeries() {
        System.out.println("\n--- Buscar Séries ---");
        System.out.print("Digite o nome da série para buscar: ");
        String nomeBusca = scanner.nextLine();

        List<Serie> resultados = serieManager.buscarSeries(nomeBusca);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada com o nome '" + nomeBusca + "'.");
        } else {
            System.out.println("\nResultados da Busca:");
            exibirSeries(resultados);
            menuGerenciarSerie(resultados);
        }
    }

    private void exibirSeries(List<Serie> series) {
        if (series.isEmpty()) {
            System.out.println("Nenhuma série para exibir.");
            return;
        }
        for (int i = 0; i < series.size(); i++) {
            Serie s = series.get(i);
            System.out.println("\n--- Série #" + (i + 1) + " ---");
            System.out.println(s.toString());
        }
    }

    private void menuGerenciarSerie(List<Serie> series) {
        if (series.isEmpty()) return;

        System.out.println("\n--- Ações com Séries Exibidas ---");
        System.out.println("1. Adicionar aos Favoritos");
        System.out.println("2. Adicionar às Séries Já Assistidas");
        System.out.println("3. Adicionar às Séries Que Deseja Assistir");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        int opcao = lerOpcao();

        if (opcao >= 1 && opcao <= 3) {
            System.out.print("Digite o NÚMERO da série para a ação: ");
            int numeroSerie = lerOpcao();
            Serie serieSelecionada = serieManager.encontrarSeriePorNumero(numeroSerie, series);
            if (serieSelecionada != null) {
                switch (opcao) {
                    case 1:
                        serieManager.adicionarFavorito(serieSelecionada);
                        System.out.println("'" + serieSelecionada.getNome() + "' adicionado(a) aos favoritos!");
                        break;
                    case 2:
                        serieManager.adicionarAssistida(serieSelecionada);
                        System.out.println("'" + serieSelecionada.getNome() + "' adicionado(a) às séries já assistidas!");
                        break;
                    case 3:
                        serieManager.adicionarDesejaAssistir(serieSelecionada);
                        System.out.println("'" + serieSelecionada.getNome() + "' adicionado(a) às séries que deseja assistir!");
                        break;
                }
            } else {
                System.out.println("Número de série inválido.");
            }
        }
    }

    private void gerenciarFavoritos() {
        System.out.println("\n--- Suas Séries Favoritas ---");
        List<Serie> favoritos = serieManager.getFavoritos();
        if (favoritos.isEmpty()) {
            System.out.println("Você não tem séries favoritas ainda.");
            return;
        }
        exibirSeriesOrdenaveis(favoritos);

        System.out.println("\n--- Ações com Favoritos ---");
        System.out.println("1. Remover dos Favoritos");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = lerOpcao();
        if (opcao == 1) {
            System.out.print("Digite o NÚMERO da série para remover: ");
            int numeroSerie = lerOpcao();
            Serie serieRemover = serieManager.encontrarSeriePorNumero(numeroSerie, favoritos);
            if (serieRemover != null) {
                serieManager.removerFavorito(serieRemover);
                System.out.println("'" + serieRemover.getNome() + "' removido(a) dos favoritos.");
            } else {
                System.out.println("Número de série inválido.");
            }
        }
    }

    private void gerenciarAssistidas() {
        System.out.println("\n--- Suas Séries Já Assistidas ---");
        List<Serie> assistidas = serieManager.getAssistidas();
        if (assistidas.isEmpty()) {
            System.out.println("Você não marcou nenhuma série como assistida ainda.");
            return;
        }
        exibirSeriesOrdenaveis(assistidas);

        System.out.println("\n--- Ações com Séries Já Assistidas ---");
        System.out.println("1. Remover desta lista");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = lerOpcao();
        if (opcao == 1) {
            System.out.print("Digite o NÚMERO da série para remover: ");
            int numeroSerie = lerOpcao();
            Serie serieRemover = serieManager.encontrarSeriePorNumero(numeroSerie, assistidas);
            if (serieRemover != null) {
                serieManager.removerAssistida(serieRemover);
                System.out.println("'" + serieRemover.getNome() + "' removido(a) da lista de assistidas.");
            } else {
                System.out.println("Número de série inválido.");
            }
        }
    }

    private void gerenciarDesejaAssistir() {
        System.out.println("\n--- Suas Séries Que Deseja Assistir ---");
        List<Serie> desejaAssistir = serieManager.getDesejaAssistir();
        if (desejaAssistir.isEmpty()) {
            System.out.println("Você não tem séries para assistir ainda.");
            return;
        }
        exibirSeriesOrdenaveis(desejaAssistir);

        System.out.println("\n--- Ações com Séries Que Deseja Assistir ---");
        System.out.println("1. Marcar como Assistida (e remover desta lista)");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = lerOpcao();
        if (opcao == 1) {
            System.out.print("Digite o NÚMERO da série para marcar como assistida: ");
            int numeroSerie = lerOpcao();
            Serie serieMover = serieManager.encontrarSeriePorNumero(numeroSerie, desejaAssistir);
            if (serieMover != null) {
                serieManager.adicionarAssistida(serieMover);
                desejaAssistir.remove(serieMover);
                serieManager.salvarDadosUsuario();
                System.out.println("'" + serieMover.getNome() + "' marcada como assistida e removida de 'Deseja Assistir'.");
            } else {
                System.out.println("Número de série inválido.");
            }
        }
    }

    private void exibirSeriesOrdenaveis(List<Serie> lista) {
        int opcaoOrdenacao;
        do {
            System.out.println("\nComo você gostaria de ordenar a lista?");
            System.out.println("1. Nome (A-Z)");
            System.out.println("2. Nota Geral (Maior para Menor)");
            System.out.println("3. Estado (Alfabético)");
            System.out.println("4. Data de Estreia (Mais Antiga para Mais Nova)");
            System.out.println("0. Não ordenar / Voltar ao menu anterior");
            System.out.print("Escolha uma opção de ordenação: ");
            opcaoOrdenacao = lerOpcao();

            List<Serie> listaOrdenada = new java.util.ArrayList<>(lista);

            switch (opcaoOrdenacao) {
                case 1:
                    listaOrdenada = serieManager.ordenarSeries(listaOrdenada, "nome");
                    break;
                case 2:
                    listaOrdenada = serieManager.ordenarSeries(listaOrdenada, "nota");
                    break;
                case 3:
                    listaOrdenada = serieManager.ordenarSeries(listaOrdenada, "estado");
                    break;
                case 4:
                    listaOrdenada = serieManager.ordenarSeries(listaOrdenada, "dataestreia");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção de ordenação inválida.");
            }
            if (opcaoOrdenacao != 0) {
                exibirSeries(listaOrdenada);
                System.out.println("\n(Lista ordenada exibida. Escolha '0' para voltar ao menu anterior.)");
            }

        } while (opcaoOrdenacao != 0);
    }
}