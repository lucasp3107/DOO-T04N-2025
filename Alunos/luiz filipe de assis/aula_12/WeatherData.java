public class WeatherData {
    private String location;
    private String description;
    private double currentTemp;
    private double maxTemp;
    private double minTemp;
    private double humidity;
    private String conditions;
    private double precipitation;
    private double windSpeed;
    private double windDirection;

    // Construtores
    public WeatherData() {}

    public WeatherData(String location, String description, double currentTemp, 
                      double maxTemp, double minTemp, double humidity, 
                      String conditions, double precipitation, 
                      double windSpeed, double windDirection) {
        this.location = location;
        this.description = description;
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.conditions = conditions;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    // Getters e Setters
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getCurrentTemp() { return currentTemp; }
    public void setCurrentTemp(double currentTemp) { this.currentTemp = currentTemp; }

    public double getMaxTemp() { return maxTemp; }
    public void setMaxTemp(double maxTemp) { this.maxTemp = maxTemp; }

    public double getMinTemp() { return minTemp; }
    public void setMinTemp(double minTemp) { this.minTemp = minTemp; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public String getConditions() { return conditions; }
    public void setConditions(String conditions) { this.conditions = conditions; }

    public double getPrecipitation() { return precipitation; }
    public void setPrecipitation(double precipitation) { this.precipitation = precipitation; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getWindDirection() { return windDirection; }
    public void setWindDirection(double windDirection) { this.windDirection = windDirection; }
}