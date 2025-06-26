package PROVA_2TRI;

import PROVA_2TRI.Adapters.TvAmazeSerieAdapter;
import PROVA_2TRI.Adapters.JsonPersistAdapter;
import PROVA_2TRI.Controllers.SerieController;
import PROVA_2TRI.Controllers.UserController;
import PROVA_2TRI.Entities.User;
import PROVA_2TRI.Interfaces.ISeriesApi;
import PROVA_2TRI.Repository.SerieRepository;
import PROVA_2TRI.Interfaces.IPersist;
import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.util.Scanner;

public class App {

    private final SerieController serieController;
    private final UserController userController;
    private final Scanner sc;

    public App() {
        this.sc = new Scanner(System.in);

        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();
        IPersist persistAdapter = new JsonPersistAdapter();

        ISeriesApi serieApi = new TvAmazeSerieAdapter(
                httpClient,
                gson,
                persistAdapter
        );
        SerieRepository repository = new SerieRepository(persistAdapter, serieApi, gson);

        this.serieController = new SerieController(serieApi, repository, sc);
        this.userController = new UserController(sc);
    }

    public static void main(String[] args) {
        try {
            App app = new App();
            app.run();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro na execução do programa: " + e.getMessage());
            System.out.println("Gostaria de tentar reabrir o sistema? (S/N)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("S")) {
                main(args);
            } else {
                System.out.println("Obrigado por usar o sistema!");
            }
        }
    }

    public void run() throws Exception {
        serieController.getSeries(sc);
        User user = userController.createUser();
        System.out.println("Olá " + user.name + ", seja bem-vindo ao sistema!");
        showMenu();
    }

    private void showMenu() {
        System.out.println("\n ---------------------------------");

        // todas as séries
        System.out.println("[1] - Consultar séries");
        System.out.println("[2] - Filtrar séries");

        // favoritos
        System.out.println("[3] - Criar favoritos");
        System.out.println("[4] - Remover favoritos");
        System.out.println("[5] - Listar favoritos");

        // todas as séries
        System.out.println("[6] - Marcar séries como assistidas");

        // ver depois
        System.out.println("[7] - Listar séries assistidas");
        System.out.println("[8] - Criar séries para ver depois");
        System.out.println("[9] - Remover séries para ver depois");
        System.out.println("[10] - Listar séries para ver depois");

        // ordenar listas
        System.out.println("[11] - Ordenar listas de séries");

        // sair
        System.out.println("[0] - Sair");

        System.out.print("Digite a opção desejada: ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                // listar todas as séries
                serieController.listSeries(serieController.series);
                break;
            case "2":
                // filtrar séries
                serieController.executeFilterSeries(serieController.series);
                break;
            case "3":
                // criar favoritos
                serieController.createFavorites();
                break;
            case "4":
                // remover favoritos
                serieController.removeFavorites();
                break;
            case "5":
                // listar favoritos
                serieController.listSeries(serieController.favorites);
                break;
            case "6":
                // marcar como assistida
                serieController.markAsWatched();
                break;
            case "7":
                // listar assistidas
                serieController.listSeriesWatched();
                break;
            case "8":
                // criar para ver depois
                serieController.createToSeeLater();
                break;
            case "9":
                // remover para ver depois
                serieController.removeToSeeLater();
                break;
            case "10":
                // listar para ver depois
                serieController.listSeries(serieController.toSeeLater);
                break;
            case "11":
                // ordenar listas
                serieController.listOrderSeries();
                break;
            case "0":
                System.out.println("Saindo...");
                sc.close();
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
        // chama o menu novamente
        showMenu();
    }
}
