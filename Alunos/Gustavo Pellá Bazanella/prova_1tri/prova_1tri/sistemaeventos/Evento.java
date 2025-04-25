package sistemaeventos;
import java.util.*;

public abstract class Evento {
    public String nome;
    public Date dataInicio;
    public Date dataFim;
    public double precoDiario;
    public int maxParticipantes;
    public List<Cliente> participantes = new ArrayList<>();

    public Evento(String nome, Date dataInicio, Date dataFim, double precoDiario, int maxParticipantes) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.precoDiario = precoDiario;
        this.maxParticipantes = maxParticipantes;
    }

    public long getDuracaoDias() {
        return (dataFim.getTime() - dataInicio.getTime()) / (1000 * 60 * 60 * 24) + 1;
    }

    public boolean verificarDisponibilidade() {
        return participantes.size() < maxParticipantes;
    }

    public boolean adicionarParticipante(Cliente cliente) {
        if (verificarDisponibilidade()) {
            participantes.add(cliente);
            return true;
        }
        return false;
    }

    public double calcularValorIngresso() {
        return precoDiario * getDuracaoDias();
    }

    public abstract String getTipo();
}
