package tracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SerieService {
	
	public void buscarSeriesSimples(String nomeBusca) {
	    if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
	        System.out.println("Nome da série não pode ser vazio.");
	        return;
	    }

	    try {
	        String urlStr = "https://api.tvmaze.com/search/shows?q=" + nomeBusca.trim().replace(" ", "%20");
	        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
	        con.setRequestMethod("GET");
	        con.setConnectTimeout(5000);
	        con.setReadTimeout(5000);

	        int status = con.getResponseCode();
	        if (status != 200) {
	            System.out.println("Erro na API. Código HTTP: " + status);
	            return;
	        }

	        InputStreamReader reader = new InputStreamReader(con.getInputStream());
	        JsonElement root = JsonParser.parseReader(reader);
	        reader.close();

	        if (!root.isJsonArray()) {
	            System.out.println("Resposta inesperada da API.");
	            return;
	        }

	        JsonArray resultados = root.getAsJsonArray();

	        if (resultados.size() == 0) {
	            System.out.println("Nenhuma série encontrada.");
	            return;
	        }

	        System.out.println("Resultados encontrados:");
	        for (int i = 0; i < resultados.size(); i++) {
	            JsonElement showEl = resultados.get(i).getAsJsonObject().get("show");
	            if (showEl == null || showEl.isJsonNull()) continue;

	            String nome = "Indisponível";
	            var obj = showEl.getAsJsonObject();
	            if (obj.has("name") && !obj.get("name").isJsonNull()) {
	                nome = obj.get("name").getAsString();
	            }

	            System.out.println((i + 1) + ". " + nome);
	        }

	    } catch (Exception e) {
	        System.out.println("Erro ao buscar séries: " + e.getMessage());
	    }
	}


    public void adicionarSeriePorBusca(Scanner scanner, Usuario usuario) {
        System.out.print("Digite o nome da série que deseja buscar para adicionar: ");
        String nomeBusca = scanner.nextLine().trim();

        if (nomeBusca.isEmpty()) {
            System.out.println("Nome da série não pode ser vazio.");
            return;
        }

        try {
            String urlStr = "https://api.tvmaze.com/search/shows?q=" + nomeBusca.replace(" ", "%20");
            HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();
            if (status != 200) {
                System.out.println("Erro na API. Código HTTP: " + status);
                return;
            }

            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            JsonElement root = JsonParser.parseReader(reader);
            reader.close();

            if (!root.isJsonArray()) {
                System.out.println("Resposta inesperada da API.");
                return;
            }

            JsonArray resultados = root.getAsJsonArray();

            if (resultados.size() == 0) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }

            List<Serie> seriesEncontradas = new ArrayList<>();
            System.out.println("Resultados encontrados:");
            for (int i = 0; i < resultados.size(); i++) {
                JsonElement showEl = resultados.get(i).getAsJsonObject().get("show");
                if (showEl == null || showEl.isJsonNull()) continue;
                Serie s = criarSerieAPartirDeJson(showEl);
                seriesEncontradas.add(s);
                System.out.println((seriesEncontradas.size()) + ". " + s.getNome());
            }

            if (seriesEncontradas.isEmpty()) {
                System.out.println("Nenhuma série válida encontrada.");
                return;
            }

            int escolhaSerie = -1;
            while (true) {
                System.out.print("Digite o número da série que deseja adicionar (0 para cancelar): ");
                String entrada = scanner.nextLine().trim();
                try {
                    escolhaSerie = Integer.parseInt(entrada);
                    if (escolhaSerie == 0) {
                        System.out.println("Operação cancelada.");
                        return;
                    }
                    if (escolhaSerie < 1 || escolhaSerie > seriesEncontradas.size()) {
                        System.out.println("Número inválido. Tente novamente.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número válido.");
                }
            }

            Serie selecionada = seriesEncontradas.get(escolhaSerie - 1);

            int escolhaLista = -1;
            while (true) {
                System.out.println("Escolha a lista para adicionar:");
                System.out.println("1. Favoritas");
                System.out.println("2. Assistidas");
                System.out.println("3. Deseja Assistir");
                System.out.print("Digite o número da lista: ");
                String entrada = scanner.nextLine().trim();
                try {
                    escolhaLista = Integer.parseInt(entrada);
                    if (escolhaLista < 1 || escolhaLista > 3) {
                        System.out.println("Número inválido. Tente novamente.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um número válido.");
                }
            }

            boolean adicionou = false;

            switch (escolhaLista) {
                case 1 -> {
                    if (!usuario.getFavoritas().contains(selecionada)) {
                        usuario.getFavoritas().add(selecionada);
                        adicionou = true;
                    }
                }
                case 2 -> {
                    if (!usuario.getAssistidas().contains(selecionada)) {
                        usuario.getAssistidas().add(selecionada);
                        adicionou = true;
                    }
                }
                case 3 -> {
                    if (!usuario.getDesejaAssistir().contains(selecionada)) {
                        usuario.getDesejaAssistir().add(selecionada);
                        adicionou = true;
                    }
                }
            }

            if (adicionou) {
                System.out.println("Série \"" + selecionada.getNome() + "\" adicionada com sucesso.");
            } else {
                System.out.println("A série já está na lista selecionada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
        }
    }

    private Serie criarSerieAPartirDeJson(JsonElement show) {
        var obj = show.getAsJsonObject();

        String nome = obj.has("name") && !obj.get("name").isJsonNull() ? obj.get("name").getAsString() : "Indisponível";
        String idioma = obj.has("language") && !obj.get("language").isJsonNull() ? obj.get("language").getAsString() : "Indisponível";

        List<String> generos = new ArrayList<>();
        if (obj.has("genres") && obj.get("genres").isJsonArray()) {
            for (JsonElement g : obj.get("genres").getAsJsonArray()) {
                if (!g.isJsonNull()) generos.add(g.getAsString());
            }
        }

        double notaGeral = 0.0;
        if (obj.has("rating") && !obj.get("rating").isJsonNull()) {
            var rating = obj.get("rating").getAsJsonObject();
            if (rating.has("average") && !rating.get("average").isJsonNull()) {
                notaGeral = rating.get("average").getAsDouble();
            }
        }

        String estado = obj.has("status") && !obj.get("status").isJsonNull() ? obj.get("status").getAsString() : "Indisponível";
        String dataEstreia = obj.has("premiered") && !obj.get("premiered").isJsonNull() ? obj.get("premiered").getAsString() : "Indisponível";
        String dataTermino = obj.has("ended") && !obj.get("ended").isJsonNull() ? obj.get("ended").getAsString() : "Indisponível";

        String emissora = "Indisponível";
        if (obj.has("network") && !obj.get("network").isJsonNull()) {
            var net = obj.get("network").getAsJsonObject();
            if (net.has("name") && !net.get("name").isJsonNull()) {
                emissora = net.get("name").getAsString();
            }
        }

        return new Serie(nome, idioma, generos, notaGeral, estado, dataEstreia, dataTermino, emissora);
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
