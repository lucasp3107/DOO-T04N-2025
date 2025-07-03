import java.util.*;

public class Utils {
    private static Scanner sc = new Scanner(System.in);

    public static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        }
    }

    public static List<Serie> ordenarLista(List<Serie> lista, int tipo) {
        Comparator<Serie> comp = switch (tipo) {
            case 1 -> Comparator.comparing(Serie::getNome);
            case 2 -> Comparator.comparingDouble(Serie::getNota).reversed();
            case 3 -> Comparator.comparing(Serie::getStatus);
            case 4 -> Comparator.comparing(Serie::getDataEstreia);
            default -> null;
        };
        if (comp != null) lista = new ArrayList<>(lista.stream().sorted(comp).toList());
        return lista;
    }
}
