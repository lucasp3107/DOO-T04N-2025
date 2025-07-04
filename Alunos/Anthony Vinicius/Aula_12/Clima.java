package com.meuprojeto.climaap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clima {
    private String localidade;
    private double temperaturaAtual;
    private double tempMax;
    private double tempMin;
    private double umidade;
    private String condicaoTempo;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;
    private LocalDateTime dataHoraConsulta;

    public Clima(String localidade, double temperaturaAtual, double tempMax, double tempMin,
                 double umidade, String condicaoTempo, double precipitacao,
                 double velocidadeVento, String direcaoVento) {
        this.localidade = localidade;
        this.temperaturaAtual = temperaturaAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.umidade = umidade;
        this.condicaoTempo = condicaoTempo;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
        this.dataHoraConsulta = LocalDateTime.now();
    }

    // Getters
    public String getLocalidade() { return localidade; }
    public double getTemperaturaAtual() { return temperaturaAtual; }
    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
    public double getUmidade() { return umidade; }
    public String getCondicaoTempo() { return condicaoTempo; }
    public double getPrecipitacao() { return precipitacao; }
    public double getVelocidadeVento() { return velocidadeVento; }
    public String getDirecaoVento() { return direcaoVento; }
    public LocalDateTime getDataHoraConsulta() { return dataHoraConsulta; }

    public void exibirInformacoes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = dataHoraConsulta.format(formatter);

        System.out.println("\n--- Clima em " + localidade + " ---");
        System.out.println("Data/Hora da Consulta: " + formattedDateTime);
        System.out.printf("Temperatura Atual: %.1f°C\n", temperaturaAtual);
        System.out.printf("Máxima para o Dia: %.1f°C\n", tempMax);
        System.out.printf("Mínima para o Dia: %.1f°C\n", tempMin);
        System.out.printf("Umidade do Ar: %.1f%%\n", umidade);
        System.out.println("Condição do Tempo: " + condicaoTempo);
        if (precipitacao > 0) {
            System.out.printf("Precipitação (últimas 24h): %.1f mm\n", precipitacao);
        } else {
            System.out.println("Precipitação: Nenhuma nas últimas 24h");
        }
        System.out.printf("Vento: %.1f km/h (%s)\n", velocidadeVento, direcaoVento);
        System.out.println("---------------------------------");
    }
}