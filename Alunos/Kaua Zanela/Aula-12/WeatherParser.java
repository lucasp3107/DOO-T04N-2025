package weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherParser {

    public static void parse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            
            JsonNode current = root.path("currentConditions");

           
            JsonNode today = root.path("days").get(0);

            System.out.println("\n===== CLIMA EM " + root.path("resolvedAddress").asText() + " =====");
            System.out.println("Condição do tempo: " + current.path("conditions").asText());
            System.out.println("Temperatura atual: " + current.path("temp").asDouble() + "°C");
            System.out.println("-----------------------------------------");
           
            System.out.println("Temperatura máxima: " + today.path("tempmax").asDouble() + "°C");
            System.out.println("Temperatura mínima: " + today.path("tempmin").asDouble() + "°C");
            System.out.println("-----------------------------------------");
            System.out.println("Umidade do ar: " + current.path("humidity").asDouble() + "%");
            System.out.println("Precipitação hoje: " + current.path("precip").asDouble() + "mm");
            System.out.println("Velocidade do vento: " + current.path("windspeed").asDouble() + "km/h");
            System.out.println("Direção do vento: " + current.path("winddir").asInt() + "°");

        } catch (Exception e) {
            System.out.println("Erro ao processar a resposta JSON: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}
