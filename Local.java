public class Local {
    private String nome;
    private String endereco;
    private double x;
    private double y;

    public Local(String nome, String endereco, double x, double y) {
        this.nome = nome;
        this.endereco = endereco;
        this.x = x;
        this.y = y;
    }

    public double distancia(Local outro) {
        double soma = Math.pow(outro.x - this.x, 2) + Math.pow(outro.y - this.y, 2);
        return Math.sqrt(soma);
    }

    public String getNome()     { return nome; }
    public String getEndereco() { return endereco; }
    public double getX()        { return x; }
    public double getY()        { return y; }

    @Override
    public String toString() {
        return nome + " — " + endereco;
    }
}
