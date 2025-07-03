package com.example;

import java.util.List;
import java.util.ArrayList;

public class Serie {
    private String name;
    private String language;
    private List<String> genres;
    private Rating rating;
    private String status;
    private String premiered;
    private String ended;
    private Network network;

    public Serie(String name, String language, List<String> genres, Rating rating, String status, String premiered, String ended, Network network) {
        this.name = name;
        this.language = language;
        this.genres = genres;
        this.rating = rating;
        this.status = status;
        this.premiered = premiered;
        this.ended = ended;
        this.network = network;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }
    public Rating getRating() { return rating; }
    public void setRating(Rating rating) { this.rating = rating; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPremiered() { return premiered; }
    public void setPremiered(String premiered) { this.premiered = premiered; }
    public String getEnded() { return ended; }
    public void setEnded(String ended) { this.ended = ended; }
    public Network getNetwork() { return network; }
    public void setNetwork(Network network) { this.network = network; }

    @Override
    public String toString() {
        return "\n--- " + name + " ---" +
                "\n  Idioma: " + language +
                "\n  Generos: " + genres +
                "\n  Nota: " + (rating != null ? rating.getAverage() : "N/A") +
                "\n  Status: " + status +
                "\n  Estreia: " + premiered +
                "\n  Termino: " + (ended != null ? ended : "Em andamento") +
                "\n  Emissora: " + (network != null ? network.getName() : "N/A");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return name.equals(serie.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}