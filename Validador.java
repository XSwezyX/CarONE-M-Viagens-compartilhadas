import java.util.Scanner;

/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Classe auxiliar de validação de campos lidos via console.
 *
 * O Validador centraliza as regras de entrada mínima para o cadastro de usuários,
 * garantindo maior consistência e reciclabilidade do código.
 */
public class Validador {

    /**
     * Lê e valida um nome completo do usuário.
     *
     * O método repete a leitura até receber um nome não em branco com ao menos
     * três caracteres.
     */
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

    /**
     * Lê email até encontrar um formato mínimo válido.
     *
     * A validação é simples e verifica apenas a presença de '@' e '.'.
     */
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

    /**
     * Lê telefone até que seja considerado válido.
     *
     * O critério é baseado em mínimo de caracteres para evitar entradas muito curtas.
     */
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

    /**
     * Lê senha e valida tamanho mínimo.
     *
     * O sistema não usa criptografia, então a senha é armazenada em texto puro.
     * Esta classe apenas garante que o tamanho mínimo seja respeitado.
     */
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

    /**
     * Lê endereço até obter uma string não vazia e com tamanho mínimo.
     *
     * O endereço é uma informação obrigatória para todos os usuários do sistema.
     */
    public static String validarEndereco(Scanner scanner) {

        String endereco;

        do {

            System.out.print("Endereço: ");
            endereco = scanner.nextLine();

            if(endereco == null || endereco.isBlank() || endereco.length() < 5) {

                System.out.println(
                        "Endereço inválido!"
                );
            }

        } while(endereco == null || endereco.isBlank() || endereco.length() < 5);

        return endereco;
    }

    /**
     * Lê o modelo do veículo para cadastro de motorista.
     *
     * Motoristas precisam fornecer o modelo do carro para identificação.
     */
    public static String validarModeloVeiculo(Scanner scanner) {

        String modelo;

        do {

            System.out.print("Modelo do veículo: ");
            modelo = scanner.nextLine();

            if(modelo.isBlank() || modelo.length() < 3) {

                System.out.println(
                        "Modelo de veículo inválido!"
                );
            }

        } while(modelo.isBlank() || modelo.length() < 3);

        return modelo;
    }

}