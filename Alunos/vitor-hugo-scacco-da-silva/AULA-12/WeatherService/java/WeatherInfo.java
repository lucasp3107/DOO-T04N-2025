public class WeatherInfo {
    private final double tempAtual;
    private final double tempMax;
    private final double tempMin;
    private final double umidade;
    private final String condicao;
    private final double precipitacao;
    private final double ventoVel;
    private final String ventoDir;

    public WeatherInfo(double tempAtual, double tempMax, double tempMin, double umidade, String condicao, double precipitacao, double ventoVel, String ventoDir) {
        this.tempAtual = tempAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.ventoVel = ventoVel;
        this.ventoDir = ventoDir;
    }

    public double getTempAtual() {
        return tempAtual;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getUmidade() {
        return umidade;
    }

    public String getCondicao() {
        return condicao;
    }

    public double getPrecipitacao() {
        return precipitacao;
    }

    public double getVentoVel() {
        return ventoVel;
    }

    public String getVentoDir() {
        return ventoDir;
    }
}
