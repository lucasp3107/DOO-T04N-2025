package com.previsao_do_tempo;

import com.previsao_do_tempo.model.Clima;
import com.previsao_do_tempo.service.WeatherApiService;
import com.previsao_do_tempo.util.ConsoleInput;
import com.previsao_do_tempo.view.WeatherView;

public class Main {
    public static void main(String[] args) {
        WeatherApiService apiService = new WeatherApiService();
        WeatherView view = new WeatherView();

        view.exibirBoasVindas();

        int opcao = 0;
        do {
            try {
                opcao = view.exibirMenuPrincipal();

                switch (opcao) {
                    case 1: // Consultar Clima
                        String cidade = view.pedirNomeCidade();
                        if (cidade.isEmpty()) {
                            view.exibirMensagem("Nome da cidade não pode ser vazio.");
                            break;
                        }
                        Clima clima = apiService.getClimaParaCidade(cidade);
                        view.exibirInfoClima(clima);
                        break;
                    case 2: // Sair
                        view.exibirMensagem("Saindo do programa. Até mais!");
                        break;
                    default:
                        view.exibirMensagem("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("\n*** Ocorreu um erro: " + e.getMessage());
                // e.printStackTrace(); // Descomente para depuração completa
                view.exibirMensagem("Não foi possível consultar o clima. Verifique o nome da cidade ou sua conexão.");
            }
        } while (opcao != 2);

        ConsoleInput.fecharScanner();
    }
}