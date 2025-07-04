package controller;

import service.TvMazeAPI;
import model.Serie;
import utils.Persistencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private String nomeUsuario;
    private TvMazeAPI api;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejo;
    private Scanner scanner;

    public Sistema(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        api = new TvMazeAPI();
        favoritos = Persistencia.carregarLista("favoritos.json");
        assistidas = Persistencia.carregarLista("assistidas.json");
        desejo = Persistencia.carregarLista("desejo.json");
        if (favoritos == null) favoritos = new ArrayList<>();
        if (assistidas == null) assistidas = new ArrayList<>();
        if (desejo == null) desejo = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void buscarEAdicionarSerie() {
        try {
            System.out.print("Nome da série: ");
            String nome = scanner.nextLine();
            List<Serie> resultados = api.buscarSeries(nome);

            if (resultados.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }

            System.out.println("Séries encontradas:");
            for (int i = 0; i < resultados.size(); i++) {
                Serie s = resultados.get(i);
                System.out.printf("%d - %s (%s) - Nota: %.2f\n", i + 1, s.getNome(), s.getEstado(), s.getNota());
                System.out.println("  Idioma: " + s.getIdioma());
                System.out.println("  Gêneros: " + String.join(", ", s.getGeneros()));
                System.out.println("  Data de estreia: " + s.getDataEstreia());
                System.out.println("  Data de término: " + s.getDataTermino());
                System.out.println("  Emissora: " + s.getEmissora());
                System.out.println("  Sinopse: " + s.getSinopse());
                System.out.println();
            }

            System.out.print("Escolha o número da série para adicionar ou 0 para cancelar: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha > 0 && escolha <= resultados.size()) {
                Serie selecionada = resultados.get(escolha - 1);
                System.out.print("Lista (favoritos/assistidas/desejo): ");
                String lista = scanner.nextLine().toLowerCase();

                switch (lista) {
                    case "favoritos":
                        favoritos.add(selecionada);
                        Persistencia.salvarLista(favoritos, "favoritos.json");
                        System.out.println("Série adicionada aos favoritos.");
                        break;
                    case "assistidas":
                        assistidas.add(selecionada);
                        Persistencia.salvarLista(assistidas, "assistidas.json");
                        System.out.println("Série adicionada às assistidas.");
                        break;
                    case "desejo":
                        desejo.add(selecionada);
                        Persistencia.salvarLista(desejo, "desejo.json");
                        System.out.println("Série adicionada à lista de desejo.");
                        break;
                    default:
                        System.out.println("Lista inválida.");
                }
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar ou adicionar série: " + e.getMessage());
        }
    }

    public void exibirListas() {
        System.out.println("Favoritos:");
        exibirLista(favoritos);
        System.out.println("\nAssistidas:");
        exibirLista(assistidas);
        System.out.println("\nDesejo:");
        exibirLista(desejo);
    }

    private void exibirLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série nessa lista.");
        } else {
            for (Serie s : lista) {
                System.out.printf("%s (%s) - Nota: %.2f\n", s.getNome(), s.getEstado(), s.getNota());
            }
        }
    }

    public void ordenarListas() {
        System.out.println("Ordenar qual lista? (favoritos/assistidas/desejo): ");
        String lista = scanner.nextLine().toLowerCase();
        List<Serie> listaEscolhida = null;
        switch (lista) {
            case "favoritos":
                listaEscolhida = favoritos;
                break;
            case "assistidas":
                listaEscolhida = assistidas;
                break;
            case "desejo":
                listaEscolhida = desejo;
                break;
            default:
                System.out.println("Lista inválida.");
                return;
        }
        if (listaEscolhida.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }
        System.out.println("Ordenar por:");
        System.out.println("1 - Ordem alfabética do nome");
        System.out.println("2 - Nota geral");
        System.out.println("3 - Estado da série");
        System.out.println("4 - Data de estreia");
        System.out.print("Escolha a opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                listaEscolhida.sort(Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));
                break;
            case "2":
                listaEscolhida.sort(Comparator.comparingDouble(Serie::getNota).reversed());
                break;
            case "3":
                listaEscolhida.sort(Comparator.comparing(Serie::getEstado, String.CASE_INSENSITIVE_ORDER));
                break;
            case "4":
                listaEscolhida.sort(Comparator.comparing(Serie::getDataEstreia));
                break;
            default:
                System.out.println("Opção inválida. Ordenação cancelada.");
                return;
        }

        System.out.println("Lista ordenada:");
        exibirLista(listaEscolhida);
    }

    public void removerSerie() {
        System.out.println("De qual lista deseja remover? (favoritos/assistidas/desejo): ");
        String lista = scanner.nextLine().toLowerCase();
        List<Serie> listaEscolhida = null;
        switch (lista) {
            case "favoritos":
                listaEscolhida = favoritos;
                break;
            case "assistidas":
                listaEscolhida = assistidas;
                break;
            case "desejo":
                listaEscolhida = desejo;
                break;
            default:
                System.out.println("Lista inválida.");
                return;
        }
        if (listaEscolhida.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }
        System.out.println("Séries na lista:");
        for (int i = 0; i < listaEscolhida.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, listaEscolhida.get(i).getNome());
        }
        System.out.print("Escolha o número da série para remover ou 0 para cancelar: ");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha > 0 && escolha <= listaEscolhida.size()) {
            Serie removida = listaEscolhida.remove(escolha - 1);
            Persistencia.salvarLista(listaEscolhida, lista + ".json");
            System.out.println("Série removida: " + removida.getNome());
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
}
