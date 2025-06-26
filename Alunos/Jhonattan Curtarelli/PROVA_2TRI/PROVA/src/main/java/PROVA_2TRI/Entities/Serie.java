package PROVA_2TRI.Entities;

import java.time.LocalDate;
import java.util.List;

public class Serie {
    public int id;
    public boolean watched = false;
    public String name;
    public String type;
    public List<String> genres;
    public double rate;
    public String state;
    public LocalDate premiereDate;
    public LocalDate endDate;
    public String broadCasterNote;
    public int getId() {
        return id;
    }

    public Serie(int id, String name, String type, List<String> genres, double rate, String state, LocalDate premiereDate, LocalDate endDate, String broadCasterNote) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.genres = genres;
        this.rate = rate;
        this.state = state;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
        this.broadCasterNote = broadCasterNote;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", watched=" + watched +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", genres=" + genres +
                ", rate=" + rate +
                ", state='" + state + '\'' +
                ", premiereDate=" + premiereDate +
                ", endDate=" + endDate +
                ", broadCasterNote='" + broadCasterNote + '\'' +
                '}';

    }
}
