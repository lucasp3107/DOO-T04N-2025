package com.example;

public class Clima {
    private double temperaturaAtual;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private double umidade;
    private String condicaoTempo;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;

    public Clima(double temperaturaAtual, double temperaturaMaxima, double temperaturaMinima, double umidade, String condicaoTempo, double precipitacao, double velocidadeVento, String direcaoVento) {
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.umidade = umidade;
        this.condicaoTempo = condicaoTempo;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public double getTemperaturaAtual() { return temperaturaAtual; }
    public double getTemperaturaMaxima() { return temperaturaMaxima; }
    public double getTemperaturaMinima() { return temperaturaMinima; }
    public double getUmidade() { return umidade; }
    public String getCondicaoTempo() { return condicaoTempo; }
    public double getPrecipitacao() { return precipitacao; }
    public double getVelocidadeVento() { return velocidadeVento; }
    public String getDirecaoVento() { return direcaoVento; }

    @Override
    public String toString() {
        return "\n--- Informacoes do Clima ---" +
                "\n  Temperatura Atual: " + temperaturaAtual + "°C" +
                "\n  Temperatura Maxima: " + temperaturaMaxima + "°C" +
                "\n  Temperatura Minima: " + temperaturaMinima + "°C" +
                "\n  Umidade do Ar: " + umidade + "%" +
                "\n  Condicao do Tempo: " + condicaoTempo +
                "\n  Precipitacao: " + precipitacao + " mm" +
                "\n  Velocidade do Vento: " + velocidadeVento + " km/h" +
                "\n  Direcao do Vento: " + direcaoVento;
    }
}
