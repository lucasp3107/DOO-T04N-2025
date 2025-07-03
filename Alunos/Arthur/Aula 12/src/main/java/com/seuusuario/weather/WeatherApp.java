package com.seuusuario.weather;

public class WeatherApp {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: mvn exec:java -Dexec.args=\"<API_KEY> \\\"Cidade,BR\\\"\"");
            System.exit(1);
        }
        String apiKey = args[0];
        String city   = args[1];

        WeatherService service = new WeatherService(apiKey);
        try {
            WeatherData data = service.getWeather(city);
            System.out.println(data);
        } catch (Exception e) {
            System.err.println("Falha ao obter o clima: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
