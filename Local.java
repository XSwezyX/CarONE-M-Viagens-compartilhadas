public class Local {
    // Local com nome, endereço e coordenadas no plano.
    private String nome;
    private String endereco;
    private double x;
    private double y;

    /**
     * Cria um local a partir de nome, endereço e posição.
     */
    public Local(String nome, String endereco, double x, double y) {
        this.nome = nome;
        this.endereco = endereco;
        this.x = x;
        this.y = y;
    }

    /**
     * Calcula a distância euclidiana entre este local e outro local.
     */
    public double distancia(Local outro) {
        double soma = Math.pow(outro.x - this.x, 2) + Math.pow(outro.y - this.y, 2);
        return Math.sqrt(soma);
    }

    public String getNome()     { return nome; }
    public String getEndereco() { return endereco; }
    public double getX()        { return x; }
    public double getY()        { return y; }

    /**
     * Retorna descrição simples do local.
     */
    @Override
    public String toString() {
        return nome + " — " + endereco;
    }
}
