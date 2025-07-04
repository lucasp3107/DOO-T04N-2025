package com.meuprojeto.climaap;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (Config.API_KEY == null || Config.API_KEY.isEmpty() || Config.API_KEY.equals("SUA_CHAVE_DA_API_AQUI")) {
            System.err.println("ERRO: Por favor, configure sua API_KEY no arquivo 'Config.java'.");
            System.err.println("Você pode obter uma chave gratuita em: https://www.visualcrossing.com/sign-up");
            System.err.println("O aplicativo não pode funcionar sem a API Key correta.");
            return;
        }

        ServicoClima servico = new ServicoClima(Config.API_KEY);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nDigite o nome da cidade (ou 'sair' para encerrar): ");
            String cidade = scanner.nextLine().trim();

            if (cidade.equalsIgnoreCase("sair")) {
                System.out.println("Saindo do aplicativo. Até mais!");
                break;
            }

            if (cidade.isEmpty()) {
                System.out.println("Por favor, digite um nome de cidade válido.");
                continue;
            }

            Clima dadosClima = servico.obterDadosClimaticos(cidade);

            if (dadosClima != null) {
                dadosClima.exibirInformacoes();
            } else {
                System.out.println("Falha ao obter dados climáticos para '" + cidade + "'. Tente novamente.");
            }
        }
        scanner.close();
    }
}