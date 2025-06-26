package PROVA_2TRI.Adapters;

import PROVA_2TRI.Interfaces.IPersist;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonPersistAdapter implements IPersist {
    private static final String FILE_PATH = "src/main/java/com/TrabalhoOOP/Data/series.json";

    public JsonPersistAdapter() {
    }

    @Override
    public void save(String json) throws Exception {
        Path basePath = Paths.get("").toAbsolutePath(); // Pasta onde o app está rodando
        Path filePath = basePath.resolve(FILE_PATH);
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(json);
        }
    }

    @Override
    public String load() throws Exception {
        Path basePath = Paths.get("").toAbsolutePath();
        Path filePath = basePath.resolve(FILE_PATH);
        File file = filePath.toFile();

        if (!file.exists()) {
            throw new Exception("Arquivo de dados não encontrado.");
        }

        return new String(Files.readAllBytes(filePath)).trim();
    }

}
