package br.com.vinicius.clima;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class Clima {
    private String cidade; 
    private Double temperaturaAtual; 
    private Double temperaturaMaxima; 
    private Double temperaturaMinima; 
    private Double umidade; 
    private String condicaoTempo; 
    private Double precipitacao; 
    private Double velocidadeVento; 
    private String direcaoVento; 


    public Clima() {}

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public Double getTemperaturaAtual() { return temperaturaAtual; }
    public void setTemperaturaAtual(Double temperaturaAtual) { this.temperaturaAtual = temperaturaAtual; }

    public Double getTemperaturaMaxima() { return temperaturaMaxima; }
    public void setTemperaturaMaxima(Double temperaturaMaxima) { this.temperaturaMaxima = temperaturaMaxima; }

    public Double getTemperaturaMinima() { return temperaturaMinima; }
    public void setTemperaturaMinima(Double temperaturaMinima) { this.temperaturaMinima = temperaturaMinima; }

    public Double getUmidade() { return umidade; }
    public void setUmidade(Double umidade) { this.umidade = umidade; }

    public String getCondicaoTempo() { return condicaoTempo; }
    public void setCondicaoTempo(String condicaoTempo) { this.condicaoTempo = condicaoTempo; }

    public Double getPrecipitacao() { return precipitacao; }
    public void setPrecipitacao(Double precipitacao) { this.precipitacao = precipitacao; }

    public Double getVelocidadeVento() { return velocidadeVento; }
    public void setVelocidadeVento(Double velocidadeVento) { this.velocidadeVento = velocidadeVento; }

    public String getDirecaoVento() { return direcaoVento; }
    public void setDirecaoVento(String direcaoVento) { this.direcaoVento = direcaoVento; } 

    @Override
    public String toString() {
        String tempAtualStr = (temperaturaAtual != null) ? String.format("%.1f°C", temperaturaAtual) : "N/A";
        String tempMaxStr = (temperaturaMaxima != null) ? String.format("%.1f°C", temperaturaMaxima) : "N/A";
        String tempMinStr = (temperaturaMinima != null) ? String.format("%.1f°C", temperaturaMinima) : "N/A";
        String umidadeStr = (umidade != null) ? String.format("%.1f%%", umidade) : "N/A";
        String precipitacaoStr = (precipitacao != null) ? String.format("%.1f mm", precipitacao) : "0.0 mm";
        String velVentoStr = (velocidadeVento != null) ? String.format("%.1f km/h", velocidadeVento) : "N/A";
        String dirVentoStr = (direcaoVento != null && !direcaoVento.isEmpty()) ? direcaoVento : "N/A";

        return "--- Clima para " + (cidade != null ? cidade : "Cidade Desconhecida") + " ---" +
               "\nTemperatura Atual: " + tempAtualStr +
               "\nMáxima do Dia: " + tempMaxStr +
               "\nMínima do Dia: " + tempMinStr +
               "\nUmidade do Ar: " + umidadeStr +
               "\nCondição do Tempo: " + (condicaoTempo != null && !condicaoTempo.isEmpty() ? condicaoTempo : "N/A") +
               "\nPrecipitação Hoje: " + precipitacaoStr +
               "\nVelocidade do Vento: " + velVentoStr +
               "\nDireção do Vento: " + dirVentoStr;
    }
}