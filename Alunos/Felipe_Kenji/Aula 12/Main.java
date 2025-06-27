import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "3HEPGNWZ5GSVCDTW3FCKAJ7PN";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        API api = new API(API_KEY);

        System.out.println("Bem-vindo ao Aplicativo de Clima!");
        System.out.println("Para sair, digite 'sair'.");

        while (true) {
            System.out.print("Digite o nome da cidade em sua língua nativa, para cidades que possuem mesmo nome, tente abreviar o estado ao lado. (ex: 'São Paulo', 'London', 'Cascavel, PR'c): ");
            String city = scanner.nextLine();

            if (city.equalsIgnoreCase("sair")) {
                System.out.println("Saindo do aplicativo. Até mais!");
                break;
            }

            try {
                Dados dados = api.getWeatherForCity(city);
                System.out.println(dados);
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro ao buscar dados do clima: " + e.getMessage());
                System.err.println("Por favor, tente novamente ou digite 'sair'.");
            }
            System.out.println();
        }

        scanner.close();
    }
}