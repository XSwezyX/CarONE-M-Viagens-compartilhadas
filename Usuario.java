import java.util.ArrayList;

public abstract class Usuario {

    protected String nome;
    protected String email;
    protected String telefone;
    protected String senha;
    protected String endereco;
    protected ArrayList<Avaliacao> avaliacoesRecebidas = new ArrayList<>();
    protected ArrayList<Reserva>   reservas            = new ArrayList<>();

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

    public boolean ehMotorista() { return false; }

    public void receberAvaliacao(Avaliacao a) { avaliacoesRecebidas.add(a); }
    public void adicionarReserva(Reserva r)   { reservas.add(r); }

    public double getMediaAvaliacoes() {
        if (avaliacoesRecebidas.isEmpty()) return 0.0;
        double soma = 0;
        for (Avaliacao a : avaliacoesRecebidas) soma += a.getNota();
        return soma / avaliacoesRecebidas.size();
    }

    public boolean verificarSenha(String tentativa) {
        return this.senha.equals(tentativa);
    }

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