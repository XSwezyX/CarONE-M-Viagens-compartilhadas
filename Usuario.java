import java.util.ArrayList;

/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Classe base abstrata que representa qualquer usuário cadastrado no sistema CarONE-M.
 *
 * <p>Define os atributos e comportamentos comuns a motoristas e passageiros,
 * como dados pessoais, autenticação por senha, avaliações recebidas e reservas
 * associadas ao perfil. Subclasses concretas ({@link Motorista} e {@link Passageiro})
 * especializam o comportamento de acordo com o papel do usuário na plataforma.</p>
 */
public abstract class Usuario {

    /** Nome completo do usuário (mínimo de 3 caracteres). */
    protected String nome;

    /** Endereço de e-mail, usado como identificador único de login. */
    protected String email;

    /** Número de telefone de contato (mínimo de 6 dígitos). */
    protected String telefone;

    /** Senha de acesso ao sistema (mínimo de 4 caracteres). */
    protected String senha;

    /** Endereço residencial do usuário. */
    protected String endereco;

    /** Lista de avaliações recebidas de outros usuários após viagens compartilhadas. */
    protected ArrayList<Avaliacao> avaliacoesRecebidas = new ArrayList<>();

    /** Lista de reservas (solicitações de carona) vinculadas a este usuário. */
    protected ArrayList<Reserva> reservas = new ArrayList<>();

    // ─────────────────────────────────────────
    //  CONSTRUTOR
    // ─────────────────────────────────────────

    /**
     * Cria um novo usuário com dados pessoais validados.
     *
     * <p>Cada campo é atribuído somente se passar pelas regras de validação
     * correspondentes. Campos que não atendam aos critérios são simplesmente
     * ignorados (ficam {@code null}); a responsabilidade de garantir dados
     * corretos antes da chamada fica com {@link Validador}.</p>
     *
     * <p>Regras aplicadas:</p>
     * <ul>
     *   <li>Nome: não nulo, não vazio, mínimo de 3 caracteres;</li>
     *   <li>E-mail: contém '@' e '.', mínimo de 5 caracteres;</li>
     *   <li>Telefone: não vazio, mínimo de 6 caracteres;</li>
     *   <li>Senha: mínimo de 4 caracteres;</li>
     *   <li>Endereço: não nulo e não vazio.</li>
     * </ul>
     *
     * @param nome      nome completo do usuário
     * @param email     e-mail de acesso
     * @param telefone  número de telefone
     * @param senha     senha de autenticação
     * @param endereco  endereço residencial
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

    // ─────────────────────────────────────────
    //  IDENTIFICAÇÃO DE PERFIL
    // ─────────────────────────────────────────

    /**
     * Indica se este usuário é um motorista.
     *
     * <p>A implementação padrão retorna {@code false}. A subclasse {@link Motorista}
     * sobrescreve este método para retornar {@code true}, permitindo verificar
     * o papel do usuário sem uso de {@code instanceof}.</p>
     *
     * @return {@code false} para todo usuário que não seja motorista
     */
    public boolean ehMotorista() { return false; }

    // ─────────────────────────────────────────
    //  AVALIAÇÕES E RESERVAS
    // ─────────────────────────────────────────

    /**
     * Registra uma avaliação recebida por este usuário.
     *
     * @param a avaliação a ser adicionada ao histórico
     */
    public void receberAvaliacao(Avaliacao a) { avaliacoesRecebidas.add(a); }

    /**
     * Vincula uma reserva ao perfil deste usuário.
     *
     * <p>Usado tanto para registrar reservas de passageiros quanto para
     * manter o histórico visível ao usuário em "Ver minhas reservas".</p>
     *
     * @param r reserva a ser adicionada
     */
    public void adicionarReserva(Reserva r) { reservas.add(r); }

    /**
     * Calcula a média aritmética de todas as avaliações recebidas.
     *
     * @return média das notas recebidas, ou {@code 0.0} se não houver nenhuma
     */
    public double getMediaAvaliacoes() {
        if (avaliacoesRecebidas.isEmpty()) return 0.0;
        double soma = 0;
        for (Avaliacao a : avaliacoesRecebidas) soma += a.getNota();
        return soma / avaliacoesRecebidas.size();
    }

    /**
     * Verifica se a senha fornecida corresponde à senha cadastrada.
     *
     * @param tentativa string digitada pelo usuário no momento do login
     * @return {@code true} se a senha estiver correta
     */
    public boolean verificarSenha(String tentativa) {
        return this.senha.equals(tentativa);
    }

    // ─────────────────────────────────────────
    //  VIAGENS
    // ─────────────────────────────────────────

    /**
     * Retorna as viagens associadas a este usuário.
     *
     * <p>A implementação padrão retorna uma lista vazia. Subclasses que possuem
     * viagens próprias (como {@link Motorista}) devem sobrescrever este método.</p>
     *
     * @return lista de viagens do usuário
     */
    public ArrayList<Viagem> getViagens() {
        return new ArrayList<>();
    }

    // ─────────────────────────────────────────
    //  UTILITÁRIO — EXIBIÇÃO DE LOCAIS
    // ─────────────────────────────────────────

    /**
     * Exibe no console a lista numerada de locais disponíveis para seleção.
     *
     * <p>Utilizado antes de solicitar que o usuário escolha origem, destino ou
     * paradas intermediárias em operações de cadastro e busca de viagens.</p>
     *
     * @param locais lista de pontos geográficos cadastrados no sistema
     */
    public void listarLocais(ArrayList<Local> locais) {
        System.out.println("Locais disponíveis:");
        for (int i = 0; i < locais.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + locais.get(i));
        }
    }

    // ─────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────

    /** @return nome completo do usuário */
    public String getNome()                              { return nome; }

    /** @return e-mail do usuário */
    public String getEmail()                             { return email; }

    /** @return telefone do usuário */
    public String getTelefone()                          { return telefone; }

    /** @return endereço residencial do usuário */
    public String getEndereco()                          { return endereco; }

    /** @return lista de avaliações recebidas */
    public ArrayList<Avaliacao> getAvaliacoesRecebidas() { return avaliacoesRecebidas; }

    /** @return lista de reservas vinculadas ao usuário */
    public ArrayList<Reserva>   getReservas()            { return reservas; }
}
