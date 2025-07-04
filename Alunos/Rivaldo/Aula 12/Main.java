package com.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VisualCrossingService service = new VisualCrossingService();

        System.out.println("Bem-vindo ao Consultor de Clima!");
        System.out.print("Por favor, digite sua chave da API (API Key) da Visual Crossing: ");
        String apiKey = scanner.nextLine();

        while (true) {
            System.out.print("\nDigite o nome da cidade para consultar o clima (ou 'sair' para encerrar): ");
            String cidade = scanner.nextLine();

            if (cidade.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando o programa. Ate mais!");
                break;
            }

            try {
                Clima clima = service.getClima(cidade, apiKey);
                System.out.println(clima);
            } catch (IOException e) {
                System.err.println("Erro de comunicacao com a API: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("A operacao foi interrompida: " + e.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
