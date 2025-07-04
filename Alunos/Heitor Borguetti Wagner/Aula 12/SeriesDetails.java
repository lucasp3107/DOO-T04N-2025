import java.util.List;

public class SeriesDetails {
    private int id;
    private String name;
    private String language;
    private List<String> genres;
    private double rating;
    private String status;
    private String premiereDate;
    private String endDate;
    private String network;

    public Series() {}

    public Series(int id, String name, String language, List<String> genres, double rating, String status,
                 String premiereDate, String endDate, String network) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.genres = genres;
        this.rating = rating;
        this.status = status;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
        this.network = network;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLanguage() { return language; }
    public List<String> getGenres() { return genres; }
    public double getRating() { return rating; }
    public String getStatus() { return status; }
    public String getPremiereDate() { return premiereDate; }
    public String getEndDate() { return endDate; }
    public String getNetwork() { return network; }

    @Override
    public String toString() {
        return String.format("Name: %s\nLanguage: %s\nGenres: %s\nRating: %.1f\nStatus: %s\nPremiere: %s\nEnd: %s\nNetwork: %s",
            name, language, genres, rating, status, premiereDate, endDate, network);
    }
}
