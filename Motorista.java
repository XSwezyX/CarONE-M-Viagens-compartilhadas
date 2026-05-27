import java.util.ArrayList;
import java.util.Scanner;

/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Representa um usuário com perfil de motorista no sistema CarONE-M.
 *
 * <p>Além dos dados herdados de {@link Usuario}, o motorista possui o modelo do
 * veículo e uma lista própria de viagens cadastradas. Por meio deste perfil é
 * possível criar viagens, responder solicitações de passageiros, concluir viagens
 * e avaliar quem viajou junto.</p>
 */
public class Motorista extends Usuario {

    /** Modelo do veículo utilizado pelo motorista nas viagens (ex.: "Honda Civic"). */
    private String modeloVeiculo;

    /** Lista de viagens cadastradas e gerenciadas por este motorista. */
    private ArrayList<Viagem> viagens = new ArrayList<>();

    // ─────────────────────────────────────────
    //  CONSTRUTOR
    // ─────────────────────────────────────────

    /**
     * Cria um novo motorista com dados pessoais e modelo do veículo.
     * A validação dos campos comuns é delegada ao construtor de {@link Usuario}.
     *
     * @param nome           nome completo
     * @param email          e-mail de acesso
     * @param telefone       número de telefone
     * @param senha          senha de autenticação
     * @param endereco       endereço residencial
     * @param modeloVeiculo  modelo do veículo (ex.: "VW Gol")
     */
    public Motorista(String nome, String email, String telefone, String senha,
                     String endereco, String modeloVeiculo) {
        super(nome, email, telefone, senha, endereco);
        this.modeloVeiculo = modeloVeiculo;
    }

    // ─────────────────────────────────────────
    //  UTILITÁRIO — lê inteiro com intervalo válido
    // ─────────────────────────────────────────

    /**
     * Lê um número inteiro do console garantindo que o valor esteja no intervalo [min, max].
     *
     * <p>Repete a solicitação enquanto o valor for inválido ou não for um número inteiro.</p>
     *
     * @param scanner instância de {@link Scanner} para leitura da entrada
     * @param min     valor mínimo aceito (inclusivo)
     * @param max     valor máximo aceito (inclusivo)
     * @return número inteiro válido digitado pelo usuário
     */
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

    /**
     * Conduz o fluxo de cadastro de uma nova viagem via console.
     *
     * <p>O processo inclui:</p>
     * <ol>
     *   <li>Seleção do ponto de partida e do destino a partir da lista de locais;</li>
     *   <li>Adição opcional de paradas intermediárias ao trajeto;</li>
     *   <li>Definição do número de lugares disponíveis (mínimo 1);</li>
     *   <li>Escolha se a viagem aceita ou não passageiros adicionais.</li>
     * </ol>
     * <p>A viagem criada é adicionada tanto à lista global quanto ao perfil do motorista.</p>
     *
     * @param motorista motorista que está cadastrando a viagem
     * @param scanner   instância de {@link Scanner} para leitura da entrada
     * @param locais    lista de todos os locais disponíveis no sistema
     * @param viagens   lista global de viagens onde a nova será registrada
     */
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

        // Monta o trajeto iniciando pela partida
        ArrayList<Local> trajeto = new ArrayList<>();
        trajeto.add(locais.get(idxPartida));

        // Permite inserir paradas intermediárias antes do destino final
        System.out.print("Deseja adicionar paradas? (1-Sim / 2-Não): ");
        if (lerOpcao(scanner, 1, 2) == 1) {
            boolean adicionando = true;
            while (adicionando) {
                listarLocais(locais);
                System.out.print("Selecione a parada: ");
                int idx = lerOpcao(scanner, 1, locais.size()) - 1;
                trajeto.add(locais.get(idx));
                System.out.println("Parada adicionada: " + locais.get(idx).getNome());
                System.out.print("Adicionar mais uma? (1-Sim / 2-Não): ");
                adicionando = (lerOpcao(scanner, 1, 2) == 1);
            }
        }

        // Adiciona o destino final ao fim do trajeto
        trajeto.add(locais.get(idxDestino));

        // Solicita o número de vagas, exigindo pelo menos 1
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

    /**
     * Exibe os passageiros confirmados em uma das viagens agendadas do motorista.
     *
     * <p>Lista apenas viagens com status {@code "agendada"} e, após a seleção,
     * apresenta somente as reservas com status {@code "confirmada"}.</p>
     *
     * @param motorista o motorista que deseja consultar seus passageiros
     * @param scanner   instância de {@link Scanner} para leitura da entrada
     */
    public void verPassageiros(Motorista motorista, Scanner scanner) {
        System.out.println("\n=== Passageiros das Minhas Viagens ===");

        // Filtra apenas as viagens ainda em andamento
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
        int idx     = lerOpcao(scanner, 1, agendadas.size()) - 1;
        Viagem viagem = agendadas.get(idx);

        // Coleta apenas as reservas já aprovadas pelo motorista
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
     * Exibe e processa todas as solicitações de carona pendentes do motorista.
     *
     * <p>Percorre todas as viagens agendadas do motorista, coleta as reservas com
     * status {@code "pendente"} e apresenta cada uma ao motorista para aceitar ou recusar.
     * Ao aceitar, um lugar é descontado do veículo; ao recusar, a reserva é marcada como
     * {@code "recusada"} e o passageiro poderá visualizar a resposta em suas reservas.</p>
     *
     * @param motorista o motorista que está respondendo as solicitações
     * @param scanner   instância de {@link Scanner} para leitura da entrada
     */
    public void responderSolicitacoes(Motorista motorista, Scanner scanner) {
        System.out.println("\n=== Solicitações de Carona Pendentes ===");

        // Reúne todas as solicitações pendentes de todas as viagens agendadas
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

    /**
     * Exibe a média geral e todos os comentários de avaliações recebidos pelo motorista.
     *
     * @param motorista o motorista cujas avaliações serão exibidas
     */
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

    /**
     * Marca uma viagem agendada como concluída e oferece avaliação imediata dos passageiros.
     *
     * <p>Após selecionar a viagem, o motorista pode optar por avaliar todos os
     * passageiros confirmados naquele momento. Caso prefira avaliar depois,
     * pode acessar a opção "Avaliar passageiros de viagem anterior".</p>
     *
     * @param motorista  o motorista que está concluindo a viagem
     * @param scanner    instância de {@link Scanner} para leitura da entrada
     * @param avaliacoes lista global de avaliações onde as novas serão registradas
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
        int idx     = lerOpcao(scanner, 1, agendadas.size()) - 1;
        Viagem viagem = agendadas.get(idx);

        viagem.concluir();
        System.out.println("Viagem concluída com sucesso!");

        // Coleta passageiros confirmados para oferecer avaliação imediata
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

    /**
     * Permite ao motorista avaliar passageiros de viagens concluídas ainda não avaliados.
     *
     * <p>Lista apenas as viagens onde ainda existe pelo menos um passageiro confirmado
     * pendente de avaliação pelo motorista. Após a seleção da viagem, todos os
     * passageiros ainda não avaliados são percorridos para avaliação.</p>
     *
     * @param motorista    o motorista que deseja avaliar
     * @param scanner      instância de {@link Scanner} para leitura da entrada
     * @param todasViagens lista global de viagens (não utilizada diretamente; a filtragem
     *                     é feita nas viagens do próprio motorista)
     * @param avaliacoes   lista global de avaliações onde as novas serão registradas
     */
    public void avaliarViagemMotorista(Motorista motorista, Scanner scanner,
                                       ArrayList<Viagem> todasViagens,
                                       ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n=== Avaliar Passageiros de Viagem Anterior ===");

        // Filtra viagens concluídas que possuem ao menos um passageiro ainda não avaliado
        ArrayList<Viagem> disponiveis = new ArrayList<>();
        for (Viagem v : motorista.getViagens()) {
            if (!v.getStatus().equals("concluida")) continue;
            for (Reserva r : v.getReservas()) {
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
        int idx     = lerOpcao(scanner, 1, disponiveis.size()) - 1;
        Viagem viagem = disponiveis.get(idx);

        // Coleta apenas os passageiros confirmados ainda sem avaliação nesta viagem
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva r : viagem.getReservas()) {
            if (r.isConfirmada() && !viagem.motoristaJaAvaliouPassageiro(r.getPassageiro())) {
                pendentes.add(r);
            }
        }

        avaliarPassageirosDeViagem(motorista, scanner, viagem, pendentes, avaliacoes);
    }

    // ─────────────────────────────────────────
    //  HELPER PRIVADO — avalia lista de passageiros de uma viagem
    // ─────────────────────────────────────────

    /**
     * Percorre uma lista de reservas e solicita ao motorista que avalie cada passageiro.
     *
     * <p>Método auxiliar compartilhado entre {@link #concluirViagem} e
     * {@link #avaliarViagemMotorista} para evitar duplicação de lógica.
     * Para cada passageiro, coleta nota e comentário opcional, cria a {@link Avaliacao}
     * e a registra tanto na lista global quanto na viagem.</p>
     *
     * @param motorista    motorista que está realizando as avaliações
     * @param scanner      instância de {@link Scanner} para leitura da entrada
     * @param viagem       viagem à qual as reservas pertencem
     * @param passageiros  lista de reservas cujos passageiros serão avaliados
     * @param avaliacoes   lista global de avaliações onde as novas serão adicionadas
     */
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
    //  EXIBIÇÃO DE LOCAIS
    // ─────────────────────────────────────────

    /**
     * Delega a exibição da lista de locais à implementação da superclasse.
     *
     * @param locais lista de locais a exibir
     */
    @Override
    public void listarLocais(ArrayList<Local> locais) {
        super.listarLocais(locais);
    }

    // ─────────────────────────────────────────
    //  IDENTIFICAÇÃO E GETTERS
    // ─────────────────────────────────────────

    /**
     * Confirma que este usuário é um motorista.
     *
     * @return {@code true} sempre, sobrescrevendo o {@code false} padrão de {@link Usuario}
     */
    @Override
    public boolean ehMotorista() { return true; }

    /**
     * Retorna as viagens cadastradas por este motorista.
     *
     * @return lista de viagens do motorista
     */
    @Override
    public ArrayList<Viagem> getViagens() { return viagens; }

    /**
     * Adiciona uma viagem à lista pessoal do motorista.
     *
     * @param v viagem a ser associada ao perfil
     */
    public void adicionarViagem(Viagem v) { viagens.add(v); }

    /** @return modelo do veículo utilizado pelo motorista */
    public String getModeloVeiculo()      { return modeloVeiculo; }
}
