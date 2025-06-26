package PROVA_2TRI.Controllers;

import PROVA_2TRI.Entities.Serie;
import PROVA_2TRI.Interfaces.ISeriesApi;
import PROVA_2TRI.Repository.SerieRepository;
import PROVA_2TRI.Utils.InputUtilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SerieController {
    private final ISeriesApi seriesApi;
    private final SerieRepository repository;
    public List<Serie> series = new ArrayList<>();
    public List<Serie> favorites = new ArrayList<>();
    public List<Serie> toSeeLater = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Scanner sc;

    public SerieController(ISeriesApi seriesApi, SerieRepository repository, Scanner sc) {
        this.seriesApi = seriesApi;
        this.repository = repository;
        this.sc = sc;
    }

    public List<Serie> getRemoteSeries() throws Exception {
        series.clear();
        series.addAll(seriesApi.getAll());
        return series;
    }

    public List<Serie> getLocalSeries() throws Exception {
        series.clear();
        series.addAll(repository.load());
        return series;
    }

    public List<Serie> filterSeries(LocalDate date, String title, String keyWords, List<Serie> seriesToFilter) {
        List<Serie> filteredSeries = new ArrayList<>();
        for (Serie serie : seriesToFilter) {
            // Implementar o filtro de acordo com os critérios desejados
        }
        return filteredSeries;
    }

    public List<Serie> orderSeries(List<Serie> seriesToOrder, Boolean alphabet, Boolean idioma, Boolean Nota, Boolean status, Boolean data) {
        Comparator<Serie> comparator = Comparator.comparing(n -> 0);

        if (alphabet) {
            comparator = comparator.thenComparing(Serie::getName);
        }
        if (idioma) {
            comparator = comparator.thenComparing(serie -> serie.type);
        }
        if (Nota) {
            comparator = comparator.thenComparing(serie -> serie.rate);
        }
        if (status) {
            comparator = comparator.thenComparing(serie -> serie.status);
        }
        if (data) {
            comparator = comparator.thenComparing(serie -> serie.premiereDate);
        }

        return seriesToOrder.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public void getSeries(Scanner sc) throws Exception {
        System.out.println("Quer consultar séries locais? (S/N) ");
        String option = InputUtilities.GetSorN(sc);
        if (option.equalsIgnoreCase("S")) {
            try {
                getLocalSeries();
                return;
            } catch (Exception e) {
                System.out.println("Erro ao carregar séries locais: " + e.getMessage());
            }
        }
        System.out.println("Consultando séries do TV AMAZE...");
        getRemoteSeries();
    }

    public void listSeries(List<Serie> series) {
        for (Serie serie : series) {
            System.out.println(serie.toString());
        }
    }

    public void executeFilterSeries(List<Serie> series) {
        System.out.println("Digite o nome da série que deseja filtrar (ou deixe em branco para não filtrar): ");
        String title = sc.nextLine().trim();

        if (!title.isEmpty()) {
            List<Serie> filteredByTitle = series.stream()
                    .filter(serie -> serie.name.toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
            listSeries(filteredByTitle);
        } else {
            System.out.println("Nenhum filtro de título aplicado.");
        }
    }

    public void createFavorites() {
        System.out.println("Digite o id da série que deseja adicionar aos favoritos: ");
        int id = InputUtilities.GetId(sc);

        Optional<Serie> matchedSerie = series.stream()
                .filter(serie -> serie.getId() == id)
                .findFirst();

        Optional<Serie> matchedSerieFavorite = favorites.stream()
                .filter(serie -> serie.getId() == id)
                .findFirst();

        if (!matchedSerie.isPresent()) {
            System.out.println("Série não encontrada.");
            createFavorites();
        }
        if (matchedSerieFavorite.isPresent()) {
            System.out.println("Série já está nos favoritos.");
            return;
        }
        favorites.add(matchedSerie.get());
        System.out.println("Série adicionada aos favoritos.");
    }

    public void removeFavorites() {
        System.out.println("Digite o id da série que deseja remover dos favoritos: ");
        int id = InputUtilities.GetId(sc);

        Optional<Serie> matchedSerie = favorites.stream()
                .filter(serie -> serie.getId() == id)
                .findFirst();

        if (!matchedSerie.isPresent()) {
            System.out.println("Série não encontrada nos favoritos.");
            return;
        }
        favorites.remove(matchedSerie.get());
    }

    public void markAsWatched() {
        System.out.println("Digite o id da série que deseja marcar como assistida: ");
        int id = InputUtilities.GetId(sc);

        Optional<Serie> matchedSerie = series.stream()
                .filter(serie -> serie.getId() == id)
                .findFirst();

        if (!matchedSerie.isPresent()) {
            System.out.println("Série não encontrada.");
            return;
        }
        matchedSerie.get().watched = true;
        System.out.println("Série marcada como assistida.");
    }

    public void listSeriesWatched() {
        List<Serie> watchedSeries = series.stream()
                .filter(serie -> serie.watched)
                .toList();
        if (watchedSeries.isEmpty()) {
            System.out.println("Nenhuma série assistida encontrada.");
            return;
        }
        listSeries(watchedSeries);
    }

    public void createToSeeLater() {
        System.out.println("Digite o id da série que deseja marcar para ver depois: ");
        int id = InputUtilities.GetId(sc);

        Optional<Serie> matchedSerie = series.stream()
                .filter(serie -> serie.getId() == id)
                .findFirst();

        if (!matchedSerie.isPresent()) {
            System.out.println("Série não encontrada.");
            return;
        }
        toSeeLater.add(matchedSerie.get());
        System.out.println("Série adicionada para ver depois.");
    }

    public void removeToSeeLater() {
        System.out.println("Digite o id da série que deseja remover da lista de ver depois: ");
        int id = InputUtilities.GetId(sc);

        Optional<Serie> matchedSerie = toSeeLater.stream()
                .filter(serie -> serie.getId() == id)
                .findFirst();

        if (!matchedSerie.isPresent()) {
            System.out.println("Série não encontrada na lista de ver depois.");
            return;
        }
        toSeeLater.remove(matchedSerie.get());
        System.out.println("Série removida da lista de ver depois.");
    }

    public void listOrderSeries() {
        System.out.println("Qual lista deseja ordenar? ");
        System.out.println("[1] - Todas as séries");
        System.out.println("[2] - As favoritas");
        System.out.println("[3] - As séries para ver depois");
        System.out.println("[4] - Cancelar");
        String option = sc.nextLine();
        List<Serie> seriesToOrder;
        switch (option) {
            case "1":
                seriesToOrder = series;
                break;
            case "2":
                seriesToOrder = favorites;
                break;
            case "3":
                seriesToOrder = toSeeLater;
                break;
            case "4":
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
                listOrderSeries();
                return;
        }

        boolean alphabet = false;

        System.out.println("Deseja ordenar as séries por ordem alfabética? (S/N): ");
        String alphabetOption = sc.nextLine();
        if (alphabetOption.equalsIgnoreCase("S")) {
            alphabet = true;
        } else if (!alphabetOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderSeries();
            return;
        }
        System.out.println("Deseja ordenar por idioma? (S/N): ");
        String idiomaOption = sc.nextLine();
        boolean idioma = false;
        if (idiomaOption.equalsIgnoreCase("S")) {
            idioma = true;
        } else if (!idiomaOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderSeries();
            return;
        }

        System.out.println("Deseja ordenar por nota? (S/N): ");
        String notaOption = sc.nextLine();
        boolean Nota = false;
        if (notaOption.equalsIgnoreCase("S")) {
            Nota = true;
        } else if (!notaOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderSeries();
            return;
        }

        System.out.println("Deseja ordenar por status? (S/N): ");
        String statusOption = sc.nextLine();
        boolean status = false;
        if (statusOption.equalsIgnoreCase("S")) {
            status = true;
        } else if (!statusOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderSeries();
            return;
        }

        System.out.println("Deseja ordenar por data de lançamento? (S/N): ");
        String dataOption = sc.nextLine();
        boolean data = false;
        if (dataOption.equalsIgnoreCase("S")) {
            data = true;
        } else if (!dataOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderSeries();
            return;
        }


        List<Serie> orderedSeries = orderSeries(seriesToOrder, alphabet, idioma, Nota, status, data);
        listSeries(orderedSeries);
    }
}
