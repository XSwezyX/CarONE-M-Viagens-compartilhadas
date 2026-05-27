/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Representa a solicitação (ou confirmação) de carona de um passageiro em uma viagem.
 *
 * <p>Uma reserva percorre três estados possíveis ao longo do seu ciclo de vida:</p>
 * <ul>
 *   <li>{@code "pendente"} — solicitação enviada, aguardando resposta do motorista;</li>
 *   <li>{@code "confirmada"} — motorista aceitou; o lugar está garantido no veículo;</li>
 *   <li>{@code "recusada"} — motorista recusou; o passageiro pode buscar outra viagem.</li>
 * </ul>
 */
public class Reserva {

    /** Passageiro que solicitou a carona. */
    private Usuario passageiro;

    /** Ponto do trajeto onde o passageiro irá embarcar. */
    private Local pontoEmbarque;

    /** Ponto do trajeto onde o passageiro irá desembarcar. */
    private Local pontoDesembarque;

    /**
     * Estado atual da reserva: {@code "pendente"}, {@code "confirmada"} ou {@code "recusada"}.
     */
    private String status;

    /** Viagem à qual esta reserva está associada. */
    private Viagem viagem;

    // ─────────────────────────────────────────
    //  CONSTRUTORES
    // ─────────────────────────────────────────

    /**
     * Cria uma reserva já com status {@code "confirmada"}.
     *
     * <p>Atalho conveniente para a inicialização de dados simulados em {@code Main},
     * onde as reservas históricas já são criadas no estado final.</p>
     *
     * @param passageiro        usuário que vai embarcar
     * @param viagem            viagem à qual a reserva pertence
     * @param pontoEmbarque     local de embarque no trajeto
     * @param pontoDesembarque  local de desembarque no trajeto
     */
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque) {
        this(passageiro, viagem, pontoEmbarque, pontoDesembarque, "confirmada");
    }

    /**
     * Cria uma reserva com status explicitamente definido.
     *
     * <p>Use {@code "pendente"} ao registrar uma nova solicitação de passageiro
     * que ainda aguarda aprovação do motorista.</p>
     *
     * @param passageiro        usuário que vai embarcar
     * @param viagem            viagem à qual a reserva pertence
     * @param pontoEmbarque     local de embarque no trajeto
     * @param pontoDesembarque  local de desembarque no trajeto
     * @param status            estado inicial: {@code "pendente"}, {@code "confirmada"} ou {@code "recusada"}
     */
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque,
                   Local pontoDesembarque, String status) {
        this.passageiro       = passageiro;
        this.viagem           = viagem;
        this.pontoEmbarque    = pontoEmbarque;
        this.pontoDesembarque = pontoDesembarque;
        this.status           = status;
    }

    // ─────────────────────────────────────────
    //  TRANSIÇÕES DE ESTADO
    // ─────────────────────────────────────────

    /**
     * Marca a reserva como {@code "confirmada"}.
     * Chamado quando o motorista aceita a solicitação de carona.
     */
    public void confirmar() {
        this.status = "confirmada";
    }

    /**
     * Marca a reserva como {@code "recusada"}.
     * Chamado quando o motorista rejeita a solicitação de carona.
     */
    public void recusar() {
        this.status = "recusada";
    }

    // ─────────────────────────────────────────
    //  VERIFICAÇÕES DE ESTADO
    // ─────────────────────────────────────────

    /** @return {@code true} se o motorista aceitou a reserva */
    public boolean isConfirmada() { return status.equals("confirmada"); }

    /** @return {@code true} se a reserva ainda aguarda resposta do motorista */
    public boolean isPendente()   { return status.equals("pendente");   }

    /** @return {@code true} se o motorista recusou a reserva */
    public boolean isRecusada()   { return status.equals("recusada");   }

    // ─────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────

    /** @return passageiro associado a esta reserva */
    public Usuario getPassageiro()     { return passageiro; }

    /** @return local de embarque do passageiro */
    public Local getPontoEmbarque()    { return pontoEmbarque; }

    /** @return local de desembarque do passageiro */
    public Local getPontoDesembarque() { return pontoDesembarque; }

    /** @return status atual da reserva */
    public String getStatus()          { return status; }

    /** @return viagem à qual esta reserva pertence */
    public Viagem getViagem()          { return viagem; }

    // ─────────────────────────────────────────
    //  EXIBIÇÃO
    // ─────────────────────────────────────────

    /**
     * Retorna os dados da reserva no formato:
     * {@code "NomePassageiro | Embarque: X → Desembarque: Y | status"}.
     *
     * @return representação textual da reserva
     */
    @Override
    public String toString() {
        return passageiro.getNome()
             + " | Embarque: " + pontoEmbarque.getNome()
             + " → Desembarque: " + pontoDesembarque.getNome()
             + " | " + status;
    }
}
