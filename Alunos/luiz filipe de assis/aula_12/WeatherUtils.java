//WeatherUtils.java
public class WeatherUtils {
 
 public static String getWindDirection(double degrees) {
     String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
                           "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
     
     int index = (int) Math.round(degrees / 22.5) % 16;
     return directions[index] + " (" + (int)degrees + "°)";
 }
 
 public static String formatTemperature(double temp) {
     return String.format("%.1f°C", temp);
 }
 
 public static String formatHumidity(double humidity) {
     return String.format("%.1f%%", humidity);
 }
 
 public static String formatPrecipitation(double precipitation) {
     return String.format("%.1f mm", precipitation);
 }
 
 public static String formatWindSpeed(double speed) {
     return String.format("%.1f km/h", speed);
 }
}