package service;

import model.Serie;
import model.Usuario;

import java.util.*;


public class SerieManager {
    private Usuario usuario;
    private TvMazeAPI api;
    private Scanner sc = new Scanner(System.in);

    public SerieManager(Usuario usuario) {
        this.usuario = usuario;
        this.api = new TvMazeAPI();
    }

    public void menu() {
        int op;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1 - Buscar série");
            System.out.println("2 - Gerenciar listas");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> buscarSerie();
                case 2 -> gerenciarListas();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        } while (op != 0);
    }

    private void buscarSerie() {
        System.out.print("Digite o nome da série: ");
        String nome = sc.nextLine();
        List<Serie> resultados = api.buscarSerie(nome);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
            return;
        }

        for (int i = 0; i < resultados.size(); i++) {
            System.out.println("\nSérie " + (i + 1));
            System.out.println(resultados.get(i));
        }

        System.out.print("Deseja adicionar alguma série às listas? (s/n) ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Informe o número da série: ");
            int idx = sc.nextInt() - 1;
            sc.nextLine();

            if (idx >= 0 && idx < resultados.size()) {
                Serie s = resultados.get(idx);
                adicionarALista(s);
            } else {
                System.out.println("Índice inválido.");
            }
        }
    }

    private void gerenciarListas() {
        int op;
        do {
            System.out.println("\n--- Gerenciar Listas ---");
            System.out.println("1 - Ver Favoritas");
            System.out.println("2 - Ver Assistidas");
            System.out.println("3 - Ver Para Assistir");
            System.out.println("4 - Ordenar Listas");
            System.out.println("5 - Remover de Listas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> exibirLista(usuario.getFavoritas(), "Favoritas");
                case 2 -> exibirLista(usuario.getAssistidas(), "Assistidas");
                case 3 -> exibirLista(usuario.getParaAssistir(), "Para Assistir");
                case 4 -> ordenarListas();
                case 5 -> removerDeListas();
                case 0 -> {}
                default -> System.out.println("Opção inválida");
            }
        } while (op != 0);
    }

    private void exibirLista(List<Serie> lista, String nomeLista) {
        System.out.println("\n--- " + nomeLista + " ---");
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
        } else {
            lista.forEach(s -> System.out.println(s));
        }
    }

    private void adicionarALista(Serie s) {
        System.out.println("Adicionar em: 1-Favoritas, 2-Assistidas, 3-Para Assistir");
        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1 -> usuario.getFavoritas().add(s);
            case 2 -> usuario.getAssistidas().add(s);
            case 3 -> usuario.getParaAssistir().add(s);
            default -> System.out.println("Opção inválida");
        }
    }

    private void ordenarListas() {
        System.out.println("Ordenar por: 1-Nome, 2-Nota, 3-Status, 4-Data de Estreia");
        int op = sc.nextInt();
        sc.nextLine();

        Comparator<Serie> comp = switch (op) {
            case 1 -> Comparator.comparing(Serie::getNome);
            case 2 -> Comparator.comparingDouble(Serie::getNota).reversed();
            case 3 -> Comparator.comparing(Serie::getStatus);
            case 4 -> Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(String::compareTo));
            default -> null;
        };

        if (comp != null) {
            usuario.getFavoritas().sort(comp);
            usuario.getAssistidas().sort(comp);
            usuario.getParaAssistir().sort(comp);
            System.out.println("Listas ordenadas.");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void removerDeListas() {
        System.out.println("Remover de: 1-Favoritas, 2-Assistidas, 3-Para Assistir");
        int op = sc.nextInt();
        sc.nextLine();

        List<Serie> lista = switch (op) {
            case 1 -> usuario.getFavoritas();
            case 2 -> usuario.getAssistidas();
            case 3 -> usuario.getParaAssistir();
            default -> null;
        };

        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista inválida ou vazia.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNome());
        }

        System.out.print("Informe o número da série para remover: ");
        int idx = sc.nextInt() - 1;
        sc.nextLine();

        if (idx >= 0 && idx < lista.size()) {
            lista.remove(idx);
            System.out.println("Removido.");
        } else {
            System.out.println("Índice inválido.");
        }
    }
}
