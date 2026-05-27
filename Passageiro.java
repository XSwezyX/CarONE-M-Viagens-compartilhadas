import java.util.ArrayList;
import java.util.Scanner;

public class Passageiro extends Usuario {

    public Passageiro(String nome, String email, String telefone, String senha, String endereco) {
        super(nome, email, telefone, senha, endereco);
    }

    // ─────────────────────────────────────────
    //  UTILITÁRIO — lê inteiro com intervalo válido
    // ─────────────────────────────────────────

    private int lerOpcao(Scanner scanner, int min, int max) {
        int valor = -1;
        while (valor < min || valor > max) {
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor < min || valor > max) {
                    System.out.println("Opção inválida! Digite entre " + min + " e " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
        return valor;
    }

    // ─────────────────────────────────────────
    //  VER RESERVAS
    // ─────────────────────────────────────────

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

    public void buscarEPedirCarona(Passageiro passageiro, Scanner scanner,
                                   ArrayList<Local> locais, ArrayList<Viagem> viagens) {
        System.out.println("\n=== Buscar Carona ===");
        listarLocais(locais);

        System.out.print("Selecione sua ORIGEM: ");
        // BUG CORRIGIDO: índice validado com lerOpcao
        int idxOrigem = lerOpcao(scanner, 1, locais.size()) - 1;

        System.out.print("Selecione seu DESTINO: ");
        int idxDestino = lerOpcao(scanner, 1, locais.size()) - 1;

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
        if (lerOpcao(scanner, 1, 2) == 2) return;

        System.out.print("Selecione a viagem: ");
        // BUG CORRIGIDO: índice validado
        int idx = lerOpcao(scanner, 1, encontradas.size()) - 1;
        Viagem viagem = encontradas.get(idx);

        Local embarque    = viagem.pontoMaisProximo(origem);
        Local desembarque = viagem.pontoMaisProximo(destino);

        System.out.println("\nPonto de embarque:    " + embarque);
        System.out.println("Ponto de desembarque: " + desembarque);
        System.out.println("Motorista: " + viagem.getMotorista().getNome());

        System.out.print("\nConfirmar solicitação? (1-Sim / 2-Não): ");
        if (lerOpcao(scanner, 1, 2) == 2) return;

        viagem.solicitarReserva(passageiro, embarque, desembarque);

        System.out.println("\nSolicitação enviada com sucesso!");
        System.out.println("Aguarde a resposta do motorista " + viagem.getMotorista().getNome() + ".");
        System.out.println("Você pode acompanhar o status em 'Ver minhas reservas'.");
    }

    // ─────────────────────────────────────────
    //  AVALIAR VIAGEM (passageiro avalia motorista)
    // ─────────────────────────────────────────

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
        // BUG CORRIGIDO: índice validado
        int idx = lerOpcao(scanner, 1, paraAvaliar.size()) - 1;
        Viagem viagem = paraAvaliar.get(idx);

        System.out.print("Nota (1 a 5): ");
        int nota = lerOpcao(scanner, 1, 5);

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
