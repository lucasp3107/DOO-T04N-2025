import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Main {
    private Scanner scanner = new Scanner(System.in);
    private API apiService;
    private Dados.User currentUser;

    public Main() {
        this.apiService = new API();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.iniciar();
    }

    public void iniciar() {
        currentUser = carregarOuCriarUsuario();

        int option;
        do {
            System.out.println("\nOlá, " + currentUser.getNickname() + "! Escolha uma opção:");
            System.out.println("1. Procurar por Série");
            System.out.println("2. Minhas Listas de Séries");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        searchSeries();
                        break;
                    case 2:
                        manageListsMenu();
                        break;
                    case 0:
                        Persistencia.salvar(currentUser);
                        System.out.println("Dados salvos. Obrigado por usar o TV Series Tracker.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                option = -1;
            }
        } while (option != 0);
    }

    private Dados.User carregarOuCriarUsuario() {
        Dados.User usuarioCarregado = Persistencia.carregar();
        if (usuarioCarregado == null) {
            System.out.print("Parece que é sua primeira vez ou os dados não puderam ser carregados.\nPor favor, digite seu apelido: ");
            String nickname = scanner.nextLine();
            Dados.User novoUsuario = new Dados.User(nickname);
            Persistencia.salvar(novoUsuario);
            System.out.println("Novo usuário '" + nickname + "' criado com sucesso!");
            return novoUsuario;
        } else {
            System.out.println("Olá, " + usuarioCarregado.getNickname() + "! Seus dados foram carregados.");
            return usuarioCarregado;
        }
    }

    private void searchSeries() {
        System.out.print("Digite o nome da série para buscar: ");
        String query = scanner.nextLine();

        if (query.trim().isEmpty()) {
            System.out.println("O nome da série não pode ser vazio.");
            return;
        }

        try {
            List<Dados.Serie> results = apiService.searchShows(query);
            if (results.isEmpty()) {
                System.out.println("Nenhuma série encontrada com este nome.");
                return;
            }

            System.out.println("\n--- Resultados da Busca ---");
            for (int i = 0; i < results.size(); i++) {
                Dados.Serie serie = results.get(i);
                String score = (serie.getRating() != null && serie.getRating().getAverage() != null) ? String.format("%.1f", serie.getRating().getAverage()) : "N/A";
                System.out.printf("%d. [ID: %d] %s (Status: %s, Nota: %s)\n",
                                  (i + 1), serie.getId(), serie.getName(), serie.getStatus(), score);
            }

            System.out.print("Digite o número da série para ver detalhes ou adicionar/remover (0 para voltar): ");
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            } finally {
                scanner.nextLine();
            }

            if (choice > 0 && choice <= results.size()) {
                Dados.Serie selectedSerie = results.get(choice - 1);
                displaySerieDetailsAndActions(selectedSerie);
            } else if (choice != 0) {
                System.out.println("Número inválido.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar séries: " + e.getMessage());
        }
    }

    private void displaySerieDetailsAndActions(Dados.Serie serie) {
        System.out.println(serie);

        int option;
        do {
            System.out.println("\n--- Ações para '" + serie.getName() + "' ---");
            System.out.println("1. Adicionar aos Favoritos");
            System.out.println("2. Remover dos Favoritos");
            System.out.println("3. Adicionar às Séries Já Assistidas");
            System.out.println("4. Remover das Séries Já Assistidas");
            System.out.println("5. Adicionar à Lista de Desejo (Watchlist)");
            System.out.println("6. Remover da Lista de Desejo (Watchlist)");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        currentUser.addSerieToList(serie, currentUser.getFavorites(), "Favoritos");
                        break;
                    case 2:
                        currentUser.removeSerieFromList(serie.getId(), currentUser.getFavorites(), "Favoritos");
                        break;
                    case 3:
                        currentUser.addSerieToList(serie, currentUser.getWatched(), "Já Assistidas");
                        break;
                    case 4:
                        currentUser.removeSerieFromList(serie.getId(), currentUser.getWatched(), "Já Assistidas");
                        break;
                    case 5:
                        currentUser.addSerieToList(serie, currentUser.getWatchlist(), "Desejo Assistir");
                        break;
                    case 6:
                        currentUser.removeSerieFromList(serie.getId(), currentUser.getWatchlist(), "Desejo Assistir");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            }
        } while (option != 0);
    }

    private void manageListsMenu() {
        int option;
        do {
            System.out.println("\n--- GERENCIAR MINHAS LISTAS ---");
            System.out.println("1. Exibir Favoritos");
            System.out.println("2. Exibir Séries Já Assistidas");
            System.out.println("3. Exibir Séries que Desejo Assistir");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        displayAndSortList(currentUser.getFavorites(), "Favoritos");
                        break;
                    case 2:
                        displayAndSortList(currentUser.getWatched(), "Já Assistidas");
                        break;
                    case 3:
                        displayAndSortList(currentUser.getWatchlist(), "Desejo Assistir");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            }
        } while (option != 0);
    }

    private void displayAndSortList(List<Dados.Serie> list, String listName) {
        if (list.isEmpty()) {
            System.out.println("\n--- Minha Lista de " + listName + " (0 séries) ---");
            System.out.println("A lista está vazia.");
            System.out.println("------------------------------------");
            return;
        }

        int option;
        do {
            System.out.println("\n--- Minha Lista de " + listName + " (" + list.size() + " séries) ---");
            for (int i = 0; i < list.size(); i++) {
                Dados.Serie serie = list.get(i);
                String score = (serie.getRating() != null && serie.getRating().getAverage() != null) ?
                               String.format("%.1f", serie.getRating().getAverage()) : "N/A";
                System.out.printf("%d. [ID: %d] %s (Status: %s, Nota: %s)\n",
                                  (i + 1), serie.getId(), serie.getName(), serie.getStatus(), score);
            }
            System.out.println("------------------------------------");


            System.out.println("\n--- Opções de Lista para " + listName + " ---");
            System.out.println("1. Ordenar por Nome");
            System.out.println("2. Ordenar por Nota Geral");
            System.out.println("3. Ordenar por Estado");
            System.out.println("4. Ordenar por Data de Estreia");
            System.out.println("5. Ver Detalhes de uma Série (por ID)");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        Collections.sort(list, Comparadores.BY_NAME);
                        System.out.println("Lista de " + listName + " ordenada por Nome.");
                        break;
                    case 2:
                        Collections.sort(list, Comparadores.BY_RATING);
                        System.out.println("Lista de " + listName + " ordenada por Nota.");
                        break;
                    case 3:
                        Collections.sort(list, Comparadores.BY_STATUS);
                        System.out.println("Lista de " + listName + " ordenada por Status.");
                        break;
                    case 4:
                        Collections.sort(list, Comparadores.BY_PREMIERED_DATE);
                        System.out.println("Lista de " + listName + " ordenada por Data de Estreia.");
                        break;
                    case 5:
                        System.out.print("Digite o ID da série para ver detalhes: ");
                        int serieId = scanner.nextInt();
                        scanner.nextLine();
                        Optional<Dados.Serie> serieOptional = list.stream().filter(s -> s.getId() == serieId).findFirst();
                        if (serieOptional.isPresent()) {
                            displaySerieDetailsAndActions(serieOptional.get());
                        } else {
                            System.out.println("Série com ID " + serieId + " não encontrada nesta lista.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                option = -1;
            }
        } while (option != 0);
    }
}