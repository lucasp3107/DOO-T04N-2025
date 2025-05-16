package sistemaeventos;

import java.util.Date;

public class Congresso extends Evento {
    public Congresso(String nome, Date dataInicio, Date dataFim, double precoDiario, int maxParticipantes) {
        super(nome, dataInicio, dataFim, precoDiario, maxParticipantes);
    }

    @Override
    public String getTipo() {
        return "Congresso";
    }
}
