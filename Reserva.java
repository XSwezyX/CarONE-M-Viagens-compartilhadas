public class Reserva {

    private Usuario passageiro;
    private Local pontoEmbarque;
    private Local pontoDesembarque;
    private String status; // "pendente", "confirmada" ou "recusada"
    private Viagem viagem;

    /**
     * Construtor padrão: cria reserva já confirmada.
     * Mantido para compatibilidade com a inicialização de dados simulados em Main.java.
     */
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque) {
        this(passageiro, viagem, pontoEmbarque, pontoDesembarque, "confirmada");
    }

    /**
     * Construtor completo: permite definir o status inicial.
     * Use "pendente" para solicitações que aguardam aprovação do motorista.
     */
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque, String status) {
        this.passageiro       = passageiro;
        this.viagem           = viagem;
        this.pontoEmbarque    = pontoEmbarque;
        this.pontoDesembarque = pontoDesembarque;
        this.status           = status;
    }

    /** Motorista aceita a solicitação. */
    public void confirmar() {
        this.status = "confirmada";
    }

    /** Motorista recusa a solicitação. */
    public void recusar() {
        this.status = "recusada";
    }

    public boolean isConfirmada() { return status.equals("confirmada"); }
    public boolean isPendente()   { return status.equals("pendente");   }
    public boolean isRecusada()   { return status.equals("recusada");   }

    public Usuario getPassageiro()      { return passageiro; }
    public Local getPontoEmbarque()     { return pontoEmbarque; }
    public Local getPontoDesembarque()  { return pontoDesembarque; }
    public String getStatus()           { return status; }
    public Viagem getViagem()           { return viagem; }

    @Override
    public String toString() {
        return passageiro.getNome()
             + " | Embarque: " + pontoEmbarque.getNome()
             + " → Desembarque: " + pontoDesembarque.getNome()
             + " | " + status;
    }
}
