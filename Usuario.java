// Usuario.java

public class Usuario {

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

        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
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

    // SETTERS

    public void setNome(String nome) {

        if(nome != null && !nome.isEmpty() && nome.length() >= 3) {
            this.nome = nome;
        }
    }

    public void setEmail(String email) {

        if(email.contains("@") && email.contains(".") && email.length() >= 5) {
            this.email = email;
        }
    }

    public void setTelefone(String telefone) {

        if(telefone != null && !telefone.isEmpty() && telefone.length() >= 6) {
            this.telefone = telefone;
        }
    }

    public void setSenha(String senha) {

        if(senha.length() >= 4) {
            this.senha = senha;
        }
    }

    // EXIBIR DADOS

    public void exibirUsuario() {

        System.out.println("\n=== Usuário ===");

        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Telefone: " + telefone);
    }
}