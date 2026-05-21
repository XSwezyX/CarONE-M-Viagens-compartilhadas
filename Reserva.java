public class Reserva {

    private Usuario passageiro;
    private Local pontoEmbarque;
    private Local pontoDesembarque;
    private String status; // "confirmada" ou "recusada"
    private Viagem viagem; // ← adiciona

   public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque) {
        this.passageiro       = passageiro;
        this.viagem           = viagem;      // ← adiciona
        this.pontoEmbarque    = pontoEmbarque;
        this.pontoDesembarque = pontoDesembarque;
        this.status           = "confirmada";
    }

    public void recusar() {
        this.status = "recusada";
    }

    public boolean isConfirmada() {
        return status.equals("confirmada");
    }

    public Usuario getPassageiro()       { return passageiro; }
    public Local getPontoEmbarque()      { return pontoEmbarque; }
    public Local getPontoDesembarque()   { return pontoDesembarque; }
    public String getStatus()            { return status; }
    public Viagem getViagem() { return viagem; }

    @Override
    public String toString() {
        return passageiro.getNome()
             + " | Embarque: " + pontoEmbarque.getNome()
             + " → Desembarque: " + pontoDesembarque.getNome()
             + " | " + status;
    }
}