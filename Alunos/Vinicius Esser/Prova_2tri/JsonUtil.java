package br.com.vinicius.prova2tri; 

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static void salvarUsuario(Usuario usuario, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), usuario);
    }

    public static Usuario carregarUsuario(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            return objectMapper.readValue(file, Usuario.class);
        }
        return null;
    }
}