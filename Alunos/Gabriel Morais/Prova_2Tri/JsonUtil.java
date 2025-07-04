import java.io.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
    private static final String FILE = "usuario.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salvar(Usuario u) {
        try (FileWriter fw = new FileWriter(FILE)) {
            gson.toJson(u, fw);
        } catch (IOException e) {
            System.out.println("Erro ao salvar.");
        }
    }

    public static Usuario carregar() {
        try (Reader r = new FileReader(FILE)) {
            return gson.fromJson(r, Usuario.class);
        } catch (Exception e) {
            return null;
        }
    }
}