package com.seriestracker;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static User user;
    private static TVMazeService api = new TVMazeService();

    public static void main(String[] args) {
        try {
            init();
            runMenu();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void init() {
        System.out.print("Digite seu apelido: ");
        String nick = scanner.nextLine();
        user = new User(nick);
        user.getFavorites().addAll(
            PersistenceService.loadList("favorites.json", Show.class));
        user.getWatched().addAll(
            PersistenceService.loadList("watched.json", Show.class));
        user.getToWatch().addAll(
            PersistenceService.loadList("toWatch.json", Show.class));
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n===== Menu Principal =====");
            System.out.println("1. Buscar série na API");
            System.out.println("2. Minhas listas (Favoritos, Assistidos, etc)");
            System.out.println("3. Ver catálogo local");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": search(); break;
                case "2": showLists(); break;
                case "3": viewLocalCatalog(); break;
                case "0": exit(); return;
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void search() {
        System.out.print("Digite o nome da série para buscar na API: ");
        String query = scanner.nextLine();
        List<Show> results = api.searchShows(query);
        handleShowSelection(results);
    }

    private static void viewLocalCatalog() {
        System.out.println("\n--- Catálogo de Séries Locais ---");
        List<Show> catalog = CatalogService.getLocalCatalog();
        handleShowSelection(catalog);
    }

    private static void handleShowSelection(List<Show> shows) {
        if (shows.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        for (int i = 0; i < shows.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, shows.get(i));
        }

        System.out.print("\nSelecione o número da série para adicionar a uma lista (ou 0 para voltar): ");
        try {
            int sel = Integer.parseInt(scanner.nextLine());
            if (sel < 1 || sel > shows.size()) return;

            Show chosen = shows.get(sel - 1);

            System.out.println("\nAdicionar '" + chosen.getName() + "' em qual lista?");
            System.out.println("1. Favoritos");
            System.out.println("2. Já assisti");
            System.out.println("3. Quero assistir");
            System.out.println("4. Remover de todas as listas");
            System.out.println("0. Cancelar");
            System.out.print("Escolha: ");
            int action = Integer.parseInt(scanner.nextLine());

            switch (action) {
                case 1: user.addFavorite(chosen); System.out.println("Adicionado aos favoritos!"); break;
                case 2: user.addWatched(chosen); System.out.println("Marcado como assistido!"); break;
                case 3: user.addToWatch(chosen); System.out.println("Adicionado à lista 'Quero Assistir'!"); break;
                case 4:
                    user.removeFavorite(chosen);
                    user.removeWatched(chosen);
                    user.removeToWatch(chosen);
                    System.out.println("Removido de todas as listas.");
                    break;
                default: System.out.println("Operação cancelada."); return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void showLists() {
        System.out.println("\n--- Minhas Listas ---");
        System.out.println("1. Favoritas");
        System.out.println("2. Já assisti");
        System.out.println("3. Quero assistir");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("Qual lista você quer ver? ");

        try {
            int opt = Integer.parseInt(scanner.nextLine());
            if (opt == 0) return;

            List<Show> list;
            String listName = "";

            switch (opt) {
                case 1: list = user.getFavorites(); listName = "Favoritos"; break;
                case 2: list = user.getWatched(); listName = "Já assisti"; break;
                case 3: list = user.getToWatch(); listName = "Quero assistir"; break;
                default: System.out.println("Opção inválida."); return;
            }

            System.out.println("\n--- Lista: " + listName + " ---");
            if (list.isEmpty()) {
                System.out.println("Esta lista está vazia.");
            } else {
                // --- INÍCIO DA ALTERAÇÃO ---
                // Mostra a lista numerada para permitir a remoção
                for (int i = 0; i < list.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, list.get(i));
                }

                System.out.print("\nDigite o número da série para remover (ou 0 para voltar): ");
                int remOpt = Integer.parseInt(scanner.nextLine());

                if (remOpt > 0 && remOpt <= list.size()) {
                    Show showToRemove = list.get(remOpt - 1);
                    
                    // Remove da lista correta baseado na opção inicial (opt)
                    switch (opt) {
                        case 1: user.removeFavorite(showToRemove); break;
                        case 2: user.removeWatched(showToRemove); break;
                        case 3: user.removeToWatch(showToRemove); break;
                    }
                    System.out.println("'" + showToRemove.getName() + "' foi removido(a) da lista.");
                }
                // --- FIM DA ALTERAÇÃO ---
            }

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void exit() {
        PersistenceService.saveList(user.getFavorites(), "favorites.json");
        PersistenceService.saveList(user.getWatched(), "watched.json");
        PersistenceService.saveList(user.getToWatch(), "toWatch.json");
        System.out.println("Listas salvas! Até logo, " + user.getNickname() + "!");
    }
}
