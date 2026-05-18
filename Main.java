// Main.java

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Sistema sistema = new Sistema();

        Usuario usuarioLogado = null;

        // LOGIN
        while (usuarioLogado == null) {

            System.out.println("\n=== LOGIN ===");

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            usuarioLogado = sistema.fazerLogin(email, senha);

            if (usuarioLogado == null) {
                System.out.println("Email ou senha inválidos!");
            }
        }

        System.out.println(
                "\nBem-vindo, "
                + usuarioLogado.getNome()
                + "!"
        );

        int opcao = 0;

        while (opcao != 5) {

            System.out.println("\n=== CarONE-M ===");
            System.out.println("1 - Cadastrar novo usuário");
            System.out.println("2 - Entrar como motorista");
            System.out.println("3 - Entrar como passageiro");
            System.out.println("4 - Listar usuários");
            System.out.println("5 - Sair");

            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:

                    System.out.println("\n=== Cadastro de Usuário ===");

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();

                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();

                    Usuario novoUsuario = new Usuario(
                            nome,
                            email,
                            telefone,
                            senha
                    );

                    sistema.cadastrarUsuario(novoUsuario);

                    System.out.println("Usuário cadastrado!");
                    break;

                case 2:

                    System.out.println(
                            usuarioLogado.getNome()
                            + " entrou como MOTORISTA"
                    );

                    break;

                case 3:

                    System.out.println(
                            usuarioLogado.getNome()
                            + " entrou como PASSAGEIRO"
                    );

                    break;

                case 4:

                    sistema.listarUsuarios();

                    break;

                case 5:

                    System.out.println("Sistema encerrado.");
                    break;

                default:

                    System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }
}