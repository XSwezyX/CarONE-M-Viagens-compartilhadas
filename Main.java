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

                    String nome = Validador.validarNome(scanner);

                    String emailCadastro = Validador.validarEmail(scanner);

                    String telefone = Validador.validarTelefone(scanner);

                    String senhaCadastro = Validador.validarSenha(scanner);

                    String endereco = Validador.validarEndereco(scanner);

                    System.out.print("Tipo (1 - Motorista, 2 - Passageiro): ");

                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    Usuario novoUsuario = null;

                    if(tipo == 1) {
                        String modeloVeiculo = Validador.validarModeloVeiculo(scanner);
                        novoUsuario = new Motorista(
                                nome,
                                emailCadastro,
                                telefone,
                                senhaCadastro,
                                endereco,
                                modeloVeiculo
                        );
                        

                    } else if(tipo == 2) {

                        novoUsuario = new Passageiro(
                                nome,
                                emailCadastro,
                                telefone,
                                senhaCadastro,
                                endereco
                        );
                
                        
                    } else {

                        System.out.println("Tipo inválido!");
                        break;
                    }

                    sistema.cadastrarUsuario(novoUsuario);

                    System.out.println(
                            "Usuário cadastrado com sucesso!"
                    );

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
