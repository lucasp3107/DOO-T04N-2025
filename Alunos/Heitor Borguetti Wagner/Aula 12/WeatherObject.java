public class WeatherObject {
    String currentTemperature;
    String maximumTemperature;
    String minimumTemperature;
    String humidity;
    String condition;
    String precipitation;
    String wind;

    void display() {
        System.out.println("Current Temperature: " + currentTemperature + "°C");
        System.out.println("Maximum Temperature: " + maximumTemperature + "°C");
        System.out.println("Minimum Temperature: " + minimumTemperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Weather Condition: " + condition);
        System.out.println("Precipitation: " + precipitation + " mm");
        System.out.println("Wind: " + wind + " km/h");
    }
}
