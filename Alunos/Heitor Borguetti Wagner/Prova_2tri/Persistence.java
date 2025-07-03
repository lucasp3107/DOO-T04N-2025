import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class Persistence {
    private static final String PATH = "data.json";

    public static void saveData(User user) {
        try (Writer writer = new FileWriter(PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(user, writer);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public static User loadData(String name) {
        if (!Files.exists(Path.of(PATH))) return new User(name);
        try (Reader reader = new FileReader(PATH)) {
            Gson gson = new Gson();
            User user = gson.fromJson(reader, User.class);
            return user != null ? user : new User(name);
        } catch (IOException e) {
            System.out.println("Load error: " + e.getMessage());
            return new User(name);
        }
    }
}
