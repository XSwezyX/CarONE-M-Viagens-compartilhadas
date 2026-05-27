import java.util.ArrayList;
import java.util.Scanner;

/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Representa um usuário com perfil de passageiro no sistema CarONE-M.
 *
 * <p>Passageiros podem buscar viagens disponíveis, solicitar caronas, acompanhar
 * o status de suas reservas e avaliar motoristas após viagens concluídas.</p>
 */
public class Passageiro extends Usuario {

    /**
     * Cria um novo passageiro com os dados pessoais fornecidos.
     * A validação dos campos é delegada ao construtor da superclasse {@link Usuario}.
     *
     * @param nome      nome completo
     * @param email     e-mail de acesso
     * @param telefone  número de telefone
     * @param senha     senha de autenticação
     * @param endereco  endereço residencial
     */
    public Passageiro(String nome, String email, String telefone, String senha, String endereco) {
        super(nome, email, telefone, senha, endereco);
    }

    // ─────────────────────────────────────────
    //  UTILITÁRIO — lê inteiro com intervalo válido
    // ─────────────────────────────────────────

    /**
     * Lê um número inteiro do console garantindo que o valor esteja dentro do intervalo [min, max].
     *
     * <p>Exibe mensagem de erro e repete a leitura enquanto o valor for inválido
     * ou não for um número inteiro.</p>
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
    //  VER RESERVAS
    // ─────────────────────────────────────────

    /**
     * Exibe no console todas as reservas do passageiro, com seu status atual.
     *
     * <p>Permite ao passageiro acompanhar solicitações pendentes, confirmadas
     * e recusadas em um único lugar.</p>
     *
     * @param passageiro o passageiro cujas reservas serão listadas
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
     * Conduz o fluxo completo de busca e solicitação de carona via console.
     *
     * <p>O processo segue as etapas abaixo:</p>
     * <ol>
     *   <li>Exibe a lista de locais disponíveis e solicita origem e destino;</li>
     *   <li>Filtra as viagens agendadas que aceitam passageiros e atendem o trajeto desejado;</li>
     *   <li>Apresenta as viagens encontradas ao passageiro;</li>
     *   <li>Permite ao passageiro escolher uma viagem e confirmar a solicitação;</li>
     *   <li>Cria a reserva com status {@code "pendente"} aguardando o motorista.</li>
     * </ol>
     *
     * @param passageiro o passageiro que está buscando a carona
     * @param scanner    instância de {@link Scanner} para leitura da entrada
     * @param locais     lista de todos os locais cadastrados no sistema
     * @param viagens    lista de todas as viagens cadastradas no sistema
     */
    public void buscarEPedirCarona(Passageiro passageiro, Scanner scanner,
                                   ArrayList<Local> locais, ArrayList<Viagem> viagens) {
        System.out.println("\n=== Buscar Carona ===");
        listarLocais(locais);

        System.out.print("Selecione sua ORIGEM: ");
        int idxOrigem  = lerOpcao(scanner, 1, locais.size()) - 1;

        System.out.print("Selecione seu DESTINO: ");
        int idxDestino = lerOpcao(scanner, 1, locais.size()) - 1;

        if (idxOrigem == idxDestino) {
            System.out.println("Origem e destino não podem ser iguais!");
            return;
        }

        Local origem  = locais.get(idxOrigem);
        Local destino = locais.get(idxDestino);

        // Filtra viagens agendadas, que aceitam passageiros e cobrem o trajeto solicitado
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
        int idx     = lerOpcao(scanner, 1, encontradas.size()) - 1;
        Viagem viagem = encontradas.get(idx);

        // Sugere os pontos do trajeto mais próximos da origem e do destino informados
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

    /**
     * Conduz o fluxo de avaliação de uma viagem concluída pelo passageiro.
     *
     * <p>Somente viagens com reserva confirmada, status {@code "concluida"} e
     * ainda não avaliadas pelo passageiro são listadas. A avaliação é composta
     * por uma nota de 1 a 5 e um comentário opcional.</p>
     *
     * @param passageiro o passageiro que está avaliando
     * @param scanner    instância de {@link Scanner} para leitura da entrada
     * @param viagens    lista de todas as viagens do sistema
     * @param avaliacoes lista global de avaliações onde a nova será registrada
     */
    public void avaliarViagem(Passageiro passageiro, Scanner scanner,
                              ArrayList<Viagem> viagens, ArrayList<Avaliacao> avaliacoes) {
        System.out.println("\n=== Avaliar Viagem ===");

        // Coleta as viagens elegíveis para avaliação
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
        int idx     = lerOpcao(scanner, 1, paraAvaliar.size()) - 1;
        Viagem viagem = paraAvaliar.get(idx);

        System.out.print("Nota (1 a 5): ");
        int nota = lerOpcao(scanner, 1, 5);

        System.out.print("Comentário (opcional, Enter para pular): ");
        String comentario = scanner.nextLine();

        // Cria a avaliação, adiciona à lista global e registra na viagem
        Avaliacao avaliacao = new Avaliacao(passageiro, viagem.getMotorista(), nota, comentario);
        avaliacoes.add(avaliacao);
        viagem.registrarAvaliacao(avaliacao);

        System.out.println("Avaliação registrada: " + avaliacao);
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
}
