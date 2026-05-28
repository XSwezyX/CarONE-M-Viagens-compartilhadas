import java.util.ArrayList;

/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Representa uma viagem compartilhada conduzida por um motorista.
 *
 * A viagem mantém informações de trajeto, capacidade, status, reservas e avaliações.
 * Ela também controla a aceitação de solicitações e registra quem já avaliou a viagem.
 */
public class Viagem {

    // Motorista responsável pela viagem.
    private Motorista motorista;
    // Trajeto completo da viagem incluindo partida, paradas e destino.
    private ArrayList<Local> trajeto;
    // Capacidade total e vagas atualmente disponíveis.
    private int lugaresTotais;
    private int lugaresDisponiveis;
    // Reservas associadas à viagem, pendentes ou confirmadas.
    private ArrayList<Reserva> reservas;
    // Status da viagem: "agendada" ou "concluida".
    private String status;
    // Controla se a viagem aceita novas solicitações de passageiros.
    private boolean aceitaPassageiros;

    // Rastreia quais usuários já avaliaram o motorista nesta viagem.
    private ArrayList<Usuario> jaAvaliaram = new ArrayList<>();

    // Rastreia quais passageiros já foram avaliados pelo motorista nesta viagem.
    private ArrayList<String> emailsPassageirosAvaliados = new ArrayList<>();

    /**
     * Cria viagem agendada definindo trajeto, número de lugares e se aceita novas solicitações.
     *
     * Por padrão, a viagem inicia no status "agendada" e só muda para "concluida"
     * quando o motorista chama o método concluir().
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

    /**
     * Verifica se a viagem pode atender o passageiro entre origem e destino.
     *
     * A viagem só pode atender se:
     * - houver vagas disponíveis,
     * - existir um ponto do trajeto próximo à origem,
     * - existir um ponto do trajeto próximo ao destino,
     * - o ponto de embarque aparecer antes do ponto de desembarque.
     */
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

    /**
     * Retorna o ponto do trajeto que está mais próximo de um local informado.
     *
     * Este cálculo é usado para indicar o ponto de embarque e desembarque
     * mais conveniente para o passageiro.
     */
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
     *
     * Este método é utilizado principalmente pela inicialização de dados
     * para simular reservas que já estão confirmadas antes do início do sistema.
     */
    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
        lugaresDisponiveis--;
    }

    /**
     * Registra uma solicitação pendente de passageiro.
     *
     * A solicitação fica no status "pendente" até o motorista confirmar ou recusar.
     * O lugar no veículo não é decrementado neste momento.
     */
    public Reserva solicitarReserva(Usuario passageiro, Local embarque, Local desembarque) {
        Reserva reserva = new Reserva(passageiro, this, embarque, desembarque, "pendente");
        reservas.add(reserva);
        passageiro.adicionarReserva(reserva);
        return reserva;
    }

    /**
     * Motorista aceita uma solicitação pendente.
     *
     * A reserva passa para o status "confirmada" e um lugar disponível é decrementado.
     * Retorna false se o veículo já estiver sem vagas.
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
     * Motorista recusa uma solicitação pendente.
     *
     * A reserva mantêm o status apropriado para que o passageiro possa acompanhar
     * o resultado da sua solicitação.
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

    /**
     * Registra avaliação de passageiro para motorista.
     *
     * Adiciona o usuário que já avaliou ao controle local e repassa a avaliação
     * para o perfil do usuário avaliado.
     */
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
     *
     * Este controle é separado para evitar confundir avaliações de motorista
     * com avaliações de passageiro sobre motorista.
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
