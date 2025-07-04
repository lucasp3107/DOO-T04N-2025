package com.previsao_do_tempo.view;

import com.previsao_do_tempo.model.Clima;
import com.previsao_do_tempo.util.ConsoleInput;

public class WeatherView {

    public void exibirBoasVindas() {
        System.out.println("=========================================");
        System.out.println("     Bem-vindo(a) ao Previsão do Tempo!");
        System.out.println("=========================================");
    }

    public int exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Consultar Clima");
        System.out.println("2. Sair");
        System.out.println("==========================");
        return ConsoleInput.lerIntComLimite("Escolha uma opção: ", 1, 2);
    }

    public String pedirNomeCidade() {
        return ConsoleInput.lerString("Digite o nome da cidade: ");
    }

    public void exibirInfoClima(Clima clima) {
        if (clima == null) {
            System.out.println("\nNão foi possível obter as informações do clima para a cidade.");
            return;
        }
        System.out.println("\n===== INFORMAÇÕES DO CLIMA =====");
        System.out.println("Cidade: " + clima.getResolvedAddress());
        System.out.println("Temperatura Atual: " + String.format("%.1f", clima.getTemperaturaAtual()) + "°C");
        System.out.println("Temperatura Máxima (Hoje): " + String.format("%.1f", clima.getTemperaturaMaximaDia()) + "°C");
        System.out.println("Temperatura Mínima (Hoje): " + String.format("%.1f", clima.getTemperaturaMinimaDia()) + "°C");
        System.out.println("Umidade do Ar: " + String.format("%.1f", clima.getUmidadeAr()) + "%");
        System.out.println("Condição do Tempo: " + clima.getCondicaoTempo());
        System.out.println("Precipitação: " + String.format("%.1f", clima.getPrecipitacao()) + " mm");
        System.out.println("Velocidade do Vento: " + String.format("%.1f", clima.getVelocidadeVento()) + " km/h");
        System.out.println("Direção do Vento: " + clima.getDirecaoVentoTexto());
        System.out.println("=================================");
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}