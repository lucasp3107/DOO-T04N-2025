import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WeatherService service = new WeatherService();

        System.out.print("Digite o nome da cidade para consultar o clima: ");
        String cidade = sc.nextLine();

        Weather clima = service.getWeather(cidade);

        if (clima != null) {
            System.out.println(clima);
        } else {
            System.out.println("Não foi possível obter as informações do clima para a cidade informada.");
        }

        sc.close();
    }
}
