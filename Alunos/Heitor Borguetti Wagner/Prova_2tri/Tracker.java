import java.util.*;
import java.util.stream.Collectors;

public class Tracker {
    private User user;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        user = Persistence.loadData(name);

        while (true) {
            System.out.println("\n1. Search Show");
            System.out.println("2. View Lists");
            System.out.println("3. Save and Exit");
            int option = Helper.readInt("Choice: ");

            switch (option) {
                case 1 -> findShow();
                case 2 -> displayLists();
                case 3 -> {
                    Persistence.saveData(user);
                    System.out.println("Saved successfully. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void findShow() {
        System.out.print("Enter show name: ");
        String searchName = scanner.nextLine();
        List<Show> results = APIConnection.searchShows(searchName);
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        for (int i = 0; i < results.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, results.get(i).getName());
        }

        int index = Helper.readInt("Choose a show (0 to cancel): ");
        if (index <= 0 || index > results.size()) return;

        Show selected = results.get(index - 1);
        System.out.println("\n" + selected);

        System.out.println("1. Add to Favorites");
        System.out.println("2. Mark as Watched");
        System.out.println("3. Add to Wishlist");
        System.out.println("0. Back");
        int choice = Helper.readInt("Choice: ");
        switch (choice) {
            case 1 -> user.getFavorites().add(selected);
            case 2 -> user.getWatched().add(selected);
            case 3 -> user.getWishlist().add(selected);
        }
    }

    private void displayLists() {
        System.out.println("1. Favorites");
        System.out.println("2. Watched");
        System.out.println("3. Wishlist");
        int op = Helper.readInt("Choice: ");

        List<Show> list = switch (op) {
            case 1 -> user.getFavorites();
            case 2 -> user.getWatched();
            case 3 -> user.getWishlist();
            default -> null;
        };
        if (list == null || list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }

        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by Rating");
        System.out.println("3. Sort by Status");
        System.out.println("4. Sort by Premiere Date");
        int sortOption = Helper.readInt("Choice: ");
        list = Helper.sortList(list, sortOption);

        for (Show s : list) {
            System.out.println("\n" + s);
        }

        System.out.println("\nDo you want to remove a show from the list?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int rem = Helper.readInt("Choice: ");
        if (rem == 1) {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d - %s\n", i + 1, list.get(i).getName());
            }
            int idx = Helper.readInt("Enter the number of the show to remove: ");
            if (idx > 0 && idx <= list.size()) {
                list.remove(idx - 1);
                System.out.println("Successfully removed!");
            }
        }

    }
}
