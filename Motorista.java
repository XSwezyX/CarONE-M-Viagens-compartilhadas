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
    //  CADASTRAR VIAGEM
    // ─────────────────────────────────────────

    public void cadastrarViagem(Motorista motorista, Scanner scanner,
                                ArrayList<Local> locais, ArrayList<Viagem> viagens) {
        System.out.println("\n=== Cadastrar Viagem ===");
        motorista.listarLocais(locais);

        System.out.print("Selecione o local de PARTIDA: ");
        int idxPartida = lerOpcao(scanner, 1, locais.size()) - 1;

        System.out.print("Selecione o local de DESTINO: ");
        int idxDestino = lerOpcao(scanner, 1, locais.size()) - 1;

        if (idxPartida == idxDestino) {
            System.out.println("Partida e destino não podem ser iguais!");
            return;
        }

        ArrayList<Local> trajeto = new ArrayList<>();
        trajeto.add(locais.get(idxPartida));

        System.out.print("Deseja adicionar paradas? (1-Sim / 2-Não): ");
        int addParada = lerOpcao(scanner, 1, 2);

        if (addParada == 1) {
            boolean adicionando = true;
            while (adicionando) {
                listarLocais(locais);
                System.out.print("Selecione a parada: ");
                int idx = lerOpcao(scanner, 1, locais.size()) - 1;
                trajeto.add(locais.get(idx));
                System.out.println("Parada adicionada: " + locais.get(idx).getNome());
                System.out.print("Adicionar mais uma? (1-Sim / 2-Não): ");
                int mais = lerOpcao(scanner, 1, 2);
                adicionando = (mais == 1);
            }
        }

        trajeto.add(locais.get(idxDestino));

        // BUG CORRIGIDO: vagas precisam ser >= 1
        int lugares = 0;
        while (lugares < 1) {
            System.out.print("Número de lugares disponíveis (mínimo 1): ");
            try {
                lugares = Integer.parseInt(scanner.nextLine().trim());
                if (lugares < 1) System.out.println("Deve haver pelo menos 1 lugar disponível.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }

        System.out.print("Aceitar passageiros? (1-Sim / 2-Não): ");
        boolean aceita = (lerOpcao(scanner, 1, 2) == 1);

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
        // BUG CORRIGIDO: índice validado com lerOpcao
        int idx = lerOpcao(scanner, 1, agendadas.size()) - 1;
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

    public void responderSolicitacoes(Motorista motorista, Scanner scanner) {
        System.out.println("\n=== Solicitações de Carona Pendentes ===");

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
            int escolha = lerOpcao(scanner, 1, 2);

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

    // BUG CORRIGIDO: parâmetro Scanner removido (nunca foi usado)
    public void verAvaliacoes(Motorista motorista) {
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
        // BUG CORRIGIDO: índice validado
        int idx = lerOpcao(scanner, 1, agendadas.size()) - 1;

        Viagem viagem = agendadas.get(idx);
        viagem.concluir();
        System.out.println("Viagem concluída com sucesso!");

        ArrayList<Reserva> confirmados = new ArrayList<>();
        for (Reserva r : viagem.getReservas()) {
            if (r.isConfirmada()) confirmados.add(r);
        }

        if (confirmados.isEmpty()) {
            System.out.println("Nenhum passageiro nesta viagem para avaliar.");
            return;
        }

        System.out.print("\nDeseja avaliar os passageiros desta viagem agora? (1-Sim / 2-Não): ");
        if (lerOpcao(scanner, 1, 2) == 2) return;

        avaliarPassageirosDeViagem(motorista, scanner, viagem, confirmados, avaliacoes);
    }

    // ─────────────────────────────────────────
    //  AVALIAR PASSAGEIROS DE VIAGEM ANTERIOR
    // ─────────────────────────────────────────

    public void avaliarViagemMotorista(Motorista motorista, Scanner scanner,
                                       ArrayList<Viagem> todasViagens,
                                       ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n=== Avaliar Passageiros de Viagem Anterior ===");

        ArrayList<Viagem> disponiveis = new ArrayList<>();
        for (Viagem v : motorista.getViagens()) {
            if (!v.getStatus().equals("concluida")) continue;
            for (Reserva r : v.getReservas()) {
                // BUG CORRIGIDO: nome do método corrigido (era motoristJaAvaliouPassageiro)
                if (r.isConfirmada() && !v.motoristaJaAvaliouPassageiro(r.getPassageiro())) {
                    disponiveis.add(v);
                    break;
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
        // BUG CORRIGIDO: índice validado
        int idx = lerOpcao(scanner, 1, disponiveis.size()) - 1;

        Viagem viagem = disponiveis.get(idx);
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva r : viagem.getReservas()) {
            // BUG CORRIGIDO: nome do método corrigido
            if (r.isConfirmada() && !viagem.motoristaJaAvaliouPassageiro(r.getPassageiro())) {
                pendentes.add(r);
            }
        }

        avaliarPassageirosDeViagem(motorista, scanner, viagem, pendentes, avaliacoes);
    }

    // ─────────────────────────────────────────
    //  HELPER PRIVADO — avalia lista de passageiros de uma viagem
    // ─────────────────────────────────────────

    private void avaliarPassageirosDeViagem(Motorista motorista, Scanner scanner,
                                            Viagem viagem, ArrayList<Reserva> passageiros,
                                            ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n── Avaliação dos passageiros ──");
        for (Reserva r : passageiros) {
            Usuario passageiro = r.getPassageiro();
            System.out.println("\nPassageiro: " + passageiro.getNome());

            System.out.print("Nota (1 a 5): ");
            int nota = lerOpcao(scanner, 1, 5);

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

    public void adicionarViagem(Viagem v)  { viagens.add(v); }
    public String getModeloVeiculo()       { return modeloVeiculo; }
}
