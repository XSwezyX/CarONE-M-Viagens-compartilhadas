import java.util.ArrayList;
import java.util.Scanner;

public class Passageiro extends Usuario {
    private ArrayList<Usuario> passageiros = new ArrayList<>();

    public Passageiro(String nome, String email, String telefone, String senha, String endereco) {
        super(nome, email, telefone, senha, endereco);
        
    }

    public void verReservas(Passageiro passageiro) {
        System.out.println("\n=== Minhas Reservas ===");

        ArrayList<Reserva>reservas = passageiro.getReservas();
        if (reservas.isEmpty()) {
            System.out.println("Você não possui reservas.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println("  • " + r);
        }
    }

    @Override
    public void listarLocais(ArrayList<Local> locais) {
        super.listarLocais(locais);
    }

    public void buscarEPedirCarona(Passageiro passageiro, Scanner scanner, ArrayList<Local> locais, ArrayList<Viagem> viagens) {
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

        Reserva reserva = viagem.confirmarReserva(passageiro, embarque, desembarque);

        if (reserva.isConfirmada()) {
            System.out.println("\nCarona confirmada! Boa viagem.");
        } else {
            System.out.println("\nMotorista recusou a carona. Tente outra viagem.");
        }
    }

    public void avaliarViagem(Passageiro passageiro, Scanner scanner, ArrayList<Viagem> viagens, ArrayList<Avaliacao> avaliacoes) {
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
}