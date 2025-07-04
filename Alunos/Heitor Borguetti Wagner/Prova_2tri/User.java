import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Show> favorites = new ArrayList<>();
    private List<Show> watched = new ArrayList<>();
    private List<Show> wishlist = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public List<Show> getFavorites() { return favorites; }
    public List<Show> getWatched() { return watched; }
    public List<Show> getWishlist() { return wishlist; }
}
