package weather;

public class Weather {
    String city;
    double temp;
    double tempMax;
    double tempMin;
    double humidity;
    String condition;
    double precipitation;
    double windSpeed;
    double windDir;

    public String toString() {
        return String.format("""
        Clima em %s:
        Temperatura atual: %.1f°C
        Máxima: %.1f°C | Mínima: %.1f°C
        Umidade: %.0f%%
        Condição: %s
        Precipitação: %.1f mm
        Vento: %.1f km/h, Direção: %.0f°
        """, city, temp, tempMax, tempMin, humidity, condition, precipitation, windSpeed, windDir);
    }
}
