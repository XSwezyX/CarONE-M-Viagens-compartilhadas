import java.util.ArrayList;
import java.util.Scanner;

public class Passageiro extends Usuario {
    

    public Passageiro() {
        super("", "", "", "", "");
    }
    public Passageiro(String nome, String email, String senha, String telefone, String endereco) {
        super(nome, email, senha, telefone, endereco);
    }
    
    public void cadastrarPassageiro(ArrayList<Passageiro> lista){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite seu nome:");
        String nome = entrada.nextLine();
        System.out.println("Digite seu email:");
        String email = entrada.nextLine();
        System.out.println("Digite sua senha:");
        String senha = entrada.nextLine();
        System.out.println("Digite seu telefone:");
        String telefone = entrada.nextLine();
        System.out.println("Digite seu endereço:");
        String endereco = entrada.nextLine();

        Passageiro passageiro = new Passageiro(nome, email, senha, telefone, endereco);
        // Aqui você pode adicionar o passageiro a uma lista ou banco de dados
        lista.add(passageiro);

    }

    public void mostrarPassageirosCadastrados(ArrayList<Passageiro> lista){
        for (Passageiro passageiro : lista) {
            StringBuilder sb = new StringBuilder();
            sb.append("Nome: ").append(passageiro.getNome()).append("\n"); 
            sb.append("Email: ").append(passageiro.getEmail()).append("\n");
            sb.append("Telefone: ").append(passageiro.getTelefone()).append("\n");
            sb.append("Endereço: ").append(passageiro.getEndereco()).append("\n");
            System.out.println(sb.toString());
        }
    }
    
}
