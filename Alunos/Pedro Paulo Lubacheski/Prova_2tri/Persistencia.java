
import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class Persistencia {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void salvar(String arquivo, Object obj) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(arquivo), obj);
    }

    public static List<Serie> carregar(String arquivo) throws Exception {
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Serie.class);
        return mapper.readValue(new File(arquivo), listType);
    }
}
