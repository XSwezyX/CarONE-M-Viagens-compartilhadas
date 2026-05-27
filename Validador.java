import java.util.Scanner;

/*
 Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 Utilitário estático responsável por ler e validar os dados de entrada do usuário.

 <p>Cada método mantém um laço de repetição que solicita o dado ao usuário até que
 o valor digitado atenda às regras estabelecidas. Isso evita que informações
 inválidas cheguem às classes de domínio.</p>

 <p>Todos os métodos são estáticos e não dependem de instância, podendo ser
 chamados diretamente como Validador.validarNome(scanner).</p>

*/
public class Validador {

/*
 Lê e valida um nome do console.

 <p>Critérios: não vazio e com pelo menos 3 caracteres.</p>


*/
    public static String validarNome(Scanner scanner) {
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            if (nome.isBlank() || nome.length() < 3) {
                System.out.println("Nome inválido! Deve ter pelo menos 3 caracteres.");
            }
        } while (nome.isBlank() || nome.length() < 3);
        return nome;
    }

/*
 Lê e valida um endereço de e-mail do console.

 <p>Critérios: deve conter o caractere '@' e um ponto '.'.</p>


*/
    public static String validarEmail(Scanner scanner) {
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Email inválido! Deve conter '@' e '.'.");
            }
        } while (!email.contains("@") || !email.contains("."));
        return email;
    }

/*
 Lê e valida um número de telefone do console.

 <p>Critérios: não vazio e com pelo menos 8 caracteres.</p>


*/
    public static String validarTelefone(Scanner scanner) {
        String telefone;
        do {
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            if (telefone.isBlank() || telefone.length() < 8) {
                System.out.println("Telefone inválido! Deve ter pelo menos 8 dígitos.");
            }
        } while (telefone.isBlank() || telefone.length() < 8);
        return telefone;
    }

/*
 Lê e valida uma senha do console.

 <p>Critérios: deve ter pelo menos 4 caracteres.</p>


*/
    public static String validarSenha(Scanner scanner) {
        String senha;
        do {
            System.out.print("Senha: ");
            senha = scanner.nextLine();
            if (senha.length() < 4) {
                System.out.println("Senha inválida! Deve ter pelo menos 4 caracteres.");
            }
        } while (senha.length() < 4);
        return senha;
    }

/*
 Lê e valida um endereço residencial do console.

 <p>Critérios: não nulo, não vazio e com pelo menos 5 caracteres.</p>


*/
    public static String validarEndereco(Scanner scanner) {
        String endereco;
        do {
            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            if (endereco == null || endereco.isBlank() || endereco.length() < 5) {
                System.out.println("Endereço inválido! Deve ter pelo menos 5 caracteres.");
            }
        } while (endereco == null || endereco.isBlank() || endereco.length() < 5);
        return endereco;
    }

/*
 Lê e valida o modelo do veículo do motorista.

 <p>Critérios: não vazio e com pelo menos 3 caracteres.</p>


*/
    public static String validarModeloVeiculo(Scanner scanner) {
        String modelo;
        do {
            System.out.print("Modelo do veículo: ");
            modelo = scanner.nextLine();
            if (modelo.isBlank() || modelo.length() < 3) {
                System.out.println("Modelo de veículo inválido! Deve ter pelo menos 3 caracteres.");
            }
        } while (modelo.isBlank() || modelo.length() < 3);
        return modelo;
    }
}
