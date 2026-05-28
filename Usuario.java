import java.util.ArrayList;

/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Classe base abstrata que representa os atributos e comportamentos comuns
 * de usuários do sistema: motoristas e passageiros.
 *
 * A classe define dados pessoais, histórico de avaliações recebidas e reservas.
 * Ela não implementa diretamente comportamentos específicos de cada tipo de usuário.
 */
public abstract class Usuario {

    // Dados básicos do usuário:
    protected String nome;
    protected String email;
    protected String telefone;
    protected String senha;
    protected String endereco;

    // Histórico de avaliações recebidas por este usuário.
    protected ArrayList<Avaliacao> avaliacoesRecebidas = new ArrayList<>();

    // Histórico de reservas associadas a este usuário.
    protected ArrayList<Reserva>   reservas            = new ArrayList<>();

    /**
     * Construtor base para qualquer usuário do sistema.
     *
     * As validações aqui são uma primeira proteção contra valores inválidos:
     * - nome mínimo de 3 caracteres,
     * - email com '@' e '.',
     * - telefone com tamanho mínimo,
     * - senha com tamanho mínimo,
     * - endereço preenchido.
     *
     * Note que não é lançado exceção para entradas inválidas; em vez disso,
     * apenas os valores válidos são atribuídos aos campos.
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
     * Indica se o usuário é do tipo motorista.
     *
     * O comportamento padrão é falso, e a subclasse Motorista o sobrescreve.
     */
    public boolean ehMotorista() { return false; }

    /**
     * Adiciona avaliação recebida ao histórico do usuário.
     *
     * Avaliações podem ser recebidas tanto por motoristas quanto por passageiros.
     */
    public void receberAvaliacao(Avaliacao a) { avaliacoesRecebidas.add(a); }

    /**
     * Adiciona reserva ao histórico do usuário.
     */
    public void adicionarReserva(Reserva r)   { reservas.add(r); }

    /**
     * Calcula a média das avaliações recebidas.
     *
     * Se não houver avaliações ainda, o método devolve 0.0 para indicar
     * que não existe média calculável.
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
    /**
     * Retorna a lista de viagens associadas ao usuário.
     *
     * A implementação padrão retorna uma lista vazia porque nem todos
     * os usuários têm viagens diretamente associadas. Motoristas sobrescrevem isso.
     */
    public ArrayList<Viagem> getViagens() {
        return new ArrayList<>();
    }
    
    /**
     * Exibe todos os locais disponíveis na tela com índice numérico.
     *
     * Este método é útil para menus que solicitam seleção de origem/destino.
     */
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