package weather;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = ".........";    // Coloque a chave da sua API aqui, apague os pontos e coloque ela

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        HttpClient client = HttpClient.newHttpClient();

        
        while (true) {
           
            System.out.print("\nDigite o nome da cidade: ");
            String cidade = scanner.nextLine();

            System.out.print("Digite a sigla do estado (ex: SP, RJ, BA): ");
            String estado = scanner.nextLine();

            
            String localizacao = cidade + ", " + estado;
            

            String cidadeCodificada = URLEncoder.encode(localizacao, StandardCharsets.UTF_8);

            String endpoint = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s?unitGroup=metric&include=current&key=%s&contentType=json",
                cidadeCodificada, API_KEY
            );

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                if (response.statusCode() == 200) {
                    WeatherParser.parse(json);
                } else {
                    System.out.println("Erro ao buscar dados para a localização '" + localizacao + "'. Verifique se os dados estão corretos.");
                    System.out.println("Resposta da API: " + response.body());
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Erro de conexão ao consultar a API: " + e.getMessage());
            }

            System.out.println("\nO que você deseja fazer?");
            System.out.println("1. Pesquisar outra cidade");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            if (!opcao.equals("1")) {
                
                break;
            }
            
        }

        System.out.println("\nPesquisa finalizada. Até a próxima!");
        
        scanner.close();
        
    }
}
