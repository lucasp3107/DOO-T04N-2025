package br.com.seunome.prova2tri; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Collections; // Para Collections.emptyList()

public class SerieManager {
    private Usuario usuario;
    private TVMazeService tvMazeService;
    private List<Serie> seriesCache;

    private static final String USUARIO_DATA_FILE = "usuario_series_data.json";

    public SerieManager() {
        this.tvMazeService = new TVMazeService();
        this.seriesCache = new ArrayList<>();
        carregarDadosUsuario();
    }

    private void carregarDadosUsuario() {
        try {
            usuario = JsonUtil.carregarUsuario(USUARIO_DATA_FILE);
            if (usuario == null) {
                System.out.println("Bem-vindo(a) ao seu Rastreador de Séries de TV!");
                Scanner tempScanner = new Scanner(System.in);
                System.out.print("Por favor, digite seu nome ou apelido: ");
                String nomeDigitado = tempScanner.nextLine();
                usuario = new Usuario(nomeDigitado);
                salvarDadosUsuario();
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do usuário. Criando novo perfil. Erro: " + e.getMessage());
            usuario = new Usuario("Convidado");
        }
    }

    public void salvarDadosUsuario() {
        try {
            JsonUtil.salvarUsuario(usuario, USUARIO_DATA_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados do usuário: " + e.getMessage());
        }
    }

    public String getNomeUsuario() {
        return usuario.getNome();
    }

    public List<Serie> buscarSeries(String termoBusca) {
        try {
            List<Serie> resultados = tvMazeService.buscarSeriesPorNome(termoBusca);
            this.seriesCache.clear();
            this.seriesCache.addAll(resultados);
            return resultados;
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar séries na API. Verifique sua conexão. Erro: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void adicionarFavorito(Serie serie) { usuario.adicionarFavorito(serie); salvarDadosUsuario(); }
    public void removerFavorito(Serie serie) { usuario.removerFavorito(serie); salvarDadosUsuario(); }
    public void adicionarAssistida(Serie serie) { usuario.adicionarAssistida(serie); salvarDadosUsuario(); }
    public void removerAssistida(Serie serie) { usuario.removerAssistida(serie); salvarDadosUsuario(); }
    public void adicionarDesejaAssistir(Serie serie) { usuario.adicionarDesejaAssistir(serie); salvarDadosUsuario(); }
    public void removerDesejaAssistir(Serie serie) { usuario.removerDesejaAssistir(serie); salvarDadosUsuario(); }

    public List<Serie> getFavoritos() { return usuario.getFavoritos(); }
    public List<Serie> getAssistidas() { return usuario.getAssistidas(); }
    public List<Serie> getDesejaAssistir() { return usuario.getDesejaAssistir(); }

    public List<Serie> ordenarSeries(List<Serie> lista, String criterio) {
        List<Serie> sortedList = new ArrayList<>(lista);
        switch (criterio.toLowerCase()) {
            case "nome":
                sortedList.sort(Comparator.comparing(Serie::getNome, Comparator.nullsLast(String::compareToIgnoreCase)));
                break;
            case "nota":
                sortedList.sort(Comparator.comparing(Serie::getNotaGeral, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "estado":
                sortedList.sort(Comparator.comparing(Serie::getEstado, Comparator.nullsLast(String::compareToIgnoreCase)));
                break;
            case "dataestreia":
                sortedList.sort(Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
            default:
                System.out.println("Critério de ordenação inválido.");
        }
        return sortedList;
    }

    public Serie encontrarSeriePorNumero(int numero, List<Serie> lista) {
        if (numero > 0 && numero <= lista.size()) {
            return lista.get(numero - 1);
        }
        return null;
    }
}