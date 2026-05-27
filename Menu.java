import java.util.ArrayList;
import java.util.Scanner;

/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Controlador principal da interface de usuário do sistema CarONE-M.
 *
 * <p>Esta classe gerencia toda a navegação via console, desde o menu inicial até os
 * submenus específicos de motorista e passageiro. Ela recebe as coleções de dados
 * do sistema e as distribui às operações de cada perfil de usuário.</p>
 *
 * <p>Fluxo geral de uso:</p>
 * <ol>
 *   <li>Instanciar {@code Menu} com as listas de dados;</li>
 *   <li>Chamar {@link #iniciar()} para entrar no loop principal.</li>
 * </ol>
 */
public class Menu {

    /** Scanner compartilhado para todas as leituras de entrada no console. */
    private Scanner scanner;

    /** Lista de motoristas cadastrados no sistema. */
    private ArrayList<Motorista>  motoristas;

    /** Lista de passageiros cadastrados no sistema. */
    private ArrayList<Passageiro> passageiros;

    /** Lista de locais disponíveis para composição de trajetos. */
    private ArrayList<Local>      locais;

    /** Lista global de todas as viagens do sistema. */
    private ArrayList<Viagem>     viagens;

    /** Lista global de todas as avaliações registradas. */
    private ArrayList<Avaliacao>  avaliacoes;

    // ─────────────────────────────────────────
    //  CONSTRUTOR
    // ─────────────────────────────────────────

    /**
     * Cria o controlador de menus com as referências às coleções do sistema.
     *
     * @param motoristas  lista de motoristas cadastrados
     * @param passageiros lista de passageiros cadastrados
     * @param locais      lista de locais do sistema
     * @param viagens     lista de viagens do sistema
     * @param avaliacoes  lista de avaliações do sistema
     */
    public Menu(ArrayList<Motorista> motoristas, ArrayList<Passageiro> passageiros,
                ArrayList<Local> locais, ArrayList<Viagem> viagens,
                ArrayList<Avaliacao> avaliacoes) {
        this.motoristas  = motoristas;
        this.passageiros = passageiros;
        this.locais      = locais;
        this.viagens     = viagens;
        this.avaliacoes  = avaliacoes;
        this.scanner     = new Scanner(System.in);
    }

    /** @return lista de locais disponíveis no sistema */
    public ArrayList<Local> getlocais() { return locais; }

    // ══════════════════════════════════════════
    //  MENU PRINCIPAL
    // ══════════════════════════════════════════

    /**
     * Inicia o loop principal do sistema, exibindo o menu de entrada.
     *
     * <p>O loop permanece ativo até que o usuário selecione a opção de sair (3).
     * Ao sair, o {@link Scanner} é encerrado.</p>
     */
    public void iniciar() {
        int opcao = 0;
        while (opcao != 3) {
            System.out.println("\n=== CarONE-M ===");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar novo usuário");
            System.out.println("3 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: fluxoLogin();    break;
                case 2: fluxoCadastro(); break;
                case 3: System.out.println("Sistema encerrado."); break;
                default: System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    // ══════════════════════════════════════════
    //  LOGIN
    // ══════════════════════════════════════════

    /**
     * Conduz o fluxo de autenticação do usuário.
     *
     * <p>Solicita o tipo de conta (motorista ou passageiro), e-mail e senha.
     * Busca o usuário na lista correspondente e, se encontrado com senha correta,
     * redireciona ao menu do perfil. Caso contrário, exibe mensagem de erro.</p>
     */
    private void fluxoLogin() {
        System.out.println("\n=== Login ===");
        System.out.println("Tipo de conta: (1 - Motorista / 2 - Passageiro)");
        System.out.print("Escolha: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (tipo == 1) {
            // Busca motorista com e-mail e senha correspondentes
            Motorista motoristaEncontrado = null;
            for (Motorista m : motoristas) {
                if (m.getEmail().equals(email) && m.verificarSenha(senha)) {
                    motoristaEncontrado = m;
                    break;
                }
            }
            if (motoristaEncontrado == null) {
                System.out.println("Email ou senha inválidos!");
                return;
            }
            System.out.println("Bem-vindo, " + motoristaEncontrado.getNome() + "!");
            menuMotorista(motoristaEncontrado);

        } else if (tipo == 2) {
            // Busca passageiro com e-mail e senha correspondentes
            Passageiro passageiroEncontrado = null;
            for (Passageiro p : passageiros) {
                if (p.getEmail().equals(email) && p.verificarSenha(senha)) {
                    passageiroEncontrado = p;
                    break;
                }
            }
            if (passageiroEncontrado == null) {
                System.out.println("Email ou senha inválidos!");
                return;
            }
            System.out.println("Bem-vindo, " + passageiroEncontrado.getNome() + "!");
            menuPassageiro(passageiroEncontrado);

        } else {
            System.out.println("Tipo inválido!");
        }
    }

    // ══════════════════════════════════════════
    //  CADASTRO
    // ══════════════════════════════════════════

    /**
     * Conduz o fluxo de cadastro de um novo usuário (motorista ou passageiro).
     *
     * <p>Utiliza {@link Validador} para garantir que todos os campos obrigatórios
     * sejam preenchidos corretamente. O e-mail é verificado contra duplicatas antes
     * do cadastro ser concluído. Motoristas também informam o modelo do veículo.</p>
     */
    private void fluxoCadastro() {
        System.out.println("\n=== Cadastro de Usuário ===");

        String nome     = Validador.validarNome(scanner);
        String endereco = Validador.validarEndereco(scanner);
        String email    = Validador.validarEmail(scanner);

        // Impede cadastro de e-mail já existente em qualquer lista
        if (emailJaCadastrado(email)) {
            System.out.println("Este email já está cadastrado!");
            return;
        }

        String telefone = Validador.validarTelefone(scanner);
        String senha    = Validador.validarSenha(scanner);

        System.out.println("Tipo: (1 - Motorista / 2 - Passageiro)");
        System.out.print("Escolha: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            String modelo = Validador.validarModeloVeiculo(scanner);
            motoristas.add(new Motorista(nome, email, telefone, senha, endereco, modelo));
        } else if (tipo == 2) {
            passageiros.add(new Passageiro(nome, email, telefone, senha, endereco));
        } else {
            System.out.println("Tipo inválido!");
            return;
        }

        System.out.println("Usuário cadastrado com sucesso!");
    }

    /**
     * Verifica se um e-mail já está cadastrado em qualquer lista de usuários.
     *
     * <p>Percorre motoristas e passageiros separadamente, pois são listas distintas.</p>
     *
     * @param email e-mail a verificar
     * @return {@code true} se o e-mail já pertence a algum usuário cadastrado
     */
    private boolean emailJaCadastrado(String email) {
        for (Motorista m : motoristas) {
            if (m.getEmail().equals(email)) return true;
        }
        for (Passageiro p : passageiros) {
            if (p.getEmail().equals(email)) return true;
        }
        return false;
    }

    // ══════════════════════════════════════════
    //  MENU MOTORISTA
    // ══════════════════════════════════════════

    /**
     * Exibe e processa o menu de opções do perfil motorista.
     *
     * <p>Permanece em loop até o motorista escolher "Voltar" (7).
     * Cada opção delega a operação ao método correspondente em {@link Motorista}.</p>
     *
     * @param motorista motorista autenticado na sessão atual
     */
    private void menuMotorista(Motorista motorista) {
        int opcao = 0;
        while (opcao != 7) {
            System.out.println("\n=== Menu Motorista ===");
            System.out.println("1 - Cadastrar nova viagem");
            System.out.println("2 - Ver passageiros de uma viagem");
            System.out.println("3 - Responder solicitações de carona");
            System.out.println("4 - Concluir uma viagem");
            System.out.println("5 - Avaliar passageiros de viagem anterior");
            System.out.println("6 - Ver minhas avaliações recebidas");
            System.out.println("7 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: motorista.cadastrarViagem(motorista, scanner, locais, viagens);               break;
                case 2: motorista.verPassageiros(motorista, scanner);                                 break;
                case 3: motorista.responderSolicitacoes(motorista, scanner);                          break;
                case 4: motorista.concluirViagem(motorista, scanner, avaliacoes);                     break;
                case 5: motorista.avaliarViagemMotorista(motorista, scanner, viagens, avaliacoes);    break;
                case 6: motorista.verAvaliacoes(motorista);                                           break;
                case 7: break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    // ══════════════════════════════════════════
    //  MENU PASSAGEIRO
    // ══════════════════════════════════════════

    /**
     * Exibe e processa o menu de opções do perfil passageiro.
     *
     * <p>Permanece em loop até o passageiro escolher "Voltar" (4).
     * Cada opção delega a operação ao método correspondente em {@link Passageiro}.</p>
     *
     * @param passageiro passageiro autenticado na sessão atual
     */
    private void menuPassageiro(Passageiro passageiro) {
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("\n=== Menu Passageiro ===");
            System.out.println("1 - Buscar caronas");
            System.out.println("2 - Avaliar uma viagem feita");
            System.out.println("3 - Ver minhas reservas");
            System.out.println("4 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: passageiro.buscarEPedirCarona(passageiro, scanner, locais, viagens); break;
                case 2: passageiro.avaliarViagem(passageiro, scanner, viagens, avaliacoes);  break;
                case 3: passageiro.verReservas(passageiro);                                  break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        }
    }
}
