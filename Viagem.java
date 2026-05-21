import java.util.ArrayList;

public class Viagem {

    private Motorista motorista;
    private ArrayList<Local> trajeto; // posição 0 = partida, última = destino
    private int lugaresTotais;
    private int lugaresDisponiveis;
    private ArrayList<Reserva> reservas;
    private String status; // "agendada" ou "concluida"
    private boolean aceitaPassageiros;
    private ArrayList<Usuario> jaAvaliaram = new ArrayList<>();
    

    public Viagem(Motorista motorista, ArrayList<Local> trajeto, int lugares, boolean aceitaPassageiros) {
        this.motorista          = motorista;
        this.trajeto            = trajeto;
        this.lugaresTotais      = lugares;
        this.lugaresDisponiveis = lugares;
        this.reservas           = new ArrayList<>();
        this.status             = "agendada";
        this.aceitaPassageiros  = aceitaPassageiros;
    }
    public boolean isAceitaPassageiros() { return aceitaPassageiros; }

    public boolean usuarioJaAvaliou(Usuario usuario) {
        for (Usuario u : jaAvaliaram) {
            if (u.getEmail().equals(usuario.getEmail())) return true;
        }
        return false;
    }

public void registrarAvaliacao(Usuario usuario) {
    jaAvaliaram.add(usuario);
}   

    // Verifica se a viagem pode atender o passageiro (tolerância de 2.0)
    public boolean podeAtenderPassageiro(Local origem, Local destino) {
        if (lugaresDisponiveis == 0) return false;

        int idxEmbarque    = -1;
        int idxDesembarque = -1;

        for (int i = 0; i < trajeto.size(); i++) {
            Local ponto = trajeto.get(i);
            if (idxEmbarque == -1 && ponto.distancia(origem) <= 2.0) {
                idxEmbarque = i;
            }
            if (ponto.distancia(destino) <= 2.0) {
                idxDesembarque = i;
            }
        }

        return idxEmbarque != -1 && idxDesembarque != -1 && idxEmbarque < idxDesembarque;
    }

    // Retorna o ponto do trajeto mais próximo de um local
    public Local pontoMaisProximo(Local local) {
    Local melhor = trajeto.get(0);
    double menorDist = trajeto.get(0).distancia(local);

    for (int i = 1; i < trajeto.size(); i++) {
        double dist = trajeto.get(i).distancia(local);
        if (dist < menorDist) {
            menorDist = dist;
            melhor = trajeto.get(i);
        }
    }

    return melhor;
}

    // Adiciona reserva confirmada e ocupa um lugar
    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
        lugaresDisponiveis--;
    }

    // Verifica se um usuário participou desta viagem
    public boolean usuarioParticipou(Usuario usuario) {
        if (motorista.getEmail().equals(usuario.getEmail())) return true;
        for (Reserva r : reservas) {
            if (r.getPassageiro().getEmail().equals(usuario.getEmail()) && r.isConfirmada()) {
                return true;
            }
        }
        return false;
    }

    public void concluir() { this.status = "concluida"; }

    public Local getPartida()              { return trajeto.get(0); }
    public Local getDestino()              { return trajeto.get(trajeto.size() - 1); }
    public Motorista getMotorista()        { return motorista; }
    public ArrayList<Local> getTrajeto()   { return trajeto; }
    public int getLugaresDisponiveis()     { return lugaresDisponiveis; }
    public int getLugaresTotais()          { return lugaresTotais; }
    public ArrayList<Reserva> getReservas(){ return reservas; }
    public String getStatus()              { return status; }

    @Override
    public String toString() {
        return "De " + getPartida().getNome() + " para " + getDestino().getNome()
             + " | Motorista: " + motorista.getNome()
             + " | Lugares: " + lugaresDisponiveis + "/" + lugaresTotais
             + " | " + status;
    }
}