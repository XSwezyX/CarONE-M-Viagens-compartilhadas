import java.util.ArrayList;

public abstract class Usuario {

    // Dados básicos e históricos compartilhados por motorista e passageiro.
    protected String nome;
    protected String email;
    protected String telefone;
    protected String senha;
    protected String endereco;
    protected ArrayList<Avaliacao> avaliacoesRecebidas = new ArrayList<>();
    protected ArrayList<Reserva>   reservas            = new ArrayList<>();

    /**
     * Cria usuário e valida campos essenciais.
     */
    public Usuario(String nome, String email, String telefone, String senha, String endereco) {
        if (nome != null && !nome.isEmpty() && nome.length() >= 3) {
            this.nome = nome;
        }
        if (email != null && email.contains("@") && email.contains(".") && email.length() >= 5) {
            this.email = email;
        }
        if (telefone != null && !telefone.isEmpty() && telefone.length() >= 6) {
            this.telefone = telefone;
        }
        if (senha != null && senha.length() >= 4) {
            this.senha = senha;
        }
        if (endereco != null && !endereco.isEmpty()) {
            this.endereco = endereco;
        }
    }

    /**
     * Indica se o usuário é motorista (sobrescrito em Motorista).
     */
    public boolean ehMotorista() { return false; }

    /**
     * Adiciona avaliação recebida ao histórico do usuário.
     */
    public void receberAvaliacao(Avaliacao a) { avaliacoesRecebidas.add(a); }

    /**
     * Adiciona reserva ao histórico do usuário.
     */
    public void adicionarReserva(Reserva r)   { reservas.add(r); }

    /**
     * Calcula a média das avaliações recebidas.
     */
    public double getMediaAvaliacoes() {
        if (avaliacoesRecebidas.isEmpty()) return 0.0;
        double soma = 0;
        for (Avaliacao a : avaliacoesRecebidas) soma += a.getNota();
        return soma / avaliacoesRecebidas.size();
    }

    /**
     * Verifica se a senha informada coincide com a senha cadastra.
     */
    public boolean verificarSenha(String tentativa) {
        return this.senha.equals(tentativa);
    }

    /**
     * Retorna viagens do usuário; usado por Motorista.
     */
    public ArrayList<Viagem> getViagens() {
        return new ArrayList<>();
    }
    
    public void listarLocais(ArrayList<Local> locais) {
        System.out.println("Locais disponíveis:");
        for (int i = 0; i < locais.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + locais.get(i));
        }
    }

    public String getNome()                              { return nome; }
    public String getEmail()                             { return email; }
    public String getTelefone()                          { return telefone; }
    public String getEndereco()                          { return endereco; }
    public ArrayList<Avaliacao> getAvaliacoesRecebidas() { return avaliacoesRecebidas; }
    public ArrayList<Reserva>   getReservas()            { return reservas; }
}