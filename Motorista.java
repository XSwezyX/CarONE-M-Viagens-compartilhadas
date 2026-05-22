import java.util.ArrayList;
import java.util.Scanner;

public class Motorista extends Usuario {

    private String modeloVeiculo;
    private ArrayList<Viagem> viagens = new ArrayList<>();

    public Motorista(String nome, String email, String telefone, String senha,
                     String endereco, String modeloVeiculo) {
        super(nome, email, telefone, senha, endereco);
        this.modeloVeiculo = modeloVeiculo;
    }

    public void cadastrarViagem(Motorista motorista, Scanner scanner, ArrayList<Local> locais, ArrayList<Viagem> viagens) {
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

    public void concluirViagem(Motorista motorista, Scanner scanner) {
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

        agendadas.get(idx).concluir();
        System.out.println("Viagem concluída com sucesso!");
    }

    @Override
    public void listarLocais(ArrayList<Local> locais) {
        super.listarLocais(locais);
    }

    @Override
    public boolean ehMotorista() { return true; }

    @Override
    public ArrayList<Viagem> getViagens() { return viagens; }

    public void adicionarViagem(Viagem v) { viagens.add(v); }
    public String getModeloVeiculo()      { return modeloVeiculo; }
}