package app;

import model.ClimaObj;
import service.ClimaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ClimaService climaService = new ClimaService();

        System.out.print("Digite o nome da cidade: ");
        String cidade = entrada.nextLine();

        try {
            ClimaObj clima = climaService.buscarClima(cidade);
            clima.exibir();
        } catch (Exception e) {
            System.out.println("Erro ao buscar clima: " + e.getMessage());
        }

        entrada.close();
    }
}
