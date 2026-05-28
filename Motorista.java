import java.util.ArrayList;
import java.util.Scanner;

public class Motorista extends Usuario {

    // Motorista pode cadastrar viagens, responder solicitações e avaliar passageiros.
    private String modeloVeiculo;
    private ArrayList<Viagem> viagens = new ArrayList<>();

    /**
     * Cria motorista com dados pessoais e modelo do veículo.
     */
    public Motorista(String nome, String email, String telefone, String senha,
                     String endereco, String modeloVeiculo) {
        super(nome, email, telefone, senha, endereco);
        this.modeloVeiculo = modeloVeiculo;
    }

    // ─────────────────────────────────────────
    //  CADASTRAR VIAGEM
    // ─────────────────────────────────────────

    public void cadastrarViagem(Motorista motorista, Scanner scanner,
                                ArrayList<Local> locais, ArrayList<Viagem> viagens) {
        System.out.println("\n=== Cadastrar Viagem ===");
        motorista.listarLocais(locais);

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
        boolean aceita = (scanner.nextInt() == 1);
        scanner.nextLine();

        Viagem viagem = new Viagem(motorista, trajeto, lugares, aceita);
        viagens.add(viagem);
        motorista.adicionarViagem(viagem);

        System.out.println("Viagem cadastrada com sucesso!");
        System.out.println(viagem);
    }

    // ─────────────────────────────────────────
    //  VER PASSAGEIROS
    // ─────────────────────────────────────────

    public void verPassageiros(Motorista motorista, Scanner scanner) {
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

        ArrayList<Reserva> confirmadas = new ArrayList<>();
        for (Reserva r : viagem.getReservas()) {
            if (r.isConfirmada()) confirmadas.add(r);
        }

        if (confirmadas.isEmpty()) {
            System.out.println("Nenhum passageiro confirmado nesta viagem ainda.");
        } else {
            System.out.println("\nPassageiros confirmados:");
            for (Reserva r : confirmadas) {
                System.out.println("  • " + r);
            }
        }
    }

    // ─────────────────────────────────────────
    //  RESPONDER SOLICITAÇÕES PENDENTES
    // ─────────────────────────────────────────

    /**
     * Exibe todas as solicitações de carona pendentes nas viagens agendadas
     * do motorista e permite aceitar ou recusar cada uma individualmente.
     */
    public void responderSolicitacoes(Motorista motorista, Scanner scanner) {
        System.out.println("\n=== Solicitações de Carona Pendentes ===");

        // Coleta todas as reservas pendentes das viagens agendadas do motorista
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Viagem v : motorista.getViagens()) {
            if (v.getStatus().equals("agendada")) {
                pendentes.addAll(v.getReservasPendentes());
            }
        }

        if (pendentes.isEmpty()) {
            System.out.println("Não há solicitações pendentes no momento.");
            return;
        }

        System.out.println(pendentes.size() + " solicitação(ões) aguardando sua resposta:\n");

        for (int i = 0; i < pendentes.size(); i++) {
            Reserva r = pendentes.get(i);
            System.out.println("── Solicitação " + (i + 1) + " ──");
            System.out.println("  Passageiro : " + r.getPassageiro().getNome());
            System.out.println("  Viagem     : " + r.getViagem());
            System.out.println("  Embarque   : " + r.getPontoEmbarque().getNome());
            System.out.println("  Desembarque: " + r.getPontoDesembarque().getNome());
            System.out.print("  Aceitar? (1-Sim / 2-Não): ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha == 1) {
                boolean aceito = r.getViagem().aceitarSolicitacao(r);
                if (aceito) {
                    System.out.println("  ✓ Carona aceita! " + r.getPassageiro().getNome() + " está confirmado(a).");
                }
            } else {
                r.getViagem().recusarSolicitacao(r);
                System.out.println("  ✗ Solicitação recusada. " + r.getPassageiro().getNome() + " será notificado(a).");
            }
        }
    }

    // ─────────────────────────────────────────
    //  VER AVALIAÇÕES RECEBIDAS
    // ─────────────────────────────────────────

    public void verAvaliacoes(Motorista motorista, Scanner scanner) {
        System.out.println("\n=== Minhas Avaliações ===");

        ArrayList<Avaliacao> avs = motorista.getAvaliacoesRecebidas();
        if (avs.isEmpty()) {
            System.out.println("Você ainda não recebeu avaliações.");
            return;
        }

        System.out.printf("Média geral: %.1f/5 (%d avaliação(ões))%n",
                motorista.getMediaAvaliacoes(), avs.size());
        System.out.println("\nComentários:");
        for (Avaliacao a : avs) {
            System.out.println("  • " + a);
        }
    }

    // ─────────────────────────────────────────
    //  CONCLUIR VIAGEM (+ avaliação imediata)
    // ─────────────────────────────────────────

    /**
     * Conclui a viagem selecionada e, logo em seguida, oferece ao motorista
     * a oportunidade de avaliar cada passageiro confirmado ali mesmo.
     */
    public void concluirViagem(Motorista motorista, Scanner scanner,
                               ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n=== Concluir Viagem ===");

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

        System.out.print("Selecione a viagem para concluir: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        Viagem viagem = agendadas.get(idx);
        viagem.concluir();
        System.out.println("Viagem concluída com sucesso!");

        // Avalia passageiros imediatamente após concluir
        ArrayList<Reserva> confirmados = new ArrayList<>();
        for (Reserva r : viagem.getReservas()) {
            if (r.isConfirmada()) confirmados.add(r);
        }

        if (confirmados.isEmpty()) {
            System.out.println("Nenhum passageiro nesta viagem para avaliar.");
            return;
        }

        System.out.print("\nDeseja avaliar os passageiros desta viagem agora? (1-Sim / 2-Não): ");
        if (scanner.nextInt() == 2) { scanner.nextLine(); return; }
        scanner.nextLine();

        avaliarPassageirosDeViagem(motorista, scanner, viagem, confirmados, avaliacoes);
    }

    // ─────────────────────────────────────────
    //  AVALIAR PASSAGEIROS DE VIAGEM ANTERIOR
    // ─────────────────────────────────────────

    /**
     * Fallback: permite avaliar passageiros de viagens já concluídas anteriormente
     * (caso o motorista tenha pulado a avaliação na hora de concluir).
     */
    public void avaliarViagemMotorista(Motorista motorista, Scanner scanner,
                                       ArrayList<Viagem> todasViagens,
                                       ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n=== Avaliar Passageiros de Viagem Anterior ===");

        // Encontra viagens concluídas que ainda têm passageiros não avaliados
        ArrayList<Viagem> disponiveis = new ArrayList<>();
        for (Viagem v : motorista.getViagens()) {
            if (!v.getStatus().equals("concluida")) continue;
            for (Reserva r : v.getReservas()) {
                if (r.isConfirmada() && !v.motoristJaAvaliouPassageiro(r.getPassageiro())) {
                    disponiveis.add(v);
                    break; // basta um passageiro pendente para incluir a viagem
                }
            }
        }

        if (disponiveis.isEmpty()) {
            System.out.println("Todos os passageiros de suas viagens já foram avaliados.");
            return;
        }

        System.out.println("Viagens com passageiros pendentes de avaliação:");
        for (int i = 0; i < disponiveis.size(); i++) {
            Viagem v = disponiveis.get(i);
            System.out.println((i + 1) + " - " + v.getPartida().getNome()
                    + " → " + v.getDestino().getNome());
        }

        System.out.print("Selecione a viagem: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idx < 0 || idx >= disponiveis.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Viagem viagem = disponiveis.get(idx);
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva r : viagem.getReservas()) {
            if (r.isConfirmada() && !viagem.motoristJaAvaliouPassageiro(r.getPassageiro())) {
                pendentes.add(r);
            }
        }

        avaliarPassageirosDeViagem(motorista, scanner, viagem, pendentes, avaliacoes);
    }

    // ─────────────────────────────────────────
    //  HELPER PRIVADO — avalia lista de passageiros de uma viagem
    // ─────────────────────────────────────────

    /**
     * Percorre cada passageiro da lista e coleta nota + comentário do motorista.
     * Reutilizado tanto em concluirViagem quanto em avaliarViagemMotorista.
     */
    private void avaliarPassageirosDeViagem(Motorista motorista, Scanner scanner,
                                            Viagem viagem, ArrayList<Reserva> passageiros,
                                            ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n── Avaliação dos passageiros ──");
        for (Reserva r : passageiros) {
            Usuario passageiro = r.getPassageiro();
            System.out.println("\nPassageiro: " + passageiro.getNome());

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

            Avaliacao avaliacao = new Avaliacao(motorista, passageiro, nota, comentario);
            avaliacoes.add(avaliacao);
            viagem.registrarAvaliacaoPassageiro(avaliacao);

            System.out.println("✓ Avaliação registrada: " + avaliacao);
        }
        System.out.println("\nTodos os passageiros desta viagem foram avaliados.");
    }

    // ─────────────────────────────────────────
    //  UTILITÁRIOS / GETTERS
    // ─────────────────────────────────────────

    @Override
    public void listarLocais(ArrayList<Local> locais) {
        super.listarLocais(locais);
    }

    @Override
    public boolean ehMotorista() { return true; }

    @Override
    public ArrayList<Viagem> getViagens() { return viagens; }

    /**
     * Registra viagem na lista de viagens do motorista.
     */
    public void adicionarViagem(Viagem v)  { viagens.add(v); }

    public String getModeloVeiculo()       { return modeloVeiculo; }
}
