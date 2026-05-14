import java.util.ArrayList;
import java.util.Scanner;
public class CarOne {
    public static void main (String[] args){
        int op;
        ArrayList<Passageiro> lista = new ArrayList<>();
        Passageiro passageiro = new Passageiro();
        do{
             StringBuilder sb = new StringBuilder();
             sb.append("CarONE-M\n");
             sb.append("1) Cadastrar um novo usuário\n");
             sb.append("2) Cadastrar uma viagem\n");
             sb.append("3) Buscar por carona\n");
             sb.append("4) Avaliar uma viagem\n");
             sb.append("5) Sair\n");
             sb.append("Selecione uma opção:");
             System.out.println(sb.toString());
             Scanner entrada = new Scanner(System.in);
             op = entrada.nextInt();
                switch(op){
                    case 1:
                        sb = new StringBuilder();
                        sb.append("Cadastrar um novo usuário\n");
                        sb.append("\n1 - PASSAGEIRO\n");
                        sb.append("2 - MOTORISTA\n");
                        System.out.println(sb.toString());
                        int tipoUsuario = entrada.nextInt();
                        if (tipoUsuario == 1){
                            passageiro.cadastrarPassageiro(lista);
                            break;
                        }else if(tipoUsuario == 2){
                            passageiro.mostrarPassageirosCadastrados(lista);
                            break;
                        }else{
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                        break;
                        
                    case 2:
                        System.out.println("Cadastrar uma viagem");
                        break;
                    case 3:
                        System.out.println("Buscar por carona");
                        break;
                    case 4:
                        System.out.println("Avaliar uma viagem");
                        break;

                    case 6:
                        System.out.println("Sair");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                };
        }while(op != 6);

    }
 
}
