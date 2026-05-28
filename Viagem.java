import java.util.ArrayList;

public class Viagem {

    // Representa uma viagem com trajeto, reservas, status e avaliações.
    private Motorista motorista;
    private ArrayList<Local> trajeto;
    private int lugaresTotais;
    private int lugaresDisponiveis;
    private ArrayList<Reserva> reservas;
    private String status;
    private boolean aceitaPassageiros;

    // Rastreia quem já avaliou esta viagem (passageiros → motorista)
    private ArrayList<Usuario> jaAvaliaram = new ArrayList<>();

    // Rastreia quais passageiros já foram avaliados pelo motorista nesta viagem
    private ArrayList<String> emailsPassageirosAvaliados = new ArrayList<>();

    /**
     * Cria viagem agendada definindo trajeto, número de lugares e se aceita novas solicitações.
     */
    public Viagem(Motorista motorista, ArrayList<Local> trajeto, int lugares, boolean aceitaPassageiros) {
        this.motorista          = motorista;
        this.trajeto            = trajeto;
        this.lugaresTotais      = lugares;
        this.lugaresDisponiveis = lugares;
        this.reservas           = new ArrayList<>();
        this.status             = "agendada";
        this.aceitaPassageiros  = aceitaPassageiros;
    }

    // ─────────────────────────────────────────
    //  VERIFICAÇÃO DE ATENDIMENTO
    // ─────────────────────────────────────────

    public boolean podeAtenderPassageiro(Local origem, Local destino) {
        if (lugaresDisponiveis <= 0) return false;

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

    public Local pontoMaisProximo(Local local) {
        Local melhor    = trajeto.get(0);
        double menorDist = trajeto.get(0).distancia(local);

        for (int i = 1; i < trajeto.size(); i++) {
            double dist = trajeto.get(i).distancia(local);
            if (dist < menorDist) {
                menorDist = dist;
                melhor    = trajeto.get(i);
            }
        }

        return melhor;
    }

    // ─────────────────────────────────────────
    //  GESTÃO DE RESERVAS
    // ─────────────────────────────────────────

    /**
     * Adiciona uma reserva já confirmada e ocupa um lugar no veículo.
     * Usado na inicialização de dados simulados em Main.java.
     */
    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
        lugaresDisponiveis--;
    }

    /**
     * Registra uma SOLICITAÇÃO PENDENTE de passageiro.
     * Não ocupa lugar — o lugar só é reservado quando o motorista aceitar.
     * Também adiciona a reserva à lista do passageiro para que ele acompanhe o status.
     */
    public Reserva solicitarReserva(Usuario passageiro, Local embarque, Local desembarque) {
        Reserva reserva = new Reserva(passageiro, this, embarque, desembarque, "pendente");
        reservas.add(reserva);
        passageiro.adicionarReserva(reserva);
        return reserva;
    }

    /**
     * Motorista ACEITA uma solicitação pendente.
     * Confirma a reserva e desconta um lugar disponível.
     * Retorna false se não houver mais vagas.
     */
    public boolean aceitarSolicitacao(Reserva reserva) {
        if (lugaresDisponiveis <= 0) {
            System.out.println("Não é possível aceitar: veículo sem vagas disponíveis.");
            return false;
        }
        reserva.confirmar();
        lugaresDisponiveis--;
        return true;
    }

    /**
     * Motorista RECUSA uma solicitação pendente.
     */
    public void recusarSolicitacao(Reserva reserva) {
        reserva.recusar();
    }

    /** Retorna todas as reservas com status "pendente". */
    public ArrayList<Reserva> getReservasPendentes() {
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.isPendente()) pendentes.add(r);
        }
        return pendentes;
    }

    // ─────────────────────────────────────────
    //  AVALIAÇÕES — passageiro avalia motorista
    // ─────────────────────────────────────────

    public void registrarAvaliacao(Avaliacao avaliacao) {
        jaAvaliaram.add(avaliacao.getAvaliador());
        avaliacao.getAvaliado().receberAvaliacao(avaliacao);
    }

    public boolean usuarioJaAvaliou(Usuario usuario) {
        for (Usuario u : jaAvaliaram) {
            if (u.getEmail().equals(usuario.getEmail())) return true;
        }
        return false;
    }

    // ─────────────────────────────────────────
    //  AVALIAÇÕES — motorista avalia passageiro
    // ─────────────────────────────────────────

    /**
     * Registra avaliação do motorista sobre um passageiro desta viagem.
     * Controle separado para não interferir com as avaliações de passageiros.
     */
    public void registrarAvaliacaoPassageiro(Avaliacao avaliacao) {
        emailsPassageirosAvaliados.add(avaliacao.getAvaliado().getEmail());
        avaliacao.getAvaliado().receberAvaliacao(avaliacao);
    }

    /** Verifica se o motorista já avaliou determinado passageiro nesta viagem. */
    public boolean motoristJaAvaliouPassageiro(Usuario passageiro) {
        return emailsPassageirosAvaliados.contains(passageiro.getEmail());
    }

    // ─────────────────────────────────────────
    //  PARTICIPAÇÃO
    // ─────────────────────────────────────────

    public boolean usuarioParticipou(Usuario usuario) {
        if (motorista.getEmail().equals(usuario.getEmail())) return true;
        for (Reserva r : reservas) {
            if (r.getPassageiro().getEmail().equals(usuario.getEmail()) && r.isConfirmada()) {
                return true;
            }
        }
        return false;
    }

    // ─────────────────────────────────────────
    //  OUTROS
    // ─────────────────────────────────────────

    public void concluir() { this.status = "concluida"; }

    public Local              getPartida()            { return trajeto.get(0); }
    public Local              getDestino()            { return trajeto.get(trajeto.size() - 1); }
    public Motorista          getMotorista()          { return motorista; }
    public ArrayList<Local>   getTrajeto()            { return trajeto; }
    public int                getLugaresDisponiveis() { return lugaresDisponiveis; }
    public int                getLugaresTotais()      { return lugaresTotais; }
    public ArrayList<Reserva> getReservas()           { return reservas; }
    public String             getStatus()             { return status; }
    public boolean            isAceitaPassageiros()   { return aceitaPassageiros; }

    @Override
    public String toString() {
        return "De " + getPartida().getNome() + " para " + getDestino().getNome()
             + " | Motorista: " + motorista.getNome()
             + " | Lugares: " + lugaresDisponiveis + "/" + lugaresTotais
             + " | " + status;
    }
}
