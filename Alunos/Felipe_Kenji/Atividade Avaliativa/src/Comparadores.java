import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

public class Comparadores {

    public static final Comparator<Dados.Serie> BY_NAME = Comparator.comparing(Dados.Serie::getName, Comparator.nullsLast(String::compareToIgnoreCase));

    public static final Comparator<Dados.Serie> BY_RATING = Comparator.comparing(
        serie -> (serie.getRating() != null && serie.getRating().getAverage() != null) ? serie.getRating().getAverage() : -1.0,
        Comparator.reverseOrder()
    );

    public static final Comparator<Dados.Serie> BY_STATUS = Comparator.comparing(
        Dados.Serie::getStatus,
        Comparator.nullsLast(
            (status1, status2) -> {
                int score1 = getStatusScore(status1);
                int score2 = getStatusScore(status2);
                return Integer.compare(score2, score1);
            }
        )
    );

    private static int getStatusScore(String status) {
        if (status == null) return 0;
        return switch (status.toLowerCase()) {
            case "running" -> 3;
            case "ended" -> 2;
            case "canceled" -> 1;
            default -> 0;
        };
    }

    public static final Comparator<Dados.Serie> BY_PREMIERED_DATE = Comparator.comparing(
        serie -> {
            if (serie.getPremiered() == null || serie.getPremiered().isEmpty()) {
                return LocalDate.MIN;
            }
            try {
                return LocalDate.parse(serie.getPremiered());
            } catch (DateTimeParseException e) {
                return LocalDate.MIN;
            }
        },
        Comparator.nullsLast(Comparator.naturalOrder())
    );
}