import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        try {
            WeatherInfo clima = WeatherService.buscarClima(cidade);
            System.out.println(clima);
        } catch (Exception e) {
            System.err.println("Erro ao buscar informações do clima: " + e.getMessage());
        }

        scanner.close();
    }
}