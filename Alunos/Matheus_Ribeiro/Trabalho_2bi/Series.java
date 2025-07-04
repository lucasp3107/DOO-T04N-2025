import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Series {
    private int id;
    private String name;
    private String language;
    private String[] genres;
    private Double rating;
    private String status;
    private String premiered;
    private String ended;
    private String network;
    private String summary;

    public boolean favorite = false;
    public boolean watched = false;
    public boolean watchLater = false;

    public Series(int id, String name, String language, String[] genres, Double rating, String status,
                  String premiered, String ended, String network, String summary) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.genres = genres;
        this.rating = rating;
        this.status = status;
        this.premiered = premiered;
        this.ended = ended;
        this.network = network;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String[] getGenres() {
        return genres;
    }

    public Double getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getNetwork() {
        return network;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDate getPremieredDate() {
        try {
            if (premiered == null || premiered.isEmpty()) {
                return LocalDate.MIN;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(this.premiered, formatter);
        } catch (Exception e) {
            return LocalDate.MIN;
        }
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public void setWatchLater(boolean watchLater) {
        this.watchLater = watchLater;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Título: " + name + "\n" +
                "Idioma: " + language + "\n" +
                "Gêneros: " + String.join(", ", genres) + "\n" +
                "Nota: " + (rating != null ? rating : "Sem nota") + "\n" +
                "Status: " + status + "\n" +
                "Data de Estreia: " + (premiered != null ? premiered : "Indefinida") + "\n" +
                "Data de Término: " + (ended != null ? ended : "Indefinida") + "\n" +
                "Emissora: " + (network != null ? network : "Indefinida") + "\n" +
                "Resumo: " + summary + "\n" +
                "Favorita: " + favorite + "\n" +
                "Já Assistida: " + watched + "\n" +
                "Assistir Depois: " + watchLater + "\n" +
                "----------------------------------------";
    }
}
