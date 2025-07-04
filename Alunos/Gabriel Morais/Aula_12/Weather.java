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
        Temperatura atual: %.1fC
        Maxima: %.1fC | Minima: %.1fC
        Umidade: %.0f%%
        Condicao: %s
        Precipitacao: %.1f mm
        Vento: %.1f km/h, Direcao: %.0f
        """, city, temp, tempMax, tempMin, humidity, condition, precipitation, windSpeed, windDir);
    }
}
