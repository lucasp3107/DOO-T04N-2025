package com.seriestracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; 
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersistenceService {
   
    private static final ObjectMapper mapper = new ObjectMapper();

    
    static {
        
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Salva 
     * @param list 
     * @param filename 
     */
    public static <T> void saveList(List<T> list, String filename) {
        try {
            
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(filename), list);
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Carrega 
     * @param filename
     * @param 
     * @return 
     */
    public static <T> List<T> loadList(String filename, Class<T> clazz) {
        File file = new File(filename);
        if (!file.exists()) {
            return new java.util.ArrayList<>(); 
        }
        
        try {
            
            CollectionType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, clazz);
            return mapper.readValue(file, type);
        } catch (IOException e) {
            System.err.println("Erro ao carregar " + filename + ", um novo arquivo será criado: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
}
