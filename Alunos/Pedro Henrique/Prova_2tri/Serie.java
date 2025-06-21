package com.minhas_series_tv.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class Serie {
    private int id;
    
    @SerializedName("name")
    private String nome;
    
    @SerializedName("language")
    private String idioma;
    
    private List<String> genres; 

    @SerializedName("rating")
    private Rating rating;
    
    private String status;
    private String premiered;
    private String ended;
    
    @SerializedName("network")
    private Network network;
    
    @SerializedName("webChannel")
    private WebChannel webChannel;

    public Serie() {}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public List<String> getGeneros() {
        return genres;
    }

    public double getNotaGeral() {
        return (rating != null) ? rating.getAverage() : 0.0;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDataEstreia() {
        if (premiered == null || premiered.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(premiered);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao parsear data de estreia '" + premiered + "': " + e.getMessage());
            return null;
        }
    }

    public LocalDate getDataTermino() {
        if (ended == null || ended.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(ended);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao parsear data de término '" + ended + "': " + e.getMessage());
            return null;
        }
    }

    public String getNomeEmissora() {
        if (network != null) {
            return network.getName();
        }
        if (webChannel != null) {
            return webChannel.getName();
        }
        return "N/A";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private static class Rating {
        private double average;
        public double getAverage() {
            return average;
        }
    }

    private static class Network {
        private String name;
        public String getName() {
            return name;
        }
    }

    private static class WebChannel {
        private String name;
        public String getName() {
            return name;
        }
    }
}