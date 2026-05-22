import java.util.ArrayList;

public class Sistema {

    private ArrayList<Usuario>   usuarios;
    private ArrayList<Local>     locais;
    private ArrayList<Viagem>    viagens;
    private ArrayList<Avaliacao> avaliacoes;

    public Sistema() {
        usuarios   = new ArrayList<>();
        locais     = new ArrayList<>();
        viagens    = new ArrayList<>();
        avaliacoes = new ArrayList<>();

        inicializarLocais();
        inicializarDados();
    }

    // ══════════════════════════════════════════
    //  LOCAIS PRÉ-DEFINIDOS
    // ══════════════════════════════════════════

    private void inicializarLocais() {
        locais.add(new Local("Paulista",      "Av. Paulista, 900",        0,   0));
        locais.add(new Local("Pinheiros",     "Largo da Batata",         -5,   2));
        locais.add(new Local("Vila Madalena", "Rua Harmonia, 100",       -4,   5));
        locais.add(new Local("Itaim Bibi",    "Av. Faria Lima, 500",     -2,  -5));
        locais.add(new Local("Moema",         "Av. Ibirapuera, 200",      1,  -7));
        locais.add(new Local("Consolação",    "R. Consolação, 400",      -1,   1));
        locais.add(new Local("Santana",       "Av. Cruzeiro do Sul, 10",  2,   9));
        locais.add(new Local("Tatuapé",       "R. Cavalheiro, 50",        8,   3));
        locais.add(new Local("Santo André",   "Praça do Carmo, 1",       10,  -8));
        locais.add(new Local("São Bernardo",  "Av. Kennedy, 200",         6, -12));
    }

    // ══════════════════════════════════════════
    //  DADOS PRÉ-POPULADOS
    // ══════════════════════════════════════════

    private void inicializarDados() {

    // ══════════════════════════════════════════
    // USUÁRIOS
    // ══════════════════════════════════════════

    Motorista joao = new Motorista(
            "João Silva",
            "joao@gmail.com",
            "11999991111",
            "1234",
            "Rua A, 10",
            "Honda Civic"
    );

    Motorista pedro = new Motorista(
            "Pedro Lima",
            "pedro@gmail.com",
            "11988882222",
            "1234",
            "Rua B, 20",
            "VW Gol"
    );

    Motorista carla = new Motorista(
            "Carla Mendes",
            "carla@gmail.com",
            "11977773333",
            "1234",
            "Rua C, 30",
            "Toyota Corolla"
    );

    Motorista lucas = new Motorista(
            "Lucas Rocha",
            "lucas@gmail.com",
            "11966664444",
            "1234",
            "Rua D, 40",
            "Jeep Renegade"
    );

    Passageiro maria = new Passageiro(
            "Maria Souza",
            "maria@gmail.com",
            "11955556666",
            "1234",
            "Rua E, 50"
    );

    Passageiro ana = new Passageiro(
            "Ana Costa",
            "ana@gmail.com",
            "11944445555",
            "1234",
            "Rua F, 60"
    );

    Passageiro bruno = new Passageiro(
            "Bruno Alves",
            "bruno@gmail.com",
            "11933334444",
            "1234",
            "Rua G, 70"
    );

    Passageiro julia = new Passageiro(
            "Julia Martins",
            "julia@gmail.com",
            "11922223333",
            "1234",
            "Rua H, 80"
    );

    Passageiro fernanda = new Passageiro(
            "Fernanda Ribeiro",
            "fernanda@gmail.com",
            "11911112222",
            "1234",
            "Rua I, 90"
    );

    Passageiro gustavo = new Passageiro(
            "Gustavo Nunes",
            "gustavo@gmail.com",
            "11900001111",
            "1234",
            "Rua J, 100"
    );

    usuarios.add(joao);
    usuarios.add(pedro);
    usuarios.add(carla);
    usuarios.add(lucas);

    usuarios.add(maria);
    usuarios.add(ana);
    usuarios.add(bruno);
    usuarios.add(julia);
    usuarios.add(fernanda);
    usuarios.add(gustavo);

    // ══════════════════════════════════════════
    // LOCAIS
    // ══════════════════════════════════════════

    Local paulista      = locais.get(0);
    Local pinheiros     = locais.get(1);
    Local vilaMad       = locais.get(2);
    Local itaim         = locais.get(3);
    Local moema         = locais.get(4);
    Local consolacao    = locais.get(5);
    Local santana       = locais.get(6);
    Local tatuape       = locais.get(7);
    Local santoAndre    = locais.get(8);
    Local saoBernardo   = locais.get(9);

    // ══════════════════════════════════════════
    // VIAGEM 1
    // João levou Maria
    // ══════════════════════════════════════════

    ArrayList<Local> t1 = new ArrayList<>();

    t1.add(paulista);
    t1.add(itaim);
    t1.add(moema);

    Viagem v1 = new Viagem(joao, t1, 3, true);

    Reserva r1 = new Reserva(
            maria,
            v1,
            paulista,
            moema
    );

    v1.adicionarReserva(r1);

    maria.adicionarReserva(r1);

    v1.concluir();

    viagens.add(v1);

    joao.adicionarViagem(v1);

    Avaliacao av1 = new Avaliacao(
            maria,
            joao,
            5,
            "Motorista muito educado!"
    );

    avaliacoes.add(av1);

    joao.receberAvaliacao(av1);

    v1.registrarAvaliacao(maria);

    // ══════════════════════════════════════════
    // VIAGEM 2
    // Pedro levou Ana
    // ══════════════════════════════════════════

    ArrayList<Local> t2 = new ArrayList<>();

    t2.add(santana);
    t2.add(consolacao);
    t2.add(paulista);

    Viagem v2 = new Viagem(pedro, t2, 2, true);

    Reserva r2 = new Reserva(
            ana,
            v2,
            santana,
            paulista
    );

    v2.adicionarReserva(r2);

    ana.adicionarReserva(r2);

    v2.concluir();

    viagens.add(v2);

    pedro.adicionarViagem(v2);

    Avaliacao av2 = new Avaliacao(
            ana,
            pedro,
            4,
            "Boa viagem."
    );

    avaliacoes.add(av2);

    pedro.receberAvaliacao(av2);

    v2.registrarAvaliacao(ana);

    // ══════════════════════════════════════════
    // VIAGEM 3
    // Carla levou Bruno e Julia
    // ══════════════════════════════════════════

    ArrayList<Local> t3 = new ArrayList<>();

    t3.add(pinheiros);
    t3.add(consolacao);
    t3.add(tatuape);

    Viagem v3 = new Viagem(carla, t3, 4, true);

    Reserva r3a = new Reserva(
            bruno,
            v3,
            pinheiros,
            tatuape
    );

    Reserva r3b = new Reserva(
            julia,
            v3,
            consolacao,
            tatuape
    );

    v3.adicionarReserva(r3a);
    v3.adicionarReserva(r3b);

    bruno.adicionarReserva(r3a);
    julia.adicionarReserva(r3b);

    v3.concluir();

    viagens.add(v3);

    carla.adicionarViagem(v3);

    Avaliacao av3a = new Avaliacao(
            bruno,
            carla,
            5,
            "Excelente motorista!"
    );

    Avaliacao av3b = new Avaliacao(
            julia,
            carla,
            4,
            "Viagem tranquila."
    );

    avaliacoes.add(av3a);
    avaliacoes.add(av3b);

    carla.receberAvaliacao(av3a);
    carla.receberAvaliacao(av3b);

    v3.registrarAvaliacao(bruno);
    v3.registrarAvaliacao(julia);

    // ══════════════════════════════════════════
    // VIAGEM 4
    // Lucas levou Fernanda
    // ══════════════════════════════════════════

    ArrayList<Local> t4 = new ArrayList<>();

    t4.add(santoAndre);
    t4.add(saoBernardo);
    t4.add(moema);

    Viagem v4 = new Viagem(lucas, t4, 3, true);

    Reserva r4 = new Reserva(
            fernanda,
            v4,
            santoAndre,
            moema
    );

    v4.adicionarReserva(r4);

    fernanda.adicionarReserva(r4);

    v4.concluir();

    viagens.add(v4);

    lucas.adicionarViagem(v4);

    Avaliacao av4 = new Avaliacao(
            fernanda,
            lucas,
            5,
            "Muito confortável."
    );

    avaliacoes.add(av4);

    lucas.receberAvaliacao(av4);

    v4.registrarAvaliacao(fernanda);

    // ══════════════════════════════════════════
    // VIAGEM 5
    // AGENDADA
    // ══════════════════════════════════════════

    ArrayList<Local> t5 = new ArrayList<>();

    t5.add(vilaMad);
    t5.add(pinheiros);
    t5.add(itaim);

    Viagem v5 = new Viagem(joao, t5, 2, true);

    viagens.add(v5);

    joao.adicionarViagem(v5);

    // ══════════════════════════════════════════
    // VIAGEM 6
    // AGENDADA
    // ══════════════════════════════════════════

    ArrayList<Local> t6 = new ArrayList<>();

    t6.add(tatuape);
    t6.add(paulista);
    t6.add(moema);

    Viagem v6 = new Viagem(pedro, t6, 3, true);

    viagens.add(v6);

    pedro.adicionarViagem(v6);

    // ══════════════════════════════════════════
    // VIAGEM 7
    // LOTADA
    // ══════════════════════════════════════════

    ArrayList<Local> t7 = new ArrayList<>();

    t7.add(santana);
    t7.add(consolacao);
    t7.add(itaim);

    Viagem v7 = new Viagem(carla, t7, 1, true);

    Reserva r7 = new Reserva(
            gustavo,
            v7,
            santana,
            itaim
    );

    v7.adicionarReserva(r7);

    gustavo.adicionarReserva(r7);

    viagens.add(v7);

    carla.adicionarViagem(v7);

    // ══════════════════════════════════════════
    // VIAGEM 8
    // NÃO ACEITA PASSAGEIROS
    // ══════════════════════════════════════════

    ArrayList<Local> t8 = new ArrayList<>();

    t8.add(vilaMad);
    t8.add(pinheiros);
    t8.add(paulista);

    Viagem v8 = new Viagem(lucas, t8, 2, false);

    viagens.add(v8);

    lucas.adicionarViagem(v8);

    // ══════════════════════════════════════════
    // VIAGEM 9
    // AGENDADA
    // ══════════════════════════════════════════

    ArrayList<Local> t9 = new ArrayList<>();

    t9.add(moema);
    t9.add(itaim);
    t9.add(consolacao);

    Viagem v9 = new Viagem(carla, t9, 3, true);

    viagens.add(v9);

    carla.adicionarViagem(v9);

    // ══════════════════════════════════════════
    // VIAGEM 10
    // AGENDADA
    // ══════════════════════════════════════════

    ArrayList<Local> t10 = new ArrayList<>();

    t10.add(saoBernardo);
    t10.add(santoAndre);
    t10.add(tatuape);

    Viagem v10 = new Viagem(joao, t10, 4, true);

    viagens.add(v10);

    joao.adicionarViagem(v10);

    ArrayList<Local> t11 = new ArrayList<>();

    t11.add(pinheiros);
    t11.add(consolacao);
    t11.add(tatuape);

    Viagem v11 = new Viagem(
            carla,
            t11,
            3,
            true
    );

    viagens.add(v11);

    carla.adicionarViagem(v11);
}

    // ══════════════════════════════════════════
    //  AUTENTICAÇÃO
    // ══════════════════════════════════════════

    public Usuario fazerLogin(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.verificarSenha(senha)) {
                return u;
            }
        }
        return null;
    }

    // ══════════════════════════════════════════
    //  CADASTRO
    // ══════════════════════════════════════════

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean emailJaCadastrado(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) return true;
        }
        return false;
    }

    public void cadastrarViagem(Viagem viagem, Motorista motorista) {
        viagens.add(viagem);
        motorista.adicionarViagem(viagem);
    }

    // ══════════════════════════════════════════
    //  BUSCA DE CARONAS
    // ══════════════════════════════════════════

    public ArrayList<Viagem> buscarCaronas(Local origem, Local destino) {
        ArrayList<Viagem> resultado = new ArrayList<>();
        for (Viagem v : viagens) {
            if (v.getStatus().equals("agendada") && v.isAceitaPassageiros() && v.podeAtenderPassageiro(origem, destino)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    // ══════════════════════════════════════════
    //  CONFIRMAR CARONA
    // ══════════════════════════════════════════

    public Reserva confirmarCarona(Usuario passageiro, Viagem viagem,
                                   Local embarque, Local desembarque) {
        Reserva reserva = new Reserva(passageiro, viagem, embarque, desembarque);

        if (viagem.isAceitaPassageiros()) {
            viagem.adicionarReserva(reserva);
            passageiro.adicionarReserva(reserva);
        } else {
            reserva.recusar();
        }

        return reserva;
    }

    // ══════════════════════════════════════════
    //  AVALIAÇÃO
    // ══════════════════════════════════════════

    public void registrarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
        avaliacao.getAvaliado().receberAvaliacao(avaliacao);
    }

    // ══════════════════════════════════════════
    //  GETTERS
    // ══════════════════════════════════════════

    public ArrayList<Usuario>   getUsuarios()  { return usuarios; }
    public ArrayList<Local>     getLocais()    { return locais; }
    public ArrayList<Viagem>    getViagens()   { return viagens; }
    public ArrayList<Avaliacao> getAvaliacoes(){ return avaliacoes; }
}