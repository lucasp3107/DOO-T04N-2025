package com.example;

import java.io.IOException;
import java.util.*;

public class Main {
    private static final TvMazeService tvMazeService = new TvMazeService();
    private static Usuario currentUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Rastreador de Series de TV!");

        System.out.print("Por favor, digite seu nome ou apelido: ");
        String userName = scanner.nextLine();
        currentUser = new Usuario(userName);
        System.out.println("Ola, " + userName + "!");

        setupInitialSeries();

        while (true) {
            printMenu();
            int choice = getIntInput(scanner);

            try {
                switch (choice) {
                    case 1:
                        searchAndShowSeries(scanner);
                        break;
                    case 2:
                        addToList(scanner, currentUser.getFavoritos(), "favoritos");
                        break;
                    case 3:
                        addToList(scanner, currentUser.getAssistidas(), "ja assistidas");
                        break;
                    case 4:
                        addToList(scanner, currentUser.getParaAssistir(), "para assistir");
                        break;
                    case 5:
                        removeFromList(scanner, currentUser.getFavoritos(), "favoritos");
                        break;
                    case 6:
                        removeFromList(scanner, currentUser.getAssistidas(), "ja assistidas");
                        break;
                    case 7:
                        removeFromList(scanner, currentUser.getParaAssistir(), "para assistir");
                        break;
                    case 8:
                        showLists(scanner);
                        break;
                    case 9:
                        System.out.println("Ate mais!");
                        return;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void setupInitialSeries() {
        List<Serie> initialSeries = new ArrayList<>();

        List<String> genresBB = Arrays.asList("Drama", "Crime", "Thriller");
        Rating ratingBB = new Rating();
        ratingBB.setAverage(9.2);
        Network networkBB = new Network();
        networkBB.setName("AMC");
        initialSeries.add(new Serie("Breaking Bad", "English", genresBB, ratingBB, "Ended", "2008-01-20", "2013-09-29", networkBB));

        List<String> genresGoT = Arrays.asList("Drama", "Adventure", "Fantasy");
        Rating ratingGoT = new Rating();
        ratingGoT.setAverage(9.1);
        Network networkGoT = new Network();
        networkGoT.setName("HBO");
        initialSeries.add(new Serie("Game of Thrones", "English", genresGoT, ratingGoT, "Ended", "2011-04-17", "2019-05-19", networkGoT));

        List<String> genresST = Arrays.asList("Drama", "Fantasy", "Horror");
        Rating ratingST = new Rating();
        ratingST.setAverage(8.7);
        Network networkST = new Network();
        networkST.setName("Netflix");
        initialSeries.add(new Serie("Stranger Things", "English", genresST, ratingST, "Running", "2016-07-15", null, networkST));

        currentUser.setParaAssistir(initialSeries);
        System.out.println("Algumas series populares foram adicionadas a sua lista 'Desejo assistir'.");
    }

    private static void printMenu() {
        System.out.println("\nO que voce gostaria de fazer?");
        System.out.println("1. Procurar e ver detalhes de uma serie");
        System.out.println("2. Adicionar uma serie aos favoritos");
        System.out.println("3. Adicionar uma serie as 'ja assistidas'");
        System.out.println("4. Adicionar uma serie as 'desejo assistir'");
        System.out.println("5. Remover uma serie dos favoritos");
        System.out.println("6. Remover uma serie das 'ja assistidas'");
        System.out.println("7. Remover uma serie das 'desejo assistir'");
        System.out.println("8. Exibir e ordenar listas");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static void searchAndShowSeries(Scanner scanner) throws IOException, InterruptedException {
        System.out.print("Digite o nome da serie: ");
        String query = scanner.nextLine();
        List<Serie> series = tvMazeService.searchSeries(query);

        if (handleEmptySeriesList(series)) return;

        System.out.println("Resultados da busca:");
        printSeriesList(series);

        System.out.print("Digite o numero da serie para ver os detalhes (ou 0 para voltar): ");
        int choice = getIntInput(scanner);

        if (choice > 0 && choice <= series.size()) {
            System.out.println(series.get(choice - 1));
        } else if (choice != 0) {
            System.out.println("Selecao invalida.");
        }
    }

    private static void addToList(Scanner scanner, List<Serie> list, String listName) throws IOException, InterruptedException {
        System.out.print("Digite o nome da serie para adicionar a '" + listName + "': ");
        String query = scanner.nextLine();
        List<Serie> series = tvMazeService.searchSeries(query);

        if (handleEmptySeriesList(series)) return;

        System.out.println("Selecione a serie para adicionar:");
        printSeriesList(series);
        int choice = getIntInput(scanner);

        if (choice > 0 && choice <= series.size()) {
            Serie selectedSerie = series.get(choice - 1);
            if (!list.contains(selectedSerie)) {
                list.add(selectedSerie);
                System.out.println("'" + selectedSerie.getName() + "' adicionada a " + listName + ".");
            } else {
                System.out.println("Essa serie ja esta na lista.");
            }
        } else {
            System.out.println("Selecao invalida.");
        }
    }

    private static void removeFromList(Scanner scanner, List<Serie> list, String listName) {
        if (handleEmptySeriesList(list)) return;

        System.out.println("Selecione a serie para remover de '" + listName + "':");
        printSeriesList(list);
        int choice = getIntInput(scanner);

        if (choice > 0 && choice <= list.size()) {
            Serie removedSerie = list.remove(choice - 1);
            System.out.println("'" + removedSerie.getName() + "' removida de " + listName + ".");
        } else {
            System.out.println("Selecao invalida.");
        }
    }

    private static void showLists(Scanner scanner) {
        System.out.println("Qual lista voce gostaria de exibir?");
        System.out.println("1. Favoritos");
        System.out.println("2. Ja assistidas");
        System.out.println("3. Desejo assistir");
        int choice = getIntInput(scanner);

        List<Serie> listToShow = null;
        String listName = "";
        switch (choice) {
            case 1: listToShow = currentUser.getFavoritos(); listName = "Favoritos"; break;
            case 2: listToShow = currentUser.getAssistidas(); listName = "Ja assistidas"; break;
            case 3: listToShow = currentUser.getParaAssistir(); listName = "Desejo assistir"; break;
            default: System.out.println("Opcao invalida."); return;
        }

        if (handleEmptySeriesList(listToShow)) return;

        System.out.println("Como voce gostaria de ordenar a lista?");
        System.out.println("1. Ordem alfabetica (A-Z)");
        System.out.println("2. Nota geral (maior para menor)");
        System.out.println("3. Status da serie");
        System.out.println("4. Data de estreia (mais recente primeiro)");
        System.out.println("0. Sem ordenacao");
        int sortChoice = getIntInput(scanner);

        switch (sortChoice) {
            case 1: listToShow.sort(Comparator.comparing(Serie::getName)); break;
            case 2: listToShow.sort(Comparator.comparingDouble((Serie s) -> s.getRating() != null ? s.getRating().getAverage() : 0).reversed()); break;
            case 3: listToShow.sort(Comparator.comparing(Serie::getStatus, Comparator.nullsLast(String::compareTo))); break;
            case 4: listToShow.sort(Comparator.comparing(Serie::getPremiered, Comparator.nullsLast(String::compareTo)).reversed()); break;
            case 0: break;
            default: System.out.println("Opcao de ordenacao invalida. Exibindo na ordem padrao.");
        }

        System.out.println("\n--- " + listName + " ---");
        for (Serie serie : listToShow) {
            System.out.println(serie);
        }
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada invalida. Por favor, digite um numero.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private static boolean handleEmptySeriesList(List<Serie> series) {
        if (series == null || series.isEmpty()) {
            System.out.println("A lista esta vazia.");
            return true;
        }
        return false;
    }

    private static void printSeriesList(List<Serie> series) {
        for (int i = 0; i < series.size(); i++) {
            System.out.println((i + 1) + ". " + series.get(i).getName());
        }
    }
}
