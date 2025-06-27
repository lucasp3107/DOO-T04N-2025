import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class API {
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
    private final String apiKey;
    private final HttpClient httpClient;

    public API(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    public Dados getWeatherForCity(String city) throws IOException, InterruptedException {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        String urlString = String.format("%s/%s/%s?unitGroup=metric&include=days,current&key=%s",
                BASE_URL, encodedCity, today, apiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("Erro ao buscar dados do clima: " + response.statusCode() + " - " + response.body());
            throw new IOException("Falha na requisição à API: " + response.body());
        }

        return parseWeatherData(response.body());
    }

    private Dados parseWeatherData(String jsonResponse) throws IOException {
        double currentTemp = 0.0;
        double maxTemp = 0.0;
        double minTemp = 0.0;
        double humidity = 0.0;
        String conditions = "N/A";
        double precipitation = 0.0;
        double windSpeed = 0.0;
        double windDirection = 0.0;

        try {
            // Parseamento manual para currentConditions
            int currentConditionsStart = jsonResponse.indexOf("\"currentConditions\":");
            if (currentConditionsStart != -1) {
                int objStart = jsonResponse.indexOf("{", currentConditionsStart);
                int objEnd = findClosingBrace(jsonResponse, objStart);
                String currentConditionsJson = jsonResponse.substring(objStart, objEnd + 1);

                currentTemp = extractDouble(currentConditionsJson, "temp");
                humidity = extractDouble(currentConditionsJson, "humidity");
                conditions = extractString(currentConditionsJson, "conditions");
                windSpeed = extractDouble(currentConditionsJson, "windspeed");
                windDirection = extractDouble(currentConditionsJson, "winddir");
            }

            // Parseamento manual para days (primeiro dia)
            int daysStart = jsonResponse.indexOf("\"days\":");
            if (daysStart != -1) {
                int arrayStart = jsonResponse.indexOf("[", daysStart);
                int arrayEnd = findClosingBracket(jsonResponse, arrayStart);
                String daysArrayJson = jsonResponse.substring(arrayStart, arrayEnd + 1);

                // Assumindo que o objeto do primeiro dia está diretamente dentro do array
                int firstDayObjStart = daysArrayJson.indexOf("{");
                int firstDayObjEnd = findClosingBrace(daysArrayJson, firstDayObjStart);
                String firstDayJson = daysArrayJson.substring(firstDayObjStart, firstDayObjEnd + 1);

                maxTemp = extractDouble(firstDayJson, "tempmax");
                minTemp = extractDouble(firstDayJson, "tempmin");
                precipitation = extractDouble(firstDayJson, "precip");
            }

        } catch (Exception e) {
            throw new IOException("Erro ao parsear dados JSON manualmente: " + e.getMessage() + "\nJSON: " + jsonResponse, e);
        }

        return new Dados(currentTemp, maxTemp, minTemp, humidity,
                               conditions, precipitation, windSpeed, windDirection);
    }

    // Método auxiliar para extrair um valor double de uma string de objeto JSON simples
    private double extractDouble(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int index = json.indexOf(searchKey);
        if (index == -1) return 0.0;

        int valueStart = index + searchKey.length();
        int valueEnd = valueStart;
        while (valueEnd < json.length() && (Character.isDigit(json.charAt(valueEnd)) || json.charAt(valueEnd) == '.' || json.charAt(valueEnd) == '-')) {
            valueEnd++;
        }
        String valueStr = json.substring(valueStart, valueEnd);
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Método auxiliar para extrair um valor string de uma string de objeto JSON simples
    private String extractString(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int index = json.indexOf(searchKey);
        if (index == -1) return "N/A";

        int valueStart = index + searchKey.length();
        int valueEnd = json.indexOf("\"", valueStart);
        if (valueEnd == -1) return "N/A";

        return json.substring(valueStart, valueEnd);
    }

    // Auxiliar básico para encontrar a chave de fechamento de um objeto
    private int findClosingBrace(String json, int startIndex) {
        int braceCount = 0;
        for (int i = startIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
            }
            if (braceCount == 0 && i > startIndex) {
                return i;
            }
        }
        return -1;
    }

    // Auxiliar básico para encontrar o colchete de fechamento de um array
    private int findClosingBracket(String json, int startIndex) {
        int bracketCount = 0;
        for (int i = startIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') {
                bracketCount++;
            } else if (c == ']') {
                bracketCount--;
            }
            if (bracketCount == 0 && i > startIndex) {
                return i;
            }
        }
        return -1;
    }
}