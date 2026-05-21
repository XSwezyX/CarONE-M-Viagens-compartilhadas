import java.util.ArrayList;

public class Motorista extends Usuario {
    private String modeloVeiculo;
    private ArrayList<Viagem> viagens = new ArrayList<>();
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
    @Override
    public boolean ehMotorista() { return true; }

    public void adicionarViagem(Viagem v) { viagens.add(v); }
    public ArrayList<Viagem> getViagens() { return viagens; }
    public String getModeloVeiculo()      { return modeloVeiculo; }

}