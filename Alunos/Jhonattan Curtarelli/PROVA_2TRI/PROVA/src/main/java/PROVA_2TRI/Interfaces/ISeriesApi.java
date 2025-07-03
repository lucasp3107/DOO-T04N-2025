package PROVA_2TRI.Interfaces;
import PROVA_2TRI.Entities.Serie;

import java.io.IOException;
import java.util.List;

public interface ISeriesApi {
    public List<Serie> getAll() throws IOException, InterruptedException,Exception;
}
