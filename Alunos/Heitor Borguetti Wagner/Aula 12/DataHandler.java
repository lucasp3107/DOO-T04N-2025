import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class DataHandler {
    private static final String FILE_PATH = "data.json";

    public static void saveData(UserData user) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(user, writer);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public static UserData loadData(String name) {
        if (!Files.exists(Path.of(FILE_PATH))) return new UserData(name);
        try (Reader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            UserData user = gson.fromJson(reader, UserData.class);
            return user != null ? user : new UserData(name);
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
            return new UserData(name);
        }
    }
}
