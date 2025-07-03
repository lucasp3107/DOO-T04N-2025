public class Weather {
    private String cidade;
    private double temperaturaAtual;
    private double temperaturaMax;
    private double temperaturaMin;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;

    public Weather(String cidade, double temperaturaAtual, double temperaturaMax, double temperaturaMin,
                   double umidade, String condicao, double precipitacao, double velocidadeVento, String direcaoVento) {
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    @Override
    public String toString() {
        return "\nCidade: " + cidade +
                "\nTemperatura Atual: " + temperaturaAtual + "°C" +
                "\nTemperatura Máxima: " + temperaturaMax + "°C" +
                "\nTemperatura Mínima: " + temperaturaMin + "°C" +
                "\nUmidade: " + umidade + "%" +
                "\nCondição: " + condicao +
                "\nPrecipitação: " + precipitacao + " mm" +
                "\nVelocidade do Vento: " + velocidadeVento + " km/h" +
                "\nDireção do Vento: " + direcaoVento;
    }
}
