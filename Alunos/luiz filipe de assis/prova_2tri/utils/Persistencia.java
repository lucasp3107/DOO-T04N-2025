package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Serie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Serie> carregarLista(String nomeArquivo) {
        try {
            File file = new File(nomeArquivo);
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<Serie>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void salvarLista(List<Serie> lista, String nomeArquivo) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), lista);
        } catch (Exception e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}