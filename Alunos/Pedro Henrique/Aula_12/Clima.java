package com.previsao_do_tempo.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class Clima {
    private double latitude;
    private double longitude;
    private String resolvedAddress; // Endereço resolvido (nome da cidade/local)
    private String address; // Endereço original da consulta

    private CurrentConditions currentConditions; // Condições atuais
    private List<DailyConditions> days; // Previsão diária

    // Construtor vazio para Gson
    public Clima() {}

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public String getAddress() {
        return address;
    }

    public double getTemperaturaAtual() {
        return (currentConditions != null) ? currentConditions.getTemp() : 0.0;
    }

    public double getTemperaturaMaximaDia() {
        return (days != null && !days.isEmpty()) ? days.get(0).getTempmax() : 0.0;
    }

    public double getTemperaturaMinimaDia() {
        return (days != null && !days.isEmpty()) ? days.get(0).getTempmin() : 0.0;
    }

    public double getUmidadeAr() {
        return (currentConditions != null) ? currentConditions.getHumidity() : 0.0;
    }

    public String getCondicaoTempo() {
        String conditions = (currentConditions != null) ? currentConditions.getConditions() : "N/A";
        // A API pode retornar várias condições separadas por vírgula. Simplificar ou traduzir.
        // Ex: "Partially cloudy, Rain" -> "Chuva e Parcialmente Nublado"
        return conditions;
    }

    public double getPrecipitacao() {
        return (currentConditions != null) ? currentConditions.getPrecip() : 0.0;
    }

    public double getVelocidadeVento() {
        return (currentConditions != null) ? currentConditions.getWindspeed() : 0.0;
    }

    public double getDirecaoVentoGraus() {
        return (currentConditions != null) ? currentConditions.getWinddir() : 0.0;
    }
    
    // Método para converter graus em uma direção cardeal aproximada
    public String getDirecaoVentoTexto() {
        if (currentConditions == null) return "N/A";
        double dir = currentConditions.getWinddir();
        if ((dir >= 337.5 && dir <= 360) || (dir >= 0 && dir < 22.5)) return "Norte (N)";
        if (dir >= 22.5 && dir < 67.5) return "Nordeste (NE)";
        if (dir >= 67.5 && dir < 112.5) return "Leste (L)";
        if (dir >= 112.5 && dir < 157.5) return "Sudeste (SE)";
        if (dir >= 157.5 && dir < 202.5) return "Sul (S)";
        if (dir >= 202.5 && dir < 247.5) return "Sudoeste (SO)";
        if (dir >= 247.5 && dir < 292.5) return "Oeste (O)";
        if (dir >= 292.5 && dir < 337.5) return "Noroeste (NO)";
        return "N/A";
    }

    // --- Classes internas para mapear a estrutura do JSON da API ---
    private static class CurrentConditions {
        private double temp;
        private double humidity;
        private double precip;
        private double windspeed;
        private double winddir; // Direção do vento em graus
        private String conditions;

        public double getTemp() { return temp; }
        public double getHumidity() { return humidity; }
        public double getPrecip() { return precip; }
        public double getWindspeed() { return windspeed; }
        public double getWinddir() { return winddir; }
        public String getConditions() { return conditions; }
    }

    private static class DailyConditions {
        private String datetime; // Data do dia em YYYY-MM-DD
        private double tempmax;
        private double tempmin;
        
        public String getDatetime() { return datetime; }
        public double getTempmax() { return tempmax; }
        public double getTempmin() { return tempmin; }
    }
}