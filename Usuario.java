// Usuario.java

public abstract class Usuario {

    protected String nome;
    protected String email;
    protected String telefone;
    protected String senha;

    public Usuario(
            String nome,
            String email,
            String telefone,
            String senha
    ) {

        
        if(nome != null && !nome.isEmpty() && nome.length() >= 3) {
            this.nome = nome;
        }

        if(email != null && email.contains("@") && email.contains(".") && email.length() >= 5) {
            this.email = email;
        }
        if(telefone != null && !telefone.isEmpty() && telefone.length() >= 6) {
            this.telefone = telefone;
        }
        if(senha != null && senha.length() >= 4) {
            this.senha = senha;
        }
    }

    

    // GETTERS

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }
    // EXIBIR DADOS

    public void exibirUsuario() {

        System.out.println("\n=== Usuário ===");

        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Telefone: " + telefone);
    }
}