import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalTime;
import java.util.List;

public class Funcoes {
    Scanner scan = new Scanner(System.in);

    public void buscarTempo() {
        try {
            System.out.println("Insira a sua chave da API: ");
            String chaveApi = scan.next();
            scan.nextLine(); // limpa scan
            System.out.println("Insira o nome da cidade que deseja pesquisar: ");
            String nomeCidade = scan.nextLine();
            nomeCidade = URLEncoder.encode(nomeCidade, StandardCharsets.UTF_8);

            String urlStr = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" +
                            nomeCidade + "?key=" + chaveApi + "&unitGroup=metric&include=days,hours";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new GsonBuilder().create();
                String jsonString = response.body();
                WeatherData data = gson.fromJson(jsonString, WeatherData.class);

                Day hoje = data.days.get(0); 
                Hour horaAtual = buscarHoraMaisProxima(hoje.hours); 

                System.out.println("Local: " + data.resolvedAddress);
                System.out.println("Data: " + hoje.datetime);
                System.out.println("Temperatura atual: " + horaAtual.temp + "°C");
                System.out.println("Máxima do dia: " + hoje.tempmax + "°C");
                System.out.println("Mínima do dia: " + hoje.tempmin + "°C");
                System.out.println("Umidade do ar: " + horaAtual.humidity + "%");
                System.out.println("Condição do tempo: " + horaAtual.conditions);
                System.out.println("Precipitação: " + horaAtual.precip + " mm");
                System.out.println("Velocidade do vento: " + horaAtual.windspeed + " km/h");
                System.out.println("Direção do vento: " + horaAtual.winddir + "°");

            } else if (response.statusCode() == 401) {
                System.out.println("Chave da API inválida (401).");
            } else if (response.statusCode() == 400) {
                System.out.println("Requisição malformada ou cidade inválida (400).");
            } else if (response.statusCode() == 404) {
                System.out.println("Cidade não encontrada (404).");
            } else {
                System.out.println("Erro: código de status HTTP " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private Hour buscarHoraMaisProxima(List<Hour> horas) {
        LocalTime agora = LocalTime.now();
        Hour maisProxima = horas.get(0);
        int menorDiferenca = Integer.MAX_VALUE;

        for (Hour h : horas) {
            try {
                LocalTime horaObjeto = LocalTime.parse(h.datetime);
                int diferenca = Math.abs(horaObjeto.getHour() - agora.getHour());
                if (diferenca < menorDiferenca) {
                    menorDiferenca = diferenca;
                    maisProxima = h;
                }
            } catch (Exception ignored) {}
        }

        return maisProxima;
    }
}
