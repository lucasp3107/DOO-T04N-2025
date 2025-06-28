package com.seuusuario.weather;

public class WeatherData {
    private final double temp;
    private final double tempMin;
    private final double tempMax;
    private final double humidity;
    private final double precipitation;
    private final double windSpeed;
    private final double windDirection;
    private final String conditions;

    public WeatherData(double temp, double tempMin, double tempMax,
                       double humidity, double precipitation,
                       double windSpeed, double windDirection,
                       String conditions) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.conditions = conditions;
    }

    public double getTemp()          { return temp; }
    public double getTempMin()       { return tempMin; }
    public double getTempMax()       { return tempMax; }
    public double getHumidity()      { return humidity; }
    public double getPrecipitation() { return precipitation; }
    public double getWindSpeed()     { return windSpeed; }
    public double getWindDirection() { return windDirection; }
    public String getConditions()    { return conditions; }

    @Override
    public String toString() {
        return String.format(
            "Condições para hoje:%n" +
            " - Temperatura agora: %.1f°C%n" +
            " - Mín: %.1f°C, Máx: %.1f°C%n" +
            " - Umidade: %.0f%%%n" +
            " - Condição: %s%n" +
            " - Precipitação: %.1f mm%n" +
            " - Vento: %.1f km/h (dir: %.0f°)%n",
            temp, tempMin, tempMax, humidity, conditions,
            precipitation, windSpeed, windDirection
        );
    }
}
