package sistemaeventos;

import java.util.*;

public class Show extends Evento {
    public List<Cliente> areaVIP = new ArrayList<>();
    private final double precoVIPMultiplicador = 1.5;

    public Show(String nome, Date dataInicio, Date dataFim, double precoDiario, int maxParticipantes) {
        super(nome, dataInicio, dataFim, precoDiario, maxParticipantes);
    }

    public boolean adicionarVIP(Cliente cliente) {
        int limiteVIP = (int) (maxParticipantes * 0.1);
        if (areaVIP.size() < limiteVIP) {
            areaVIP.add(cliente);
            participantes.add(cliente);
            return true;
        }
        return false;
    }

    public double calcularValorIngressoVIP() {
        return calcularValorIngresso() * precoVIPMultiplicador;
    }

    @Override
    public String getTipo() {
        return "Show";
    }
}
