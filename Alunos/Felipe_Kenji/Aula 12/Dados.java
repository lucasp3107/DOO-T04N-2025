
public class Dados {
    private double currentTemp;
    private double maxTemp;
    private double minTemp;
    private double humidity;
    private String conditions;
    private double precipitation;
    private double windSpeed;
    private double windDirection;

    public Dados(double currentTemp, double maxTemp, double minTemp, double humidity,
                       String conditions, double precipitation, double windSpeed, double windDirection) {
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.conditions = conditions;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public double getCurrentTemp() { return currentTemp; }
    public double getMaxTemp() { return maxTemp; }
    public double getMinTemp() { return minTemp; }
    public double getHumidity() { return humidity; }
    public String getConditions() { return conditions; }
    public double getPrecipitation() { return precipitation; }
    public double getWindSpeed() { return windSpeed; }
    public double getWindDirection() { return windDirection; }

    @Override
    public String toString() {
        return String.format(
            "--- Dados do Clima para a Cidade ---\n" +
            "Temperatura Atual: %.1f°C\n" +
            "Máxima do Dia: %.1f°C\n" +
            "Mínima do Dia: %.1f°C\n" +
            "Umidade do Ar: %.1f%%\n" +
            "Condição do Tempo: %s\n" +
            "Precipitação: %.2f mm\n" +
            "Velocidade do Vento: %.1f km/h\n" +
            "Direção do Vento: %.1f°\n" +
            "------------------------------------",
            currentTemp, maxTemp, minTemp, humidity, conditions,
            precipitation, windSpeed, windDirection
        );
    }
}