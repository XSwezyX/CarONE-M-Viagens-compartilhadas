/*
 Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 Representa a solicitação (ou confirmação) de carona de um passageiro em uma viagem.

 <p>Uma reserva percorre três estados possíveis ao longo do seu ciclo de vida:</p>
 <ul>
 <li>"pendente" — solicitação enviada, aguardando resposta do motorista;</li>
 <li>"confirmada" — motorista aceitou; o lugar está garantido no veículo;</li>
 <li>"recusada" — motorista recusou; o passageiro pode buscar outra viagem.</li>
 </ul>

*/
public class Reserva {

    /* Passageiro que solicitou a carona. */
    private Usuario passageiro;

    /* Ponto do trajeto onde o passageiro irá embarcar. */
    private Local pontoEmbarque;

    /* Ponto do trajeto onde o passageiro irá desembarcar. */
    private Local pontoDesembarque;

/*
 Estado atual da reserva: "pendente", "confirmada" ou "recusada".

*/
    private String status;

    /* Viagem à qual esta reserva está associada. */
    private Viagem viagem;

    // ─────────────────────────────────────────
    //  CONSTRUTORES
    // ─────────────────────────────────────────

/*
 Cria uma reserva já com status "confirmada".

 <p>Atalho conveniente para a inicialização de dados simulados em Main,
 onde as reservas históricas já são criadas no estado final.</p>


*/
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque) {
        this(passageiro, viagem, pontoEmbarque, pontoDesembarque, "confirmada");
    }

/*
 Cria uma reserva com status explicitamente definido.

 <p>Use "pendente" ao registrar uma nova solicitação de passageiro
 que ainda aguarda aprovação do motorista.</p>


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

/*
 Marca a reserva como "confirmada".
 Chamado quando o motorista aceita a solicitação de carona.

*/
    public void confirmar() {
        this.status = "confirmada";
    }

/*
 Marca a reserva como "recusada".
 Chamado quando o motorista rejeita a solicitação de carona.

*/
    public void recusar() {
        this.status = "recusada";
    }

    // ─────────────────────────────────────────
    //  VERIFICAÇÕES DE ESTADO
    // ─────────────────────────────────────────

    // {@code true} se o motorista aceitou a reserva
    public boolean isConfirmada() { return status.equals("confirmada"); }

    // {@code true} se a reserva ainda aguarda resposta do motorista
    public boolean isPendente()   { return status.equals("pendente");   }

    // {@code true} se o motorista recusou a reserva
    public boolean isRecusada()   { return status.equals("recusada");   }

    // ─────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────

    // passageiro associado a esta reserva
    public Usuario getPassageiro()     { return passageiro; }

    // local de embarque do passageiro
    public Local getPontoEmbarque()    { return pontoEmbarque; }

    // local de desembarque do passageiro
    public Local getPontoDesembarque() { return pontoDesembarque; }

    // status atual da reserva
    public String getStatus()          { return status; }

    // viagem à qual esta reserva pertence
    public Viagem getViagem()          { return viagem; }

    // ─────────────────────────────────────────
    //  EXIBIÇÃO
    // ─────────────────────────────────────────

/*
 Retorna os dados da reserva no formato:
 "NomePassageiro | Embarque: X → Desembarque: Y | status".


*/
    @Override
    public String toString() {
        return passageiro.getNome()
             + " | Embarque: " + pontoEmbarque.getNome()
             + " → Desembarque: " + pontoDesembarque.getNome()
             + " | " + status;
    }
}
