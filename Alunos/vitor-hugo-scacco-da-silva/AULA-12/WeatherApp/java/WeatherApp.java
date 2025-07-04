import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        String apiKey = "SUA_CHAVE_AQUI";
        WeatherService service = new WeatherService(apiKey);

        try {
            WeatherInfo info = service.getWeather(cidade);
            System.out.println("Clima em " + cidade + ":");
            System.out.println("Temperatura atual: " + info.getTempAtual() + "°C");
            System.out.println("Temperatura máxima: " + info.getTempMax() + "°C");
            System.out.println("Temperatura mínima: " + info.getTempMin() + "°C");
            System.out.println("Umidade: " + info.getUmidade() + "%");
            System.out.println("Condição: " + info.getCondicao());
            System.out.println("Precipitação: " + info.getPrecipitacao() + " mm");
            System.out.println("Vento: " + info.getVentoVel() + " km/h " + info.getVentoDir());
        } catch (Exception e) {
            System.out.println("Erro ao obter dados: " + e.getMessage());
        }

        scanner.close();
    }
}
