package com.rastreadorseries.app;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Serie implements Comparable<Serie> {
    private int id;
    private String name;
    private String language;
    private List<String> genres;
    private Double rating;
    private String status;
    private String premiered;
    private String ended;
    private String networkName;

    public Serie(int id, String name, String language, List<String> genres, Double rating,
                 String status, String premiered, String ended, String networkName) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.genres = new ArrayList<>(genres);
        this.rating = rating;
        this.status = status;
        this.premiered = premiered;
        this.ended = ended;
        this.networkName = networkName;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLanguage() { return language; }
    public List<String> getGenres() { return new ArrayList<>(genres); }
    public Double getRating() { return rating; }
    public String getStatus() { return status; }
    public String getPremiered() { return premiered; }
    public String getEnded() { return ended; }
    public String getNetworkName() { return networkName; }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(name);
        sb.append("\n  Idioma: ").append(language);
        sb.append("\n  Gêneros: ").append(genres.isEmpty() ? "N/A" : String.join(", ", genres));
        sb.append("\n  Nota Geral: ").append(rating != null ? String.format("%.1f", rating) : "N/A");
        sb.append("\n  Estado: ").append(status);
        sb.append("\n  Estreia: ").append(premiered != null ? premiered : "N/A");
        sb.append("\n  Término: ").append(ended != null ? ended : "Em Andamento/N/A");
        sb.append("\n  Emissora: ").append(networkName != null ? networkName : "N/A");
        return sb.toString();
    }

    @Override
    public int compareTo(Serie outra) {
        return this.name.compareToIgnoreCase(outra.name);
    }

    public static Serie fromJson(JsonObject serieJson) {
        int id = serieJson.get("id").getAsInt();
        String name = serieJson.get("name").getAsString();
        String language = serieJson.has("language") && !serieJson.get("language").isJsonNull() ? serieJson.get("language").getAsString() : "N/A";

        List<String> genres = new ArrayList<>();
        if (serieJson.has("genres") && serieJson.get("genres").isJsonArray()) {
            JsonArray genresArray = serieJson.getAsJsonArray("genres");
            genresArray.forEach(element -> genres.add(element.getAsString()));
        }

        Double rating = null;
        if (serieJson.has("rating") && serieJson.getAsJsonObject("rating").has("average") && !serieJson.getAsJsonObject("rating").get("average").isJsonNull()) {
            rating = serieJson.getAsJsonObject("rating").get("average").getAsDouble();
        }

        String status = serieJson.has("status") && !serieJson.get("status").isJsonNull() ? serieJson.get("status").getAsString() : "N/A";
        String premiered = serieJson.has("premiered") && !serieJson.get("premiered").isJsonNull() ? serieJson.get("premiered").getAsString() : null;
        String ended = serieJson.has("ended") && !serieJson.get("ended").isJsonNull() ? serieJson.get("ended").getAsString() : null;

        String networkName = null;
        if (serieJson.has("network") && serieJson.get("network").isJsonObject() && serieJson.getAsJsonObject("network").has("name") && !serieJson.getAsJsonObject("network").get("name").isJsonNull()) {
            networkName = serieJson.getAsJsonObject("network").get("name").getAsString();
        }

        return new Serie(id, name, language, genres, rating, status, premiered, ended, networkName);
    }

    public LocalDate getPremieredDate() {
        if (premiered == null) return null;
        try {
            return LocalDate.parse(premiered);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao parsear data de estreia: " + premiered + " - " + e.getMessage());
            return null;
        }
    }
}