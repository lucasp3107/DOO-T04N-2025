import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.net.URI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Functions {
    ArrayList<Series> seriesList = new ArrayList<>();
    ArrayList<Series> favoritesList = new ArrayList<>();
    ArrayList<Series> watchedList = new ArrayList<>();
    ArrayList<Series> watchLaterList = new ArrayList<>();

    public ArrayList<Series> getList(String listType) {
        return switch (listType) {
            case "All" -> seriesList;
            case "Favorites" -> favoritesList;
            case "Watched" -> watchedList;
            case "WatchLater" -> watchLaterList;
            default -> new ArrayList<>();
        };
    }

    public String getUsername() {
        String username = JOptionPane.showInputDialog(null, "Digite seu nome ou apelido:");
        JOptionPane.showMessageDialog(null, "Seja bem-vindo " + username + "!");
        return username;
    }

    // Método pra mostrar texto grande numa janela scrollável
    private void showLargeText(String title, String text) {
        JTextArea textArea = new JTextArea(25, 70);
        textArea.setText(text);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void searchSeries() {
        try {
            String keyword = JOptionPane.showInputDialog(null, "Digite a palavra-chave para buscar séries:");
            if (keyword == null || keyword.trim().isEmpty()) return;

            String encoded = URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8);
            String url = "https://api.tvmaze.com/search/shows?q=" + encoded;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                SearchResponse[] searchResults = gson.fromJson(response.body(), SearchResponse[].class);

                StringBuilder resultBuilder = new StringBuilder();

                for (SearchResponse item : searchResults) {
                    Show show = item.show;

                    Series series = new Series(
                            show.id,
                            show.name,
                            show.language,
                            show.genres,
                            (show.rating != null ? show.rating.average : null),
                            show.status,
                            show.premiered,
                            show.ended,
                            (show.network != null ? show.network.networkName : null),
                            show.summary
                    );

                    boolean duplicate = false;
                    for (Series s : seriesList) {
                        if (s.getId() == series.getId()) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (!duplicate) {
                        seriesList.add(series);
                        resultBuilder.append(series).append("\n\n");
                    }
                }

                if (resultBuilder.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhuma série encontrada.");
                } else {
                    showLargeText("Resultado da Busca", resultBuilder.toString());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Erro ao buscar séries. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na busca. Tente novamente.");
        }
    }

    public void showList(String listType) {
        ArrayList<Series> list = getList(listType);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma série na lista.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Series s : list) {
                sb.append(s).append("\n\n");
            }
            showLargeText("Lista: " + listType, sb.toString());
        }
    }

    public void addToList(String listType) {
        if (seriesList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "A lista de séries está vazia. Faça uma busca antes.");
            return;
        }

        showList("All");

        String input = JOptionPane.showInputDialog(null, "Digite o ID da série para adicionar:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input.trim());
            boolean found = false;

            for (Series s : seriesList) {
                if (s.getId() == id) {
                    switch (listType) {
                        case "Favorites" -> {
                            s.setFavorite(true);
                            if (!favoritesList.contains(s)) favoritesList.add(s);
                        }
                        case "Watched" -> {
                            s.setWatched(true);
                            if (!watchedList.contains(s)) watchedList.add(s);
                        }
                        case "WatchLater" -> {
                            s.setWatchLater(true);
                            if (!watchLaterList.contains(s)) watchLaterList.add(s);
                        }
                    }
                    found = true;
                    break;
                }
            }

            if (!found) JOptionPane.showMessageDialog(null, "ID não encontrado.");
            else JOptionPane.showMessageDialog(null, "Série adicionada com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    public void removeFromList(String listType) {
        ArrayList<Series> list = getList(listType);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma série na lista para remover.");
            return;
        }

        showList(listType);

        String input = JOptionPane.showInputDialog(null, "Digite o ID da série para remover:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input.trim());
            boolean found = false;

            for (Series s : list) {
                if (s.getId() == id) {
                    switch (listType) {
                        case "Favorites" -> {
                            s.setFavorite(false);
                            favoritesList.remove(s);
                        }
                        case "Watched" -> {
                            s.setWatched(false);
                            watchedList.remove(s);
                        }
                        case "WatchLater" -> {
                            s.setWatchLater(false);
                            watchLaterList.remove(s);
                        }
                    }
                    found = true;
                    break;
                }
            }

            if (!found) JOptionPane.showMessageDialog(null, "ID não encontrado na lista.");
            else JOptionPane.showMessageDialog(null, "Série removida com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    public void sortByName(ArrayList<Series> list) {
        list.sort(Comparator.comparing(Series::getName));
        showListBySorted(list);
    }

    public void sortByRating(ArrayList<Series> list) {
        list.sort(Comparator.comparing(Series::getRating, Comparator.nullsLast(Comparator.naturalOrder())));
        showListBySorted(list);
    }

    public void sortByStatus(ArrayList<Series> list) {
        list.sort(Comparator.comparing(Series::getStatus));
        showListBySorted(list);
    }

    public void sortByDate(ArrayList<Series> list) {
        list.sort(Comparator.comparing(Series::getPremieredDate));
        showListBySorted(list);
    }

    private void showListBySorted(ArrayList<Series> list) {
        StringBuilder sb = new StringBuilder();
        for (Series s : list) {
            sb.append(s).append("\n\n");
        }
        showLargeText("Lista Ordenada", sb.toString());
    }
}
