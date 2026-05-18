import java.util.Scanner;

public class Validador {

    public static String validarNome(Scanner scanner) {

        String nome;

        do {

            System.out.print("Nome: ");
            nome = scanner.nextLine();

            if(nome.isBlank() || nome.length() < 3) {

                System.out.println(
                        "Nome inválido!"
                );
            }

        } while(nome.isBlank() || nome.length() < 3);

        return nome;
    }

    public static String validarEmail(Scanner scanner) {

        String email;

        do {

            System.out.print("Email: ");
            email = scanner.nextLine();

            if(!email.contains("@") ||
               !email.contains(".")) {

                System.out.println(
                        "Email inválido!"
                );
            }

        } while(!email.contains("@") ||
                !email.contains("."));

        return email;
    }

    public static String validarTelefone(Scanner scanner) {

        String telefone;

        do {

            System.out.print("Telefone: ");
            telefone = scanner.nextLine();

            if(telefone.isBlank() ||
               telefone.length() < 8) {

                System.out.println(
                        "Telefone inválido!"
                );
            }

        } while(telefone.isBlank() ||
                telefone.length() < 8);

        return telefone;
    }

    public static String validarSenha(Scanner scanner) {

        String senha;

        do {

            System.out.print("Senha: ");
            senha = scanner.nextLine();

            if(senha.length() < 4) {

                System.out.println(
                        "Senha inválida!"
                );
            }

        } while(senha.length() < 4);

        return senha;
    }
}