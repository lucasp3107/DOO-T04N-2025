import java.util.List;

public class Serie {
    private String name;
    private String language;
    private List<String> genres;
    private Rating rating;
    private String status;
    private String premiered;
    private String ended;
    private Network network;

    public static class Rating {
        private double average;
        public double getAverage() { return average; }
    }

    public static class Network {
        private String name;
        public String getName() {
            return name;
        }
    }

    public String getName() {
        return name;
    }

    public double getNota() {
        return rating != null ? rating.getAverage() : 0.0;
    }
    public String getStatus() {
        return status;
    }

    public String getDataEstreia() {
        return premiered;
    }

    @Override
    public String toString() {
        String nomeEmissora = (network != null && network.getName() != null) ? network.getName() : "N/A";
        String dataTermino = (ended != null) ? ended : "Em andamento";
        String generosStr = (genres != null && !genres.isEmpty()) ? String.join(", ", genres) : "N/A";

        return String.format(
                "   - Nome: %s (Nota: %.1f)\n" +
                        "     Gêneros: %s\n" +
                        "     Status: %s (Estreia: %s | Término: %s)\n" +
                        "     Idioma: %s | Emissora: %s",
                name, getNota(), generosStr, status, premiered, dataTermino, language, nomeEmissora
        );
    }
}
