import java.util.ArrayList;

public class Passageiro extends Usuario {
    protected ArrayList<Usuario> passageiros;

    public Passageiro(
            String nome,
            String email,
            String telefone,
            String senha
    ) {

        super(nome, email, telefone, senha);
        passageiros = new ArrayList<>();
    }
    @Override
    public void cadastrarUsuario(Usuario usuario) {
        passageiros.add(usuario);
    }
    @Override
    public void listarUsuarios() {
        listarPassageiros();
    }
    public void listarPassageiros() {

        System.out.println("\n=== Passageiros Cadastrados ===");
        for (Usuario p : passageiros) {
            p.exibirUsuario();
        }
    }

}