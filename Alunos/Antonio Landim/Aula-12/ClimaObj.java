package model;

public class ClimaObj {
    private String temperaturaAtual;
    private String temperaturaMaxima;
    private String temperaturaMinima;
    private String umidade;
    private String condicao;
    private String precipitacao;
    private String vento;

    public ClimaObj(String temperaturaAtual, String temperaturaMaxima, String temperaturaMinima,
                    String umidade, String condicao, String precipitacao, String vento) {
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.vento = vento;
    }

    public void exibir() {
        System.out.println("Temperatura atual: " + temperaturaAtual + "°C");
        System.out.println("Temperatura máxima: " + temperaturaMaxima + "°C");
        System.out.println("Temperatura mínima: " + temperaturaMinima + "°C");
        System.out.println("Umidade: " + umidade + "%");
        System.out.println("Condição do tempo: " + condicao);
        System.out.println("Precipitação: " + precipitacao + " mm");
        System.out.println("Vento: " + vento + " km/h");
    }
}
