public class ClimaObj {
    String temperaturaAtual;
    String temperaturaMaxima;
    String temperaturaMinima;
    String umidade;
    String condicao;
    String precipitacao;
    String vento;

    void exibir() {
        System.out.println("\n=== Informações do Clima ===");
        System.out.println("Temperatura atual: " + temperaturaAtual + "°C");
        System.out.println("Temperatura máxima: " + temperaturaMaxima + "°C");
        System.out.println("Temperatura mínima: " + temperaturaMinima + "°C");
        System.out.println("Umidade: " + umidade + "%");
        System.out.println("Condição do tempo: " + condicao);
        System.out.println("Precipitação: " + precipitacao + " mm");
        System.out.println("Vento: " + vento + " km/h");
    }
}
