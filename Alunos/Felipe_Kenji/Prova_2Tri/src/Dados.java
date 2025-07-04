import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Dados {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Serie implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private String name;
        private String language;
        private String[] genres;
        private Rating rating;
        private String status;
        private String premiered;
        private String ended;
        private Network network;

        public Serie() {}

        public Serie(int id, String name, String language, String[] genres, Rating rating, String status,
                     String premiered, String ended, Network network) {
            this.id = id;
            this.name = name;
            this.language = language;
            this.genres = genres;
            this.rating = rating;
            this.status = status;
            this.premiered = premiered;
            this.ended = ended;
            this.network = network;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getLanguage() { return language; }
        public String[] getGenres() { return genres; }
        public Rating getRating() { return rating; }
        public String getStatus() { return status; }
        public String getPremiered() { return premiered; }
        public String getEnded() { return ended; }
        public Network getNetwork() { return network; }

        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setLanguage(String language) { this.language = language; }
        public void setGenres(String[] genres) { this.genres = genres; }
        public void setRating(Rating rating) { this.rating = rating; }
        public void setStatus(String status) { this.status = status; }
        public void setPremiered(String premiered) { this.premiered = premiered; }
        public void setEnded(String ended) { this.ended = ended; }
        public void setNetwork(Network network) { this.network = network; }

        @Override
        public String toString() {
            String genresStr = (genres != null && genres.length > 0) ? String.join(", ", genres) : "N/A";
            String score = (rating != null && rating.getAverage() != null) ? String.format("%.1f", rating.getAverage()) : "N/A";
            String networkName = (network != null && network.getName() != null) ? network.getName() : "Desconhecida";
            String endDate = (ended != null && !ended.isEmpty()) ? ended : "Ainda em exibição";

            return String.format(
                "\n--- Detalhes da Série ---\n" +
                "ID: %d\n" +
                "Nome: %s\n" +
                "Idioma: %s\n" +
                "Gêneros: %s\n" +
                "Nota Geral: %s\n" +
                "Estado: %s\n" +
                "Estreia: %s\n" +
                "Término: %s\n" +
                "Emissora: %s\n" +
                "-------------------------\n",
                id, name, language, genresStr, score, status, premiered, endDate, networkName
            );
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

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Rating implements Serializable {
            private static final long serialVersionUID = 1L;
            private Double average;

            public Rating() {}
            public Double getAverage() { return average; }
            public void setAverage(Double average) { this.average = average; }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Network implements Serializable {
            private static final long serialVersionUID = 1L;
            private String name;

            public Network() {}
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
        }
    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private String nickname;
        private List<Serie> favorites;
        private List<Serie> watched;
        private List<Serie> watchlist;

        public User() {
            this.favorites = new ArrayList<>();
            this.watched = new ArrayList<>();
            this.watchlist = new ArrayList<>();
        }

        public User(String nickname) {
            this();
            this.nickname = nickname;
        }

        @JsonCreator
        public User(@JsonProperty("nickname") String nickname,
                    @JsonProperty("favorites") List<Serie> favorites,
                    @JsonProperty("watched") List<Serie> watched,
                    @JsonProperty("watchlist") List<Serie> watchlist) {
            this.nickname = nickname;
            this.favorites = favorites != null ? favorites : new ArrayList<>();
            this.watched = watched != null ? watched : new ArrayList<>();
            this.watchlist = watchlist != null ? watchlist : new ArrayList<>();
        }

        public String getNickname() { return nickname; }
        public List<Serie> getFavorites() { return favorites; }
        public List<Serie> getWatched() { return watched; }
        public List<Serie> getWatchlist() { return watchlist; }

        public void setNickname(String nickname) { this.nickname = nickname; }
        public void setFavorites(List<Serie> favorites) { this.favorites = favorites; }
        public void setWatched(List<Serie> watched) { this.watched = watched; }
        public void setWatchlist(List<Serie> watchlist) { this.watchlist = watchlist; }

        public boolean addSerieToList(Dados.Serie serie, List<Dados.Serie> list, String listName) {
            if (serie == null) {
                System.out.println("Série inválida.");
                return false;
            }
            boolean exists = list.contains(serie);
            if (exists) {
                System.out.println("A série '" + serie.getName() + "' já está na sua lista de " + listName + ".");
                return false;
            }
            list.add(serie);
            System.out.println("Série '" + serie.getName() + "' adicionada à sua lista de " + listName + ".");
            return true;
        }

        public boolean removeSerieFromList(int serieId, List<Dados.Serie> list, String listName) {
            Optional<Dados.Serie> serieToRemove = list.stream()
                .filter(s -> s.getId() == serieId)
                .findFirst();

            if (serieToRemove.isPresent()) {
                list.remove(serieToRemove.get());
                System.out.println("Série '" + serieToRemove.get().getName() + "' removida da sua lista de " + listName + ".");
                return true;
            } else {
                System.out.println("Série com ID " + serieId + " não encontrada na sua lista de " + listName + ".");
                return false;
            }
        }
    }
}