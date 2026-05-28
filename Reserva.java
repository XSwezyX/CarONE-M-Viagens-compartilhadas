/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Representa a reserva de um passageiro em uma viagem.
 *
 * Cada reserva possui pontos de embarque e desembarque, estado de aprovação
 * e referência à viagem associada.
 */
public class Reserva {

    // Passageiro que fez a solicitação.
    private Usuario passageiro;
    // Ponto de embarque e desembarque escolhidos para a reserva.
    private Local pontoEmbarque;
    private Local pontoDesembarque;
    // Situação atual da reserva: pendente, confirmada ou recusada.
    private String status;
    private Viagem viagem;

    /**
     * Construtor padrão: cria reserva já confirmada.
     *
     * Usado para representar reservas já estabelecidas no cenário de inicialização.
     */
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque) {
        this(passageiro, viagem, pontoEmbarque, pontoDesembarque, "confirmada");
    }

    /**
     * Construtor completo: permite definir o status inicial.
     *
     * Este construtor é usado para criar reservas pendentes que aguardam a
     * aprovação do motorista antes de se tornarem confirmadas.
     */
    public Reserva(Usuario passageiro, Viagem viagem, Local pontoEmbarque, Local pontoDesembarque, String status) {
        this.passageiro       = passageiro;
        this.viagem           = viagem;
        this.pontoEmbarque    = pontoEmbarque;
        this.pontoDesembarque = pontoDesembarque;
        this.status           = status;
    }

    /**
     * Marca a reserva como confirmada.
     */
    public void confirmar() {
        this.status = "confirmada";
    }

    /**
     * Marca a reserva como recusada.
     */
    public void recusar() {
        this.status = "recusada";
    }

    // Consultas de status da reserva.
    public boolean isConfirmada() { return status.equals("confirmada"); 
    }
    public boolean isPendente()   { return status.equals("pendente");   }
    public boolean isRecusada()   { return status.equals("recusada");   }

    // Getters retornam os dados desta reserva.
    public Usuario getPassageiro()      { return passageiro; }
    public Local getPontoEmbarque()     { return pontoEmbarque; }
    public Local getPontoDesembarque()  { return pontoDesembarque; }
    public String getStatus()           { return status; }
    public Viagem getViagem()           { return viagem; }

    /**
     * Retorna resumo da reserva para exibição no console.
     */
    @Override
    public String toString() {
        return passageiro.getNome()
             + " | Embarque: " + pontoEmbarque.getNome()
             + " → Desembarque: " + pontoDesembarque.getNome()
             + " | " + status;
    }
}
