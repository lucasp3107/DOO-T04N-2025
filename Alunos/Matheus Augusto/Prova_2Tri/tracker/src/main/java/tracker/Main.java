package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SerieService serieService = new SerieService();

        Usuario usuario = new Usuario("Matheus");

        boolean running = true;
        while (running) {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1. Procurar séries");
            System.out.println("2. Gerenciar listas (Adicionar/Remover)");
            System.out.println("3. Exibir listas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.print("Digite o nome da série que deseja buscar: ");
                    String nomeBusca = scanner.nextLine();
                    serieService.buscarSeriesSimples(nomeBusca);
                }
                case "2" -> {
                    System.out.println("\n--- GERENCIAR LISTAS ---");
                    System.out.println("1. Adicionar série (via busca)");
                    System.out.println("2. Remover série");
                    System.out.print("Escolha uma opção: ");
                    String opGerenciar = scanner.nextLine();

                    switch (opGerenciar) {
                        case "1" -> {
                            serieService.adicionarSeriePorBusca(scanner, usuario);
                        }
                        case "2" -> {
                            removerSerieDaLista(scanner, usuario);
                        }
                        default -> System.out.println("Opção inválida.");
                    }
                }
                case "3" -> {
                    System.out.println("\n--- EXIBIR LISTAS ---");
                    System.out.println("Escolha a lista para exibir:");
                    System.out.println("1. Favoritas");
                    System.out.println("2. Assistidas");
                    System.out.println("3. Deseja Assistir");
                    System.out.print("Escolha: ");
                    String listaStr = scanner.nextLine();

                    var lista = switch (listaStr) {
                        case "1" -> usuario.getFavoritas();
                        case "2" -> usuario.getAssistidas();
                        case "3" -> usuario.getDesejaAssistir();
                        default -> null;
                    };

                    if (lista == null) {
                        System.out.println("Opção inválida.");
                        break;
                    }

                    if (lista.isEmpty()) {
                        System.out.println("Lista vazia.");
                        break;
                    }

                    System.out.println("Ordenar por:");
                    System.out.println("1. Nome");
                    System.out.println("2. Nota Geral");
                    System.out.println("3. Estado");
                    System.out.println("4. Data de Estreia");
                    System.out.print("Escolha: ");
                    String ordenar = scanner.nextLine();

                    lista.sort((s1, s2) -> {
                        return switch (ordenar) {
                            case "1" -> s1.getNome().compareToIgnoreCase(s2.getNome());
                            case "2" -> Double.compare(s2.getNotaGeral(), s1.getNotaGeral()); // Decrescente
                            case "3" -> s1.getEstado().compareToIgnoreCase(s2.getEstado());
                            case "4" -> s1.getDataEstreia().compareToIgnoreCase(s2.getDataEstreia());
                            default -> 0;
                        };
                    });

                    System.out.println("\nExibindo lista:");
                    for (Serie s : lista) {
                        System.out.println("---------------------");
                        System.out.println(s);
                    }
                }
                case "4" -> {
                    running = false;
                    System.out.println("Saindo do sistema. Até logo!");
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    public static void removerSerieDaLista(Scanner scanner, Usuario usuario) {
        System.out.println("Escolha a lista para remover série:");
        System.out.println("1. Favoritas");
        System.out.println("2. Assistidas");
        System.out.println("3. Deseja Assistir");
        System.out.print("Digite o número da lista: ");
        String escolhaLista = scanner.nextLine();

        var lista = switch (escolhaLista) {
            case "1" -> usuario.getFavoritas();
            case "2" -> usuario.getAssistidas();
            case "3" -> usuario.getDesejaAssistir();
            default -> null;
        };

        if (lista == null) {
            System.out.println("Opção inválida.");
            return;
        }

        if (lista.isEmpty()) {
            System.out.println("Lista vazia. Nada para remover.");
            return;
        }

        System.out.println("Séries na lista:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getNome());
        }

        int escolhaSerie = -1;
        while (true) {
            System.out.print("Digite o número da série que deseja remover (0 para cancelar): ");
            String entrada = scanner.nextLine().trim();
            try {
                escolhaSerie = Integer.parseInt(entrada);
                if (escolhaSerie == 0) {
                    System.out.println("Operação cancelada.");
                    return;
                }
                if (escolhaSerie < 1 || escolhaSerie > lista.size()) {
                    System.out.println("Número inválido. Tente novamente.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }

        Serie serieRemovida = lista.remove(escolhaSerie - 1);
        System.out.println("Série \"" + serieRemovida.getNome() + "\" removida da lista com sucesso.");
    }
}
