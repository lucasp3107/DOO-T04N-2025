import java.util.*;

public class Helper {
    private static Scanner sc = new Scanner(System.in);

    public static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    public static List<Show> sortList(List<Show> list, int type) {
        Comparator<Show> comp = switch (type) {
            case 1 -> Comparator.comparing(Show::getName);
            case 2 -> Comparator.comparingDouble(Show::getRating).reversed();
            case 3 -> Comparator.comparing(Show::getStatus);
            case 4 -> Comparator.comparing(Show::getPremiereDate);
            default -> null;
        };
        if (comp != null) list = new ArrayList<>(list.stream().sorted(comp).toList());
        return list;
    }
}
