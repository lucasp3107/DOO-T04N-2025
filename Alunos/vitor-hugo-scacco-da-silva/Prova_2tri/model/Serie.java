package model;

import java.util.List;

public class Serie {
    private int id;
    private String name;
    private String language;
    private List<String> genres;
    private Rating rating;
    private String status;
    private String premiered;
    private String ended;
    private Network network;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLanguage() { return language; }
    public List<String> getGenres() { return genres; }
    public Rating getRating() { return rating; }
    public String getStatus() { return status; }
    public String getPremiered() { return premiered; }
    public String getEnded() { return ended; }
    public Network getNetwork() { return network; }

    public double getRatingAverage() {
        return (rating != null && rating.getAverage() != null) ? rating.getAverage() : 0.0;
    }

    @Override
    public String toString() {
        return "Nome: " + name + " | Idioma: " + language +
               "\nGêneros: " + genres +
               "\nNota: " + getRatingAverage() +
               " | Status: " + status +
               "\nEstreia: " + premiered +
               " | Fim: " + ended +
               "\nEmissora: " + (network != null ? network.getName() : "N/A");
    }
}

class Rating {
    private Double average;
    public Double getAverage() { return average; }
}

class Network {
    private String name;
    public String getName() { return name; }
}