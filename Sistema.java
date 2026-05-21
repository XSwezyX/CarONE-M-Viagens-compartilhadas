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

        // Usuários
        Motorista joao  = new Motorista("João Silva",  "joao@gmail.com",
                "11999991111", "1234", "Rua A, 10", "Honda Civic");
        Motorista pedro = new Motorista("Pedro Lima",  "pedro@gmail.com",
                "11988882222", "1234", "Rua B, 20", "VW Gol");
        Passageiro maria = new Passageiro("Maria Souza", "maria@gmail.com",
                "11977773333", "1234", "Rua C, 30");
        Passageiro ana  = new Passageiro("Ana Costa",  "ana@gmail.com",
                "11966664444", "1234", "Rua D, 40");

        usuarios.add(joao);
        usuarios.add(pedro);
        usuarios.add(maria);
        usuarios.add(ana);

        // Atalhos para os locais
        Local paulista   = locais.get(0);
        Local pinheiros  = locais.get(1);
        Local vilaMad    = locais.get(2);
        Local itaim      = locais.get(3);
        Local moema      = locais.get(4);
        Local consolacao = locais.get(5);
        Local santana    = locais.get(6);
        Local tatuape    = locais.get(7);

        // ── Viagem concluída 1 — João levou Maria
        ArrayList<Local> t1 = new ArrayList<>();
        t1.add(paulista);
        t1.add(itaim);
        t1.add(moema);
        Viagem v1 = new Viagem(joao, t1, 3, true);
        Reserva r1 = new Reserva(maria, v1, paulista, moema);
        v1.adicionarReserva(r1);
        maria.adicionarReserva(r1);
        v1.concluir();
        viagens.add(v1);
        joao.adicionarViagem(v1);

        Avaliacao av1 = new Avaliacao(maria, joao, 5, "Motorista muito atencioso!");
        avaliacoes.add(av1);
        joao.receberAvaliacao(av1);
        v1.registrarAvaliacao(maria);

        // ── Viagem concluída 2 — Pedro levou Ana
        ArrayList<Local> t2 = new ArrayList<>();
        t2.add(santana);
        t2.add(consolacao);
        t2.add(paulista);
        Viagem v2 = new Viagem(pedro, t2, 2, true);
        Reserva r2 = new Reserva(ana, v2, santana, paulista);
        v2.adicionarReserva(r2);
        ana.adicionarReserva(r2);
        v2.concluir();
        viagens.add(v2);
        pedro.adicionarViagem(v2);

        Avaliacao av2 = new Avaliacao(ana, pedro, 4, "Boa viagem, um pouco atrasado.");
        avaliacoes.add(av2);
        pedro.receberAvaliacao(av2);
        v2.registrarAvaliacao(ana);

        // ── Viagem agendada 1 — João aceita passageiros
        ArrayList<Local> t3 = new ArrayList<>();
        t3.add(vilaMad);
        t3.add(pinheiros);
        t3.add(itaim);
        Viagem v3 = new Viagem(joao, t3, 2, true);
        viagens.add(v3);
        joao.adicionarViagem(v3);

        // ── Viagem agendada 2 — Pedro não aceita passageiros
        ArrayList<Local> t4 = new ArrayList<>();
        t4.add(tatuape);
        t4.add(paulista);
        t4.add(moema);
        Viagem v4 = new Viagem(pedro, t4, 3, false);
        viagens.add(v4);
        pedro.adicionarViagem(v4);
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