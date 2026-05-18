import java.util.ArrayList;

public class Motorista extends Usuario {
    private String modeloVeiculo;
    public Motorista(
            String nome,
            String email,
            String telefone,
            String senha,
            String endereco,
            String modeloVeiculo
    ) {

        super(nome, email, telefone, senha, endereco);
        this.modeloVeiculo = modeloVeiculo;
    }

}