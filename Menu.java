import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private Sistema sistema;

    public Menu(Sistema sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
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
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = sistema.fazerLogin(email, senha);

        if (usuario == null) {
            System.out.println("Email ou senha inválidos!");
            return;
        }

        System.out.println("Bem-vindo, " + usuario.getNome() + "!");
        menuPerfil(usuario);
    }

    // ══════════════════════════════════════════
    //  CADASTRO
    // ══════════════════════════════════════════

    private void fluxoCadastro() {
        System.out.println("\n=== Cadastro de Usuário ===");

        String nome     = Validador.validarNome(scanner);
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        String email    = Validador.validarEmail(scanner);

        if (sistema.emailJaCadastrado(email)) {
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
            System.out.print("Modelo do veículo: ");
            String modelo = scanner.nextLine();
            System.out.print("Placa do veículo: ");
            String placa = scanner.nextLine();
            sistema.cadastrarUsuario(
                new Motorista(nome, email, telefone, senha, endereco, modelo)
            );
        } else if (tipo == 2) {
            sistema.cadastrarUsuario(
                new Passageiro(nome, email, telefone, senha, endereco)
            );
        } else {
            System.out.println("Tipo inválido!");
            return;
        }

        System.out.println("Usuário cadastrado com sucesso!");
    }

    // ══════════════════════════════════════════
    //  ESCOLHA DE PERFIL
    // ══════════════════════════════════════════

    private void menuPerfil(Usuario usuario) {
        int opcao = 0;
        while (opcao != 3) {
            System.out.println("\n=== Perfil ===");
            System.out.println("Como deseja interagir?");
            if (usuario.ehMotorista()) {
                System.out.println("1 - Motorista");
            } else {
                System.out.println("1 - (você não é motorista)");
            }
            System.out.println("2 - Passageiro");
            System.out.println("3 - Sair da conta");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    if (usuario.ehMotorista()) {
                        menuMotorista((Motorista) usuario);
                    } else {
                        System.out.println("Você não é motorista!");
                    }
                    break;
                case 2: menuPassageiro(usuario); break;
                case 3: System.out.println("Saindo da conta..."); break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    // ══════════════════════════════════════════
    //  MENU MOTORISTA
    // ══════════════════════════════════════════

    private void menuMotorista(Motorista motorista) {
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("\n=== Menu Motorista ===");
            System.out.println("1 - Cadastrar nova viagem");
            System.out.println("2 - Ver passageiros de uma viagem");
            System.out.println("3 - Ver minhas avaliações");
            System.out.println("4 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrarViagem(motorista); break;
                case 2: verPassageiros(motorista);  break;
                case 3: verAvaliacoes(motorista);   break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarViagem(Motorista motorista) {
        System.out.println("\n=== Cadastrar Viagem ===");

        ArrayList<Local> locais = sistema.getLocais();
        listarLocais(locais);

        System.out.print("Selecione o local de PARTIDA: ");
        int idxPartida = scanner.nextInt() - 1;
        scanner.nextLine();

        System.out.print("Selecione o local de DESTINO: ");
        int idxDestino = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idxPartida == idxDestino) {
            System.out.println("Partida e destino não podem ser iguais!");
            return;
        }

        ArrayList<Local> trajeto = new ArrayList<>();
        trajeto.add(locais.get(idxPartida));

        System.out.print("Deseja adicionar paradas? (1-Sim / 2-Não): ");
        int addParada = scanner.nextInt();
        scanner.nextLine();

        if (addParada == 1) {
            boolean adicionando = true;
            while (adicionando) {
                listarLocais(locais);
                System.out.print("Selecione a parada: ");
                int idx = scanner.nextInt() - 1;
                scanner.nextLine();
                trajeto.add(locais.get(idx));
                System.out.println("Parada adicionada: " + locais.get(idx).getNome());
                System.out.print("Adicionar mais uma? (1-Sim / 2-Não): ");
                int mais = scanner.nextInt();
                scanner.nextLine();
                adicionando = (mais == 1);
            }
        }

        trajeto.add(locais.get(idxDestino));

        System.out.print("Número de lugares disponíveis: ");
        int lugares = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Aceitar passageiros? (1-Sim / 2-Não): ");
        int aceitar = scanner.nextInt();
        scanner.nextLine();
        boolean aceita = (aceitar == 1);

        Viagem viagem = new Viagem(motorista, trajeto, lugares, aceita);
        sistema.cadastrarViagem(viagem, motorista);

        System.out.println("Viagem cadastrada com sucesso!");
        System.out.println(viagem);
    }

    private void verPassageiros(Motorista motorista) {
        System.out.println("\n=== Passageiros das Minhas Viagens ===");

        ArrayList<Viagem> agendadas = new ArrayList<>();
        for (Viagem v : motorista.getViagens()) {
            if (v.getStatus().equals("agendada")) agendadas.add(v);
        }

        if (agendadas.isEmpty()) {
            System.out.println("Você não possui viagens agendadas.");
            return;
        }

        for (int i = 0; i < agendadas.size(); i++) {
            System.out.println((i + 1) + " - " + agendadas.get(i));
        }

        System.out.print("Selecione a viagem: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        Viagem viagem = agendadas.get(idx);

        ArrayList<Reserva> reservas = viagem.getReservas();

        if (reservas.isEmpty()) {
            System.out.println("Nenhum passageiro nesta viagem ainda.");
        } else {
            System.out.println("\nPassageiros:");
            for (Reserva r : reservas) {
                System.out.println("  • " + r);
            }
        }
    }

    private void verAvaliacoes(Motorista motorista) {
        System.out.println("\n=== Minhas Avaliações ===");

        ArrayList<Avaliacao> avaliacoes = motorista.getAvaliacoesRecebidas();

        if (avaliacoes.isEmpty()) {
            System.out.println("Você ainda não recebeu avaliações.");
            return;
        }

        System.out.printf("Média geral: %.1f/5 (%d avaliação(ões))%n",
                motorista.getMediaAvaliacoes(), avaliacoes.size());

        System.out.println("\nComentários:");
        for (Avaliacao a : avaliacoes) {
            System.out.println("  • " + a);
        }
    }

    // ══════════════════════════════════════════
    //  MENU PASSAGEIRO
    // ══════════════════════════════════════════

    private void menuPassageiro(Usuario usuario) {
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
                case 1: buscarEPedirCarona(usuario); break;
                case 2: avaliarViagem(usuario);      break;
                case 3: verReservas(usuario);        break;
                case 4: break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarEPedirCarona(Usuario usuario) {
        System.out.println("\n=== Buscar Carona ===");

        ArrayList<Local> locais = sistema.getLocais();
        listarLocais(locais);

        System.out.print("Selecione sua ORIGEM: ");
        int idxOrigem = scanner.nextInt() - 1;
        scanner.nextLine();

        System.out.print("Selecione seu DESTINO: ");
        int idxDestino = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idxOrigem == idxDestino) {
            System.out.println("Origem e destino não podem ser iguais!");
            return;
        }

        Local origem  = locais.get(idxOrigem);
        Local destino = locais.get(idxDestino);

        ArrayList<Viagem> encontradas = sistema.buscarCaronas(origem, destino);

        if (encontradas.isEmpty()) {
            System.out.println("Nenhuma viagem disponível para este trajeto.");
            return;
        }

        System.out.println("\n" + encontradas.size() + " viagem(ns) encontrada(s):");
        for (int i = 0; i < encontradas.size(); i++) {
            System.out.println((i + 1) + " - " + encontradas.get(i));
        }

        System.out.print("\nDeseja solicitar uma carona? (1-Sim / 2-Não): ");
        int solicitar = scanner.nextInt();
        scanner.nextLine();
        if (solicitar == 2) return;

        System.out.print("Selecione a viagem: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        Viagem viagem = encontradas.get(idx);

        Local embarque    = viagem.pontoMaisProximo(origem);
        Local desembarque = viagem.pontoMaisProximo(destino);

        System.out.println("\nPonto de embarque:    " + embarque);
        System.out.println("Ponto de desembarque: " + desembarque);
        System.out.println("Motorista: " + viagem.getMotorista().getNome());

        System.out.print("\nConfirmar solicitação? (1-Sim / 2-Não): ");
        int confirmar = scanner.nextInt();
        scanner.nextLine();
        if (confirmar == 2) return;

        Reserva reserva = sistema.confirmarCarona(usuario, viagem, embarque, desembarque);

        if (reserva.isConfirmada()) {
            System.out.println("\nCarona confirmada! Boa viagem.");
        } else {
            System.out.println("\nMotorista recusou a carona. Tente outra viagem.");
        }
    }

    private void avaliarViagem(Usuario usuario) {
        System.out.println("\n=== Avaliar Viagem ===");

        ArrayList<Viagem> paraAvaliar = new ArrayList<>();
        for (Reserva r : usuario.getReservas()) {
            Viagem v = r.getViagem();
            if (r.isConfirmada()
                    && v.getStatus().equals("concluida")
                    && !v.usuarioJaAvaliou(usuario)) {
                paraAvaliar.add(v);
            }
        }

        if (paraAvaliar.isEmpty()) {
            System.out.println("Não há viagens disponíveis para avaliar.");
            return;
        }

        System.out.println("Viagens que você pode avaliar:");
        for (int i = 0; i < paraAvaliar.size(); i++) {
            System.out.println((i + 1) + " - " + paraAvaliar.get(i));
        }

        System.out.print("Selecione a viagem: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        Viagem viagem = paraAvaliar.get(idx);

        System.out.print("Nota (1 a 5): ");
        int nota = scanner.nextInt();
        scanner.nextLine();
        while (nota < 1 || nota > 5) {
            System.out.println("Nota inválida! Digite entre 1 e 5.");
            nota = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Comentário (opcional, Enter para pular): ");
        String comentario = scanner.nextLine();

        Avaliacao avaliacao = new Avaliacao(usuario, viagem.getMotorista(), nota, comentario);
        sistema.registrarAvaliacao(avaliacao);
        viagem.registrarAvaliacao(usuario);

        System.out.println("Avaliação registrada: " + avaliacao);
    }

    private void verReservas(Usuario usuario) {
        System.out.println("\n=== Minhas Reservas ===");

        ArrayList<Reserva> reservas = usuario.getReservas();

        if (reservas.isEmpty()) {
            System.out.println("Você não possui reservas.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println("  • " + r);
        }
    }

    // ══════════════════════════════════════════
    //  UTILITÁRIOS
    // ══════════════════════════════════════════

    private void listarLocais(ArrayList<Local> locais) {
        System.out.println("Locais disponíveis:");
        for (int i = 0; i < locais.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + locais.get(i));
        }
    }
}