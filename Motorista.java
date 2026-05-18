import java.util.ArrayList;

public class Motorista extends Usuario {
    protected ArrayList<Usuario> motoristas;
    public Motorista(
            String nome,
            String email,
            String telefone,
            String senha
    ) {

        super(nome, email, telefone, senha);
        motoristas = new ArrayList<>();
    }
    @Override
    public void cadastrarUsuario(Usuario usuario) {
        motoristas.add(usuario);
    }
    @Override
    public void listarUsuarios() {
        listarMotoristas();
    }
    public void listarMotoristas() {

        System.out.println("\n=== Motoristas Cadastrados ===");
        for (Usuario m : motoristas) {
            m.exibirUsuario();
        }
    }
}