package Aula12.app;

import Aula12.model.WeatherInfo;
import Aula12.service.WeatherService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherService weatherService = new WeatherService();

        System.out.println("Informe o nome da cidade:");
        String cidade = scanner.nextLine();

        WeatherInfo info = weatherService.getWeather(cidade);

        if (info != null) {
            System.out.println("\nPrevisão para: " + info.city);
            System.out.println("Temperatura atual: " + info.temperature + "°C");
            System.out.println("Temperatura máxima: " + info.maxTemperature + "°C");
            System.out.println("Temperatura mínima: " + info.minTemperature + "°C");
            System.out.println("Umidade do ar: " + info.humidity + "%");
            System.out.println("Condições: " + info.conditions);
            System.out.println("Precipitação: " + info.precipitation + "mm");
            System.out.println("Velocidade do vento: " + info.windSpeed + " km/h");
            System.out.println("Direção do vento: " + info.windDirection);
        } else {
            System.out.println("Não foi possível obter informações para " + cidade);
        }
    }
}
