import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public Scanner           scanner;
    private ArrayList<Motorista>  motoristas;
    private ArrayList<Passageiro> passageiros;
    private ArrayList<Local>      locais;
    private ArrayList<Viagem>     viagens;
    private ArrayList<Avaliacao>  avaliacoes;

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

    public ArrayList<Local> getlocais() {
        return locais;
    }

    // ══════════════════════════════════════════
    //  MENU PRINCIPAL
    // ══════════════════════════════════════════

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
            menuMotorista(motoristaEncontrado, scanner, locais, viagens);

        } else if (tipo == 2) {
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

    private void fluxoCadastro() {
        System.out.println("\n=== Cadastro de Usuário ===");

        String nome     = Validador.validarNome(scanner);
        String endereco = Validador.validarEndereco(scanner);
        String email    = Validador.validarEmail(scanner);

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

    private void menuMotorista(Motorista motorista, Scanner scanner, ArrayList<Local> locais, ArrayList<Viagem> viagens) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n=== Menu Motorista ===");
            System.out.println("1 - Cadastrar nova viagem");
            System.out.println("2 - Ver passageiros de uma viagem");
            System.out.println("3 - Ver minhas avaliações");
            System.out.println("4 - Concluir uma viagem");
            System.out.println("5 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: motorista.cadastrarViagem(motorista, scanner, locais, viagens); break;
                case 2: motorista.verPassageiros(motorista, scanner);  break;
                case 3: motorista.verAvaliacoes(motorista, scanner);   break;
                case 4: motorista.concluirViagem(motorista, scanner);  break;
                case 5: break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    // ══════════════════════════════════════════
    //  MENU PASSAGEIRO
    // ══════════════════════════════════════════

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
                case 2: passageiro.avaliarViagem(passageiro, scanner, viagens, avaliacoes); break;
                case 3: passageiro.verReservas(passageiro); break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        }
    }
    
}