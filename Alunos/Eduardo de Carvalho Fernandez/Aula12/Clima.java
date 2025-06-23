public class Clima {

    private String cidade;
    private String data;
    private double tempAtual;
    private double tempMax;
    private double tempMin;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double vento;
    private String direcaoVento;

    public Clima(String cidade, String data, double tempAtual, double tempMax, double tempMin,
                 double umidade, String condicao, double precipitacao, double vento, String direcaoVento) {
        this.cidade = cidade;
        this.data = data;
        this.tempAtual = tempAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.vento = vento;
        this.direcaoVento = direcaoVento;
    }

    @Override
    public String toString() {
        return String.format("Clima em %s (%s):\nTemp atual: %.1f°C\nMáx: %.1f°C, Mín: %.1f°C\n" +
                             "Umidade: %.1f%%\nCondição: %s\nPrecipitação: %.1f mm\nVento: %.1f km/h (%s)",
                             cidade, data, tempAtual, tempMax, tempMin,
                             umidade, condicao, precipitacao, vento, direcaoVento);
    }
}
