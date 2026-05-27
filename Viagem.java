import java.util.ArrayList;

/*
 Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 Representa uma viagem cadastrada por um motorista no sistema CarONE-M.

 <p>Uma viagem é definida por um motorista, um trajeto (sequência ordenada de locais),
 e uma capacidade de passageiros. Ela pode estar nos estados "agendada" ou
 "concluida". Viagens agendadas que aceitam passageiros aparecem nas buscas de
 carona; viagens concluídas ficam disponíveis para avaliação.</p>

 <p>O controle de vagas é feito de forma separada para solicitações pendentes e
 confirmadas: uma vaga só é descontada quando o motorista aceita a reserva.</p>

*/
public class Viagem {

    /* Motorista responsável por conduzir a viagem. */
    private Motorista motorista;

    /* Sequência ordenada de locais que compõem o trajeto, do ponto de partida ao destino. */
    private ArrayList<Local> trajeto;

    /* Número total de lugares para passageiros (não inclui o motorista). */
    private int lugaresTotais;

    /* Número de lugares ainda disponíveis para novos passageiros. */
    private int lugaresDisponiveis;

    /* Lista de todas as reservas associadas a esta viagem (pendentes, confirmadas e recusadas). */
    private ArrayList<Reserva> reservas;

    /* Estado atual da viagem: {@code "agendada"} ou {@code "concluida"}. */
    private String status;

    /* Indica se o motorista deseja aceitar passageiros nesta viagem. */
    private boolean aceitaPassageiros;

/*
 Registro dos usuários que já avaliaram o motorista nesta viagem,
 impedindo avaliações duplicadas pelo mesmo passageiro.

*/
    private ArrayList<Usuario> jaAvaliaram = new ArrayList<>();

/*
 Registro dos e-mails dos passageiros que já foram avaliados pelo motorista
 nesta viagem, controlado separadamente das avaliações de passageiros.

*/
    private ArrayList<String> emailsPassageirosAvaliados = new ArrayList<>();

    // ─────────────────────────────────────────
    //  CONSTRUTOR
    // ─────────────────────────────────────────

/*
 Cria uma nova viagem no estado "agendada".


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

/*
 Verifica se esta viagem pode atender um passageiro com os pontos de origem e destino informados.

 <p>Para que a viagem seja considerada compatível, três condições devem ser atendidas:</p>
 <ol>
 <li>Haver pelo menos um lugar disponível no veículo;</li>
 <li>Existir um ponto do trajeto a no máximo 2 km da origem do passageiro;</li>
 <li>Existir um ponto do trajeto a no máximo 2 km do destino do passageiro,
 localizado após o ponto de embarque na sequência do trajeto.</li>
 </ol>


*/
    public boolean podeAtenderPassageiro(Local origem, Local destino) {
        if (lugaresDisponiveis <= 0) return false;

        int idxEmbarque    = -1;
        int idxDesembarque = -1;

        for (int i = 0; i < trajeto.size(); i++) {
            Local ponto = trajeto.get(i);

            // Primeiro ponto do trajeto dentro de 2 km da origem → ponto de embarque
            if (idxEmbarque == -1 && ponto.distancia(origem) <= 2.0) {
                idxEmbarque = i;
            }

            // Ponto do trajeto dentro de 2 km do destino → candidato ao desembarque
            if (ponto.distancia(destino) <= 2.0) {
                idxDesembarque = i;
            }
        }

        // Ambos os pontos devem existir e o embarque deve preceder o desembarque
        return idxEmbarque != -1 && idxDesembarque != -1 && idxEmbarque < idxDesembarque;
    }

/*
 Retorna o ponto do trajeto mais próximo de um local informado.

 <p>Utilizado para sugerir automaticamente os pontos de embarque e desembarque
 mais adequados ao passageiro com base em sua origem e destino desejados.</p>


*/
    public Local pontoMaisProximo(Local local) {
        Local melhor     = trajeto.get(0);
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

/*
 Adiciona uma reserva já confirmada e desconta um lugar do veículo.

 <p>Usado exclusivamente na inicialização de dados simulados em Main,
 onde reservas históricas são inseridas diretamente no estado confirmado.</p>


*/
    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
        lugaresDisponiveis--;
    }

/*
 Registra uma nova solicitação de carona com status "pendente".

 <p>O lugar não é descontado neste momento. O desconto ocorre somente quando
 o motorista aceitar a reserva via #aceitarSolicitacao(Reserva).
 A reserva também é vinculada ao passageiro para que ele possa acompanhar
 o andamento em "Ver minhas reservas".</p>


*/
    public Reserva solicitarReserva(Usuario passageiro, Local embarque, Local desembarque) {
        Reserva reserva = new Reserva(passageiro, this, embarque, desembarque, "pendente");
        reservas.add(reserva);
        passageiro.adicionarReserva(reserva);
        return reserva;
    }

/*
 Confirma uma reserva pendente, descontando um lugar no veículo.

 <p>Retorna false e exibe aviso se não houver mais vagas disponíveis
 (situação possível se duas solicitações simultâneas disputarem a última vaga).</p>


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

/*
 Recusa uma reserva pendente, sem alterar a contagem de vagas.


*/
    public void recusarSolicitacao(Reserva reserva) {
        reserva.recusar();
    }

/*
 Retorna todas as reservas desta viagem com status "pendente".


*/
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

/*
 Registra a avaliação feita por um passageiro sobre o motorista desta viagem.

 <p>Adiciona o avaliador à lista de controle para impedir avaliações duplicadas
 e repassa a avaliação ao perfil do usuário avaliado.</p>


*/
    public void registrarAvaliacao(Avaliacao avaliacao) {
        jaAvaliaram.add(avaliacao.getAvaliador());
        avaliacao.getAvaliado().receberAvaliacao(avaliacao);
    }

/*
 Verifica se o usuário informado já avaliou o motorista desta viagem.


*/
    public boolean usuarioJaAvaliou(Usuario usuario) {
        for (Usuario u : jaAvaliaram) {
            if (u.getEmail().equals(usuario.getEmail())) return true;
        }
        return false;
    }

    // ─────────────────────────────────────────
    //  AVALIAÇÕES — motorista avalia passageiro
    // ─────────────────────────────────────────

/*
 Registra a avaliação feita pelo motorista sobre um passageiro desta viagem.

 <p>O controle é feito por e-mail em lista separada para não interferir
 com as avaliações que passageiros fazem sobre o motorista.</p>


*/
    public void registrarAvaliacaoPassageiro(Avaliacao avaliacao) {
        emailsPassageirosAvaliados.add(avaliacao.getAvaliado().getEmail());
        avaliacao.getAvaliado().receberAvaliacao(avaliacao);
    }

/*
 Verifica se o motorista já avaliou determinado passageiro nesta viagem.


*/
    public boolean motoristaJaAvaliouPassageiro(Usuario passageiro) {
        return emailsPassageirosAvaliados.contains(passageiro.getEmail());
    }

    // ─────────────────────────────────────────
    //  PARTICIPAÇÃO
    // ─────────────────────────────────────────

/*
 Verifica se um usuário participou desta viagem, seja como motorista ou como
 passageiro com reserva confirmada.

 <p>Este método é usado para restringir a funcionalidade de avaliação apenas
 a quem realmente esteve na viagem.</p>


*/
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
    //  CICLO DE VIDA
    // ─────────────────────────────────────────

/*
 Marca a viagem como "concluida", liberando-a para avaliações.

 <p>Após a conclusão, a viagem não aparece mais nas buscas de carona,
 mas fica disponível no histórico de avaliações do passageiro e do motorista.</p>

*/
    public void concluir() { this.status = "concluida"; }

    // ─────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────

    // primeiro local do trajeto (ponto de partida)
    public Local getPartida()              { return trajeto.get(0); }

    // último local do trajeto (destino final)
    public Local getDestino()              { return trajeto.get(trajeto.size() - 1); }

    // motorista responsável pela viagem
    public Motorista getMotorista()        { return motorista; }

    // sequência completa de locais do trajeto
    public ArrayList<Local> getTrajeto()   { return trajeto; }

    // quantidade de lugares ainda disponíveis para passageiros
    public int getLugaresDisponiveis()     { return lugaresDisponiveis; }

    // capacidade total de passageiros do veículo
    public int getLugaresTotais()          { return lugaresTotais; }

    // todas as reservas da viagem (qualquer status)
    public ArrayList<Reserva> getReservas() { return reservas; }

    // estado atual da viagem: {@code "agendada"} ou {@code "concluida"}
    public String getStatus()              { return status; }

    // {@code true} se o motorista aceita novos passageiros
    public boolean isAceitaPassageiros()   { return aceitaPassageiros; }

    // ─────────────────────────────────────────
    //  EXIBIÇÃO
    // ─────────────────────────────────────────

/*
 Retorna um resumo da viagem no formato:
 "De X para Y | Motorista: Nome | Lugares: disp/total | status".


*/
    @Override
    public String toString() {
        return "De " + getPartida().getNome() + " para " + getDestino().getNome()
             + " | Motorista: " + motorista.getNome()
             + " | Lugares: " + lugaresDisponiveis + "/" + lugaresTotais
             + " | " + status;
    }
}
