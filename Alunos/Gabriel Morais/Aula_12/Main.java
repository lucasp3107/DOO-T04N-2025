import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a cidade: ");
        String cidade = sc.nextLine();

        try {
            String apiKey = "Ponha a chave aq";
            String urlStr = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + cidade.replace(" ", "%20") + "/today?unitGroup=metric&key=" + apiKey + "&contentType=json";

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String linha;
            while ((linha = in.readLine()) != null) json.append(linha);
            in.close();

            JSONObject obj = new JSONObject(json.toString());
            JSONArray days = obj.getJSONArray("days");
            JSONObject today = days.getJSONObject(0);

            Weather d = new Weather();
            d.city = obj.getString("resolvedAddress");
            d.temp = today.getDouble("temp");
            d.tempMax = today.getDouble("tempmax");
            d.tempMin = today.getDouble("tempmin");
            d.humidity = today.getDouble("humidity");
            d.condition = today.getString("conditions");
            d.precipitation = today.getDouble("precip");
            d.windSpeed = today.getDouble("windspeed");
            d.windDir = today.getDouble("winddir");

            System.out.println("\n" + d);
        } catch (Exception e) {
            System.out.println("Erro ao obter dados: " + e.getMessage());
        }
    }
}
