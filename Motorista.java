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

}