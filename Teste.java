import java.util.ArrayList;

public class Teste {

    public static void main(String[] args) {

        System.out.println("=== TESTE 1: Criando Locais ===");
        Local paulista    = new Local("Paulista",      "Av. Paulista, 900",    0,  0);
        Local pinheiros   = new Local("Pinheiros",     "Largo da Batata",     -5,  2);
        Local itaim       = new Local("Itaim Bibi",    "Av. Faria Lima, 500", -2, -5);
        Local moema       = new Local("Moema",         "Av. Ibirapuera, 200",  1, -7);
        Local consolacao  = new Local("Consolação",    "R. Consolação, 400",  -1,  1);
        System.out.println("Locais criados: OK");
        System.out.println(paulista);
        System.out.println(pinheiros);

        System.out.println("\n=== TESTE 2: Distância entre Locais ===");
        double dist = paulista.distancia(consolacao);
        System.out.println("Distância Paulista → Consolação: " + dist);
        System.out.println("Esperado: menor que 2.0? " + (dist <= 2.0));

        System.out.println("\n=== TESTE 3: Criando Motorista e Passageiro ===");
        Motorista joao = new Motorista("João Silva","joao@gmail.com", "11999923", "1234","rua 3",
                "Honda Civic");
        Passageiro maria = new Passageiro("Maria Souza",
                "maria@gmail.com", "11234512", "4567","rua 4");
        System.out.println("Motorista: " + joao.getNome() + " | OK");
        System.out.println("Passageiro: " + maria.getNome() + " | OK");

        System.out.println("\n=== TESTE 4: Criando Viagem ===");
        ArrayList<Local> trajeto = new ArrayList<>();
        trajeto.add(paulista);   // partida
        trajeto.add(itaim);      // parada
        trajeto.add(moema);      // destino
        Viagem viagem = new Viagem(joao, trajeto, 3);
        System.out.println("Viagem criada: " + viagem);

        System.out.println("\n=== TESTE 5: podeAtenderPassageiro ===");
        // Consolação está perto de Paulista (distância ~1.4) → deve aceitar
        boolean teste1 = viagem.podeAtenderPassageiro(consolacao, moema);
        System.out.println("Consolação → Moema (deve ser true):  " + teste1);

        // Moema → Paulista: destino vem ANTES do embarque no trajeto → deve rejeitar
        boolean teste2 = viagem.podeAtenderPassageiro(moema, paulista);
        System.out.println("Moema → Paulista (deve ser false): " + teste2);

        // Pinheiros está longe do trajeto (distância > 2.0) → deve rejeitar
        boolean teste3 = viagem.podeAtenderPassageiro(pinheiros, moema);
        System.out.println("Pinheiros → Moema (deve ser false): " + teste3);

        System.out.println("\n=== TESTE 6: pontoMaisProximo ===");
        Local maisProximo = viagem.pontoMaisProximo(consolacao);
        System.out.println("Ponto mais próximo de Consolação: " + maisProximo.getNome());
        System.out.println("Esperado: Paulista");

        System.out.println("\n=== TESTE 7: Reserva e adicionarReserva ===");
        Reserva reserva = new Reserva(maria, paulista, moema);
        viagem.adicionarReserva(reserva);
        System.out.println("Lugares antes: 3 | Agora: " + viagem.getLugaresDisponiveis());
        System.out.println("Esperado: 2");

        System.out.println("\n=== TESTE 8: usuarioParticipou ===");
        viagem.concluir();
        System.out.println("Maria participou? " + viagem.usuarioParticipou(maria));
        System.out.println("Esperado: true");
        System.out.println("João participou? " + viagem.usuarioParticipou(joao));
        System.out.println("Esperado: true");

        System.out.println("\n=== TESTE 9: Avaliacao ===");
        Avaliacao av = new Avaliacao(maria, joao, 5, "Ótima viagem!");
        System.out.println(av);
        Avaliacao avSemComentario = new Avaliacao(maria, joao, 3, "");
        System.out.println(avSemComentario);

        System.out.println("\n=== TODOS OS TESTES CONCLUÍDOS ===");
    }
}