import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "3HEPGNWZ5GSVCDTW3FCKAJ7PN";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        API api = new API(API_KEY);

        System.out.println("Bem-vindo ao Aplicativo de Clima!");
        System.out.print("Digite o nome da cidade em sua língua nativa (ex: São Paulo, London): ");
        String city = scanner.nextLine();

        try {
            Dados dados = api.getWeatherForCity(city);
            System.out.println(dados);
        } catch (IOException | InterruptedException e) {
            System.err.println("Ocorreu um erro ao buscar os dados do clima: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}