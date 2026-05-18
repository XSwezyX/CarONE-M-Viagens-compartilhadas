// Sistema.java

import java.util.ArrayList;

public class Sistema {

    private ArrayList<Usuario> usuarios;

    public Sistema() {

        usuarios = new ArrayList<>();

        inicializarUsuarios();
    }

    private void inicializarUsuarios() {

        Usuario u1 = new Usuario(
                "João",
                "joao@gmail.com",
                "119996",
                "1234"
        );

        Usuario u2 = new Usuario(
                "Maria",
                "maria@gmail.com",
                "118888",
                "4567"
        );

        usuarios.add(u1);
        usuarios.add(u2);
    }

    public void cadastrarUsuario(Usuario usuario) {

        usuarios.add(usuario);
    }

    public Usuario fazerLogin(
            String email,
            String senha
    ) {

        for (Usuario u : usuarios) {

            if (
                u.getEmail().equals(email)
                &&
                u.getSenha().equals(senha)
            ) {

                return u;
            }
        }

        return null;
    }

    public void listarUsuarios() {

        System.out.println("\n=== Usuários ===");

        for (Usuario u : usuarios) {

            System.out.println(
                    "Nome: " + u.getNome()
            );

            System.out.println(
                    "Email: " + u.getEmail()
            );

            System.out.println("-------------------");
        }
    }
}