import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Sistema sistema = new Sistema();
    static Usuario logado = null;
    // PONTO DE ENTRADA
    public static void main(String[] args) {

        telaLogin();

        

    // LOGIN
    static void telaLogin() {

        while (logado == null) {

            System.out.println("Sistema de Viagens Compartilhadas");

            System.out.println("Usuários de teste:");
            System.out.println("Motorista  → joao@gmail.com / senha: 1234");
            System.out.println("Passageiro → maria@gmail.com / senha: 4567");

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Senha: ");
            String senha = scanner.nextLine().trim();

            logado = sistema.fazerLogin(email, senha);

            if (logado == null) {

                System.out.println("Email ou senha incorretos.");
            }
        }

        System.out.println("Bem-vindo(a), " + logado.getNome() + "!");
    }

    // ESCOLHER PERFIL
    static int telaPerfil() {

        System.out.println("Como deseja entrar?");
        System.out.println("1 - Motorista");
        System.out.println("2 - Passageiro");
        System.out.println("0 - Sair");

        System.out.print("Escolha: ");

        return Integer.parseInt(scanner.nextLine());
    }

    // MENU MOTORISTA
    static void menuMotorista(Motorista motorista) {

        boolean continuar = true;

        while (continuar) {

            System.out.println("MOTORISTA - " + motorista.getNome());

            System.out.println("1 - Cadastrar nova viagem");
            System.out.println("2 - Ver passageiros");
            System.out.println("3 - Ver avaliações");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            switch (Integer.parseInt(scanner.nextLine())) {


                case 1:
                    cadastrarViagem(motorista);
                    break;

                case 2:
                    verPassageirosDasViagens(motorista);
                    break;

                case 3:
                    verAvaliacoes(motorista);
                    break;

                case 0:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // CADASTRAR VIAGEM
    static void cadastrarViagem(Motorista motorista) {

        System.out.println("CADASTRAR NOVA VIAGEM");

    }

    // VER PASSAGEIROS
    static void verPassageirosDasViagens(Motorista motorista) {

        System.out.println("PASSAGEIROS DAS VIAGENS");

    }
    // VER AVALIAÇÕES
    static void verAvaliacoes(Motorista motorista) {

        System.out.println("MINHAS AVALIAÇÕES");

    }

    // MENU PASSAGEIRO
    static void menuPassageiro(Passageiro passageiro) {

        boolean continuar = true;

        while (continuar) {

            System.out.println("PASSAGEIRO - " + passageiro.getNome());

            System.out.println("1 - Buscar viagens");
            System.out.println("2 - Pedir carona");
            System.out.println("3 - Avaliar viagem");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            switch (Integer.parseInt(scanner.nextLine())) {


                case 1:
                    buscarViagens();
                    break;

                case 2:
                    pedirCarona(passageiro);
                    break;

                case 3:
                    avaliarViagem(passageiro);
                    break;

                case 0:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // BUSCAR VIAGENS
    static void buscarViagens() {

        System.out.println("VIAGENS DISPONÍVEIS");
    }

    // PEDIR CARONA
    static void pedirCarona(Passageiro passageiro) {

        System.out.println("PEDIR CARONA");
    }

    // AVALIAR VIAGEM
    static void avaliarViagem(Passageiro passageiro) {

        System.out.println("AVALIAR VIAGEM");

    }