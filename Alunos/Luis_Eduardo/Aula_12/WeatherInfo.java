public class WeatherInfo {
    private double temperaturaAtual;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;

    public WeatherInfo(double temperaturaAtual, double temperaturaMaxima, double temperaturaMinima, double umidade,
                       String condicao, double precipitacao, double velocidadeVento, String direcaoVento) {
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    @Override
    public String toString() {
        return "Clima Atual:\n" +
                "- Temperatura atual: " + temperaturaAtual + "°C\n" +
                "- Temperatura máxima: " + temperaturaMaxima + "°C\n" +
                "- Temperatura mínima: " + temperaturaMinima + "°C\n" +
                "- Umidade: " + umidade + "%\n" +
                "- Condição: " + condicao + "\n" +
                "- Precipitação: " + precipitacao + " mm\n" +
                "- Vento: " + velocidadeVento + " km/h " + direcaoVento + "\n";
    }
}