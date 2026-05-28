/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Representa um ponto geográfico utilizado no sistema de caronas.
 *
 * Cada local possui nome, endereço e coordenadas bidimensionais simples
 * que permitem calcular distâncias e escolher pontos de embarque/desembarque.
 */
public class Local {
    // Nome e descrição textual do local.
    private String nome;
    private String endereco;
    // Coordenadas no plano cartesiano, usadas apenas para estimativas de distância.
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
     *
     * Esta métrica é usada para estimar se um ponto está próximo o suficiente
     * para ser considerado ponto de embarque ou desembarque.
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
