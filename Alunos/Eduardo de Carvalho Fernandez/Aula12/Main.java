import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String cidade = "Sao Paulo";
        String apiKey = "aqui não tico tico" + //
                        ""; 
        try {
            String endpoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + cidade.replace(" ", "%20") + "/today?unitGroup=metric&key=" + apiKey + "&include=days";
            URL url = new URL(endpoint);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String linha;
            StringBuilder json = new StringBuilder();
            while ((linha = reader.readLine()) != null) {
                json.append(linha);
            }
            reader.close();

            Clima clima = ClimaParser.parse(json.toString(), cidade);
            if (clima != null) {
                System.out.println(clima);
            } else {
                System.out.println("Erro ao processar os dados do clima.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao consultar a API.");
        }
    }
}
