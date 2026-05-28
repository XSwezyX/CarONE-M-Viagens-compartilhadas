import java.util.ArrayList;
import java.util.Scanner;

/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Representa o usuário do tipo passageiro.
 *
 * Passageiros podem buscar viagens disponíveis, solicitar caronas, consultar
 * reservas e avaliar motoristas após viagens concluídas.
 */
public class Passageiro extends Usuario {

    // O passageiro herda todo o comportamento comum de Usuario.
    public Passageiro(String nome, String email, String telefone, String senha, String endereco) {
        super(nome, email, telefone, senha, endereco);
    }

    // ─────────────────────────────────────────
    //  VER RESERVAS
    // ─────────────────────────────────────────

    /**
     * Exibe todas as reservas do passageiro com situação atual.
     *
     * Inclui reservas pendentes, confirmadas e recusadas, permitindo que o passageiro
     * acompanhe o status de cada solicitação.
     */
    public void verReservas(Passageiro passageiro) {
        System.out.println("\n=== Minhas Reservas ===");

        ArrayList<Reserva> reservas = passageiro.getReservas();
        if (reservas.isEmpty()) {
            System.out.println("Você não possui reservas.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println("  • " + r);
        }
    }

    // ─────────────────────────────────────────
    //  BUSCAR E PEDIR CARONA
    // ─────────────────────────────────────────

    /**
     * Busca viagens disponíveis para origem e destino indicados, e envia solicitação.
     *
     * O método filtra apenas viagens que estão agendadas, que aceitam passageiros,
     * que ainda possuem vagas e cujo trajeto cobre tanto a origem quanto o destino
     * na ordem correta.
     */
    public void buscarEPedirCarona(Passageiro passageiro, Scanner scanner,
                                   ArrayList<Local> locais, ArrayList<Viagem> viagens) {
        System.out.println("\n=== Buscar Carona ===");
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

        ArrayList<Viagem> encontradas = new ArrayList<>();
        for (Viagem v : viagens) {
            if (v.getStatus().equals("agendada")
                    && v.isAceitaPassageiros()
                    && v.podeAtenderPassageiro(origem, destino)) {
                encontradas.add(v);
            }
        }

        if (encontradas.isEmpty()) {
            System.out.println("Nenhuma viagem disponível para este trajeto.");
            return;
        }

        System.out.println("\n" + encontradas.size() + " viagem(ns) encontrada(s):");
        for (int i = 0; i < encontradas.size(); i++) {
            System.out.println((i + 1) + " - " + encontradas.get(i));
        }

        System.out.print("\nDeseja solicitar uma carona? (1-Sim / 2-Não): ");
        if (scanner.nextInt() == 2) { scanner.nextLine(); return; }
        scanner.nextLine();

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
        if (scanner.nextInt() == 2) { scanner.nextLine(); return; }
        scanner.nextLine();

        // Cria reserva com status "pendente" — aguarda aprovação do motorista
        viagem.solicitarReserva(passageiro, embarque, desembarque);

        System.out.println("\nSolicitação enviada com sucesso!");
        System.out.println("Aguarde a resposta do motorista " + viagem.getMotorista().getNome() + ".");
        System.out.println("Você pode acompanhar o status em 'Ver minhas reservas'.");
    }

    // ─────────────────────────────────────────
    //  AVALIAR VIAGEM (passageiro avalia motorista)
    // ─────────────────────────────────────────

    /**
     * Permite que o passageiro avalie o motorista de uma viagem concluída.
     *
     * O passageiro só pode avaliar viagens onde sua reserva foi confirmada,
     * a viagem já está concluída e ele ainda não avaliou aquele motorista.
     */
    public void avaliarViagem(Passageiro passageiro, Scanner scanner,
                              ArrayList<Viagem> viagens, ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n=== Avaliar Viagem ===");

        ArrayList<Viagem> paraAvaliar = new ArrayList<>();
        for (Reserva r : passageiro.getReservas()) {
            Viagem v = r.getViagem();
            if (r.isConfirmada()
                    && v.getStatus().equals("concluida")
                    && !v.usuarioJaAvaliou(passageiro)) {
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

        Avaliacao avaliacao = new Avaliacao(passageiro, viagem.getMotorista(), nota, comentario);
        avaliacoes.add(avaliacao);
        viagem.registrarAvaliacao(avaliacao);

        System.out.println("Avaliação registrada: " + avaliacao);
    }

    @Override
    public void listarLocais(ArrayList<Local> locais) {
        super.listarLocais(locais);
    }
}
