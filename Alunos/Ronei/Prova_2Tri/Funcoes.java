import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Funcoes {
    Scanner scan = new Scanner(System.in);
    ArrayList<Serie> listaDeSeries = new ArrayList<>();
    ArrayList<Serie> listaDeFavoritas = new ArrayList<>();
    ArrayList<Serie> listaDeJaAssistida = new ArrayList<>();
    ArrayList<Serie> listaDeAssistirDepois = new ArrayList<>();

    public ArrayList<Serie> getListaDeSeries() {
        return listaDeSeries;
    }

    public String getNomeUsuario() {
      System.out.println("Insira o seu nome ou apelido");
      String nomeUsuario = scan.nextLine();
      System.out.println("Seja bem-vindo " + nomeUsuario + "!");

      return nomeUsuario;
   }

    public void buscarSeries() throws Exception {
        System.out.println("Insira a palavra-chave para buscar séries:");
        try {
            String palavraChave = scan.nextLine();
            String palavraChaveCodificada = URLEncoder.encode(palavraChave, StandardCharsets.UTF_8);

            String urlStr = "https://api.tvmaze.com/search/shows?q=" + palavraChaveCodificada;

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .GET()
                    .build();

            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                RespostaBuscaShow[] resposta = gson.fromJson(response.body(), RespostaBuscaShow[].class);

                for (RespostaBuscaShow item : resposta) {
                    Show Show = item.show;

                    Serie serie = new Serie(
                        Show.id,
                        Show.name,
                        Show.language,
                        Show.genres,
                        (Show.rating != null ? Show.rating.average : null),
                        Show.status,
                        Show.premiered,
                        Show.ended,
                        (Show.network != null ? Show.network.name : null),
                        Show.summary
                    );

                    boolean duplicada = false;
                    for (Serie s : listaDeSeries) {
                        if (s.getId() == serie.getId()) {
                            duplicada = true;
                            break;
                        }
                    }

                    if (!duplicada) {
                        listaDeSeries.add(serie);
                        System.out.println(serie);
                    }
                }

            } else {
                System.out.println("Erro ao buscar séries. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Erro na busca. Por favor, tente novamente.");
            scan.nextLine();
        }
    }

    public void exibirSeries() {
        if(listaDeSeries.size() != 0) {
            for (Serie serie : listaDeSeries) {
            System.out.println(serie);
            }
            System.out.println(listaDeSeries.size() + " series encontradas.");
        } else {
            System.out.println("Nenhum resultado encontrado. Caso esteja tentando ultilizar alguma função" +
            " na qual precise digitar o ID da serie, digite 0.");
        }
    }

    //Lista de Favoritas
    public ArrayList<Serie> getListaDeFavoritas() {
        return listaDeFavoritas;
    }

    public void favoritarSerie() {
        exibirSeries();

        System.out.println("Insira o ID da serie que deseja favoritar: ");
        int IdSerieFavorita = scan.nextInt();
        scan.nextLine(); // limpa scan

        for (Serie serie : listaDeSeries) {
            if(IdSerieFavorita == serie.getId()) {
                serie.setFavorita(true);
                listaDeFavoritas.add(serie);
                break;
            }
        }
    }

    public void desfavoritarSerie() {
        exibirSeriesFavoritas();
        System.out.println("Caso queira cancelar a ação, digite 0.");
        System.out.println("Insira o ID da serie que deseja desfavoritar: ");
        int IdSerieDesfavorita = scan.nextInt();
        scan.nextLine(); // limpa scan

        for (Serie serie : listaDeSeries) {
            if(IdSerieDesfavorita == serie.getId()) {
                serie.setFavorita(false);
                listaDeFavoritas.remove(serie);
                break;
            }
        }
    }

    public void exibirSeriesFavoritas() {
        for (Serie serie : listaDeFavoritas) {
            System.out.println(serie);
        }
    }

    //Lista de Já Assistidas
    public ArrayList<Serie> getListaDeJaAssistidas() {
        return listaDeJaAssistida;
    }

    public void MarcarComoJaAssistida() {
        exibirSeries();

        System.out.println("Insira o ID da Serie que deseja marcar como já assistida: ");
        int IdSerieAssistida = scan.nextInt();
        scan.nextLine(); // limpa scan

        for (Serie serie : listaDeSeries) {
            if(IdSerieAssistida == serie.getId()) {
                serie.setJaAssistida(true);
                listaDeJaAssistida.add(serie);
                break;
            }
        }
    }

    public void DesmarcarComoJaAssistida() {
        exibirSeriesJaAssistidas();
        System.out.println("Caso queira cancelar a ação, digite 0.");
        System.out.println("Insira o ID da serie que deseja desmarcar como ja assistida: ");
        int IdSerieNaoAssistida = scan.nextInt();
        scan.nextLine(); // limpa scan

        for (Serie serie : listaDeSeries) {
            if(IdSerieNaoAssistida == serie.getId()) {
                serie.setJaAssistida(false);
                listaDeJaAssistida.remove(serie);
                break;
            }
        }
    }

    public void exibirSeriesJaAssistidas() {
        for (Serie serie : listaDeJaAssistida) {
            System.out.println(serie);
        }
    }

    //Lista de Assistir Depois
    public ArrayList<Serie> getListaDeAssistirDepois() {
        return listaDeAssistirDepois;
    }

    public void AdicionarAssistirDepois() {
        exibirSeries();

        System.out.println("Insira o ID da serie que deseja adicionar a \"Assistir Depois\": ");
        int IdSerieAssistirDepois = scan.nextInt();
        scan.nextLine(); // limpa scan

        for (Serie serie : listaDeSeries) {
            if(IdSerieAssistirDepois == serie.getId()) {
                serie.setAssistirDepois(true);
                listaDeAssistirDepois.add(serie);
                break;
            }
        }
    }

    public void RemoverAssistirDepois() {
        exibirSeriesAssistirDepois();
        System.out.println("Caso queira cancelar a ação, digite 0.");
        System.out.println("Insira o ID da serie que deseja remover de \"Assistir Depois\": ");
        int IdSerieNaoAssistirDepois = scan.nextInt();
        scan.nextLine(); // limpa scan

        for (Serie serie : listaDeSeries) {
            if(IdSerieNaoAssistirDepois == serie.getId()) {
                serie.setAssistirDepois(false);
                listaDeAssistirDepois.remove(serie);
                break;
            }
        }
    }

    public void exibirSeriesAssistirDepois() {
        for (Serie serie : listaDeAssistirDepois) {
            System.out.println(serie);
        }
    }

    //Ordenar Listas
    public void OrdenarListaNome(ArrayList<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
        for (Serie serie : lista) {
            System.out.println(serie);
        }
    }
    public void OrdenarListaNota(ArrayList<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNota, Comparator.nullsLast(Comparator.naturalOrder())));
        for (Serie serie : lista) {
            System.out.println(serie);
        }
    }

    public void OrdenarListaStatus(ArrayList<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
        for (Serie serie : lista) {
            System.out.println(serie);
        }
    }

    public void OrdenarPorData(ArrayList<Serie> lista) {
        try {
            lista.sort(Comparator.comparing(Serie::getDataPublicacaoComoData));

            for (Serie serie : lista) {
                System.out.println(serie);
            }
        } catch (Exception e) {
            System.out.println("Erro ao ordenar por data: " + e.getMessage());
        }
    }


}