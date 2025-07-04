import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI; // Importe a classe URI
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = entrada.nextLine();

        try {
            String chave = "9QJKJYB48MR5V4BEDBZDC2G52"; // Sua API KEY da Visual Crossing
            String urlApi = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                          + cidade + "?unitGroup=metric&key=" + chave + "&include=current";

            // Crie a URI primeiro para garantir a sintaxe correta e a codificação de caracteres
            URI uri = new URI(urlApi.replace(" ", "%20")); // Substitua espaços por %20 para evitar erros
            URL url = uri.toURL(); // Converte a URI em uma URL

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();

            String dados = resposta.toString();

            // O seu método `entre` e a classe `ClimaObj` não estão no código fornecido.
            // Vou supor que eles existem e a lógica para extrair os dados continua a mesma.
            String temp = entre(dados, "\"temp\":", ",");
            String tempMax = entre(dados, "\"tempmax\":", ",");
            String tempMin = entre(dados, "\"tempmin\":", ",");
            String umidade = entre(dados, "\"humidity\":", ",");
            String condicao = entre(dados, "\"conditions\":\"", "\"");
            String vento = entre(dados, "\"windspeed\":", ",");
            String chuva = entre(dados, "\"precip\":", ",");

            ClimaObj clima = new ClimaObj();
            clima.temperaturaAtual = temp;
            clima.temperaturaMaxima = tempMax;
            clima.temperaturaMinima = tempMin;
            clima.umidade = umidade;
            clima.condicao = condicao;
            clima.vento = vento;
            clima.precipitacao = chuva;

            clima.exibir();

        } catch (Exception e) {
            System.out.println("Erro ao consultar o clima: " + e.getMessage());
            e.printStackTrace(); // É bom imprimir o stack trace para depuração
        }

        entrada.close();
    }

    // Assumindo que este método existe no seu código
    public static String entre(String texto, String inicio, String fim) {
        int pos1 = texto.indexOf(inicio);
        if (pos1 == -1) return "";
        int pos2 = texto.indexOf(fim, pos1 + inicio.length());
        if (pos2 == -1) return "";
        return texto.substring(pos1 + inicio.length(), pos2);
    }
    
    // Você precisa ter a classe ClimaObj definida para que o código funcione.
    // Exemplo de como poderia ser a classe ClimaObj:
    static class ClimaObj {
        String temperaturaAtual;
        String temperaturaMaxima;
        String temperaturaMinima;
        String umidade;
        String condicao;
        String vento;
        String precipitacao;

        public void exibir() {
            System.out.println("--- Dados do Clima ---");
            System.out.println("Temperatura Atual: " + this.temperaturaAtual + "°C");
            System.out.println("Temperatura Máxima: " + this.temperaturaMaxima + "°C");
            System.out.println("Temperatura Mínima: " + this.temperaturaMinima + "°C");
            System.out.println("Umidade: " + this.umidade + "%");
            System.out.println("Condição: " + this.condicao);
            System.out.println("Velocidade do Vento: " + this.vento + " km/h");
            System.out.println("Precipitação: " + this.precipitacao + " mm");
        }
    }
}