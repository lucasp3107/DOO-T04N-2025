package main;

import br.com.tvtracker.api.TVMazeAPI;
import br.com.tvtracker.data.GerenciadorDados;
import br.com.tvtracker.model.Serie;
import br.com.tvtracker.model.Usuario;

import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TVMazeAPI api = new TVMazeAPI();
        GerenciadorDados dados = new GerenciadorDados();

        System.out.println("Bem-vindo ao Tracker de Séries!");

        String nomeUsuario;
        do {
            System.out.print("Digite seu nome ou apelido para começar: ");
            nomeUsuario = scanner.nextLine().trim();
            if (nomeUsuario.isEmpty()) {
                System.out.println("Nome não pode estar em branco. Tente novamente.");
            }
        } while (nomeUsuario.isEmpty());

        Usuario usuario = dados.carregarOuCriarUsuario(nomeUsuario);

        boolean executando = true;

        while (executando) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.printf ("║  TV Tracker  | Usuário: %-14s║\n", usuario.getNome());
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ 1 - Buscar séries por nome            ║");
            System.out.println("║ 2 - Ver séries favoritas              ║");
            System.out.println("║ 3 - Ver séries assistidas             ║");
            System.out.println("║ 4 - Ver séries desejadas              ║");
            System.out.println("║ 0 - Sair                              ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    buscarSeries(api, usuario, dados, scanner);
                    break;
                case "2":
                    exibirLista("Favoritas", usuario.getSeriesFavoritasIds(), api, usuario, dados, scanner);
                    break;
                case "3":
                    exibirLista("Assistidas", usuario.getSeriesAssistidasIds(), api, usuario, dados, scanner);
                    break;
                case "4":
                    exibirLista("Desejadas", usuario.getSeriesDesejadasIds(), api, usuario, dados, scanner);
                    break;
                case "0":
                    dados.salvarUsuario(usuario);
                    System.out.println("Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void buscarSeries(TVMazeAPI api, Usuario usuario, GerenciadorDados dados, Scanner scanner) {
        System.out.print("Digite o nome da série: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome da série não pode estar em branco.");
            return;
        }

        List<Serie> series = api.buscarSeriesPorNome(nome);

        if (series.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        int pagina = 0;
        int tamanhoPagina = 10;
        boolean navegando = true;

        while (navegando) {
            int inicio = pagina * tamanhoPagina;
            int fim = Math.min(inicio + tamanhoPagina, series.size());
            List<Serie> subLista = series.subList(inicio, fim);

            System.out.println("\nSéries encontradas:");
            for (int i = 0; i < subLista.size(); i++) {
                Serie s = subLista.get(i);
                System.out.printf("%d - %s (%s)\n", i + 1, s.getNome(), s.getEstreia());
            }

            System.out.println("\nEscolha uma das opções abaixo:");
            System.out.println("1-10 - Ver detalhes e adicionar série");
            if (fim < series.size()) {
                System.out.println("M - Mostrar mais séries");
            }
            System.out.println("0 - Voltar ao menu");

            System.out.print("Opção: ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equals("0")) {
                navegando = false;
            } else if (entrada.equalsIgnoreCase("M") && fim < series.size()) {
                pagina++;
            } else {
                try {
                    int indice = Integer.parseInt(entrada);
                    if (indice < 1 || indice > subLista.size()) {
                        System.out.println("Número fora do intervalo.");
                        continue;
                    }

                    Serie serieSelecionada = subLista.get(indice - 1);
                    System.out.println("\n" + serieSelecionada.toString());

                    System.out.println("Deseja adicionar esta série a alguma lista?");
                    System.out.println("1 - Favoritos | 2 - Assistidas | 3 - Desejadas | 0 - Ignorar");
                    System.out.print("Opção: ");
                    String opcao = scanner.nextLine().trim();

                    switch (opcao) {
                        case "1":
                            usuario.marcarFavorita(serieSelecionada.getId());
                            System.out.println("Adicionada aos favoritos.");
                            break;
                        case "2":
                            usuario.marcarAssistida(serieSelecionada.getId());
                            System.out.println("Adicionada às assistidas.");
                            break;
                        case "3":
                            usuario.marcarDesejada(serieSelecionada.getId());
                            System.out.println("Adicionada à lista de desejo.");
                            break;
                        case "0":
                        default:
                            break;
                    }

                    dados.salvarUsuario(usuario);

                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número válido.");
                }
            }
        }
    }

    private static void exibirLista(String titulo, Set<Integer> ids, TVMazeAPI api, Usuario usuario, GerenciadorDados dados, Scanner scanner) {
        if (ids.isEmpty()) {
            System.out.println("\nLista " + titulo + " está vazia.");
            return;
        }

        List<Serie> series = ids.stream()
                .map(api::buscarSeriePorId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (series.isEmpty()) {
            System.out.println("Não foi possível carregar as séries da lista.");
            return;
        }

        System.out.println("\nEscolha a ordenação:");
        System.out.println("1 - Nome A-Z");
        System.out.println("2 - Nota (maior primeiro)");
        System.out.println("3 - Status da série");
        System.out.println("4 - Data de estreia");
        System.out.print("Opção: ");
        String ordem = scanner.nextLine().trim();

        switch (ordem) {
            case "1":
                series.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
                break;
            case "2":
                series.sort(Comparator.comparingDouble(Serie::getNota).reversed());
                break;
            case "3":
                series.sort(Comparator.comparing(Serie::getStatus));
                break;
            case "4":
                series.sort(Comparator.comparing(Serie::getEstreia, Comparator.nullsLast(String::compareTo)));
                break;
            default:
                System.out.println("Ordem inválida. Mostrando sem ordenação específica.");
        }

        while (true) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║        Lista de Séries - " + titulo + "       ║");
            System.out.println("╚═══════════════════════════════════════╝");

            for (int i = 0; i < series.size(); i++) {
                Serie s = series.get(i);
                System.out.printf("%d - %s (%s)\n", i + 1, s.getNome(), s.getEstreia());
            }

            System.out.print("\nDigite o número da série para ver detalhes ou 0 para voltar: ");
            String entrada = scanner.nextLine().trim();

            try {
                int indice = Integer.parseInt(entrada);
                if (indice == 0) return;

                if (indice < 1 || indice > series.size()) {
                    System.out.println("Número inválido.");
                    continue;
                }

                Serie serieSelecionada = series.get(indice - 1);
                System.out.println("\n" + serieSelecionada.toString());

                System.out.print("Deseja remover essa série da lista? (S/N): ");
                String confirmar = scanner.nextLine().trim().toUpperCase();

                if (confirmar.equals("S")) {
                    int id = serieSelecionada.getId();
                    switch (titulo) {
                        case "Favoritas" -> usuario.removerFavorita(id);
                        case "Assistidas" -> usuario.removerAssistida(id);
                        case "Desejadas" -> usuario.removerDesejada(id);
                    }
                    dados.salvarUsuario(usuario);
                    series.remove(serieSelecionada);
                    System.out.println("Série removida com sucesso.");
                    if (series.isEmpty()) {
                        System.out.println("A lista agora está vazia.");
                        return;
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }
    }
}
