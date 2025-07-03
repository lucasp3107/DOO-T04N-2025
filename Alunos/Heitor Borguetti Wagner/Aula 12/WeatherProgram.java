import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherProgram {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the city name: ");
        String city = input.nextLine();

        try {
            String apiKey = "INSERT KEY";
            String apiUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" 
                          + city + "?unitGroup=metric&key=" + apiKey + "&include=current";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String data = response.toString();

            String temp = between(data, "\"temp\":", ",");
            String tempMax = between(data, "\"tempmax\":", ",");
            String tempMin = between(data, "\"tempmin\":", ",");
            String humidity = between(data, "\"humidity\":", ",");
            String condition = between(data, "\"conditions\":\"", "\"");
            String wind = between(data, "\"windspeed\":", ",");
            String rain = between(data, "\"precip\":", ",");

            WeatherObject weather = new WeatherObject();
            weather.currentTemperature = temp;
            weather.maximumTemperature = tempMax;
            weather.minimumTemperature = tempMin;
            weather.humidity = humidity;
            weather.condition = condition;
            weather.wind = wind;
            weather.precipitation = rain;

            weather.display();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        input.close();
    }

    public static String between(String text, String start, String end) {
        int pos1 = text.indexOf(start);
        if (pos1 == -1) return "";
        int pos2 = text.indexOf(end, pos1 + start.length());
        if (pos2 == -1) return "";
        return text.substring(pos1 + start.length(), pos2);
    }
}
