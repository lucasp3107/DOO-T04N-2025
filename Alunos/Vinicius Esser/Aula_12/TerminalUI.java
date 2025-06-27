package br.com.vinicius.clima;

import java.io.IOException;         
import java.lang.InterruptedException; 
import java.util.InputMismatchException; 
import java.util.Scanner;

public class TerminalUI {
    private WeatherService weatherService;
    private Scanner scanner;

    public TerminalUI() {
        this.weatherService = new WeatherService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("--- Aplicativo de Consulta de Clima ---");
        System.out.println("Obtendo dados do clima em tempo real!");

        String cidade;
        do {
            System.out.println("\n--- Menu ---");
            System.out.print("Digite o nome da cidade para consultar (ou '0' para sair): ");
            cidade = scanner.nextLine();

            if (cidade.equals("0")) {
                System.out.println("Saindo do aplicativo. Até mais!");
                break;
            }

            if (!cidade.trim().isEmpty()) {
                try {
                    Clima clima = weatherService.getWeatherData(cidade);
                    if (clima != null) {
                        System.out.println(clima.toString()); 
                    } else {
                        System.out.println("Não foi possível obter os dados do clima para '" + cidade + "'.");
                    }
                } catch (IllegalArgumentException e) { 
                    System.err.println("Erro na entrada: " + e.getMessage());
                } catch (IOException | InterruptedException e) { 
                    System.err.println("Erro ao se comunicar com o serviço de clima: " + e.getMessage());
                    System.err.println("Verifique sua conexão com a internet ou se a chave da API está correta.");
                    System.err.println("Detalhes do erro: " + e.getMessage()); 
                } catch (Exception e) { 
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                    e.printStackTrace(); 
                }
            } else {
                System.out.println("Por favor, digite um nome de cidade válido.");
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine(); 

        } while (true); 

        scanner.close(); 
    }
}