/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Representa um ponto geográfico do sistema, usado como parada ao longo de um trajeto.
 *
 * <p>A localização é definida por coordenadas cartesianas (x, y), que simplificam
 * o cálculo de distâncias sem necessitar de geolocalização real. Cada local possui
 * também um nome de exibição e um endereço descritivo.</p>
 */
public class Local {

    /** Nome de exibição do local (ex.: "Paulista", "Vila Madalena"). */
    private String nome;

    /** Endereço textual do local (ex.: "Av. Paulista, 900"). */
    private String endereco;

    /** Coordenada X na representação cartesiana do mapa. */
    private double x;

    /** Coordenada Y na representação cartesiana do mapa. */
    private double y;

    /**
     * Cria um novo local com nome, endereço e coordenadas geográficas simplificadas.
     *
     * @param nome     nome de exibição do ponto
     * @param endereco endereço textual completo
     * @param x        posição horizontal no plano cartesiano
     * @param y        posição vertical no plano cartesiano
     */
    public Local(String nome, String endereco, double x, double y) {
        this.nome     = nome;
        this.endereco = endereco;
        this.x        = x;
        this.y        = y;
    }

    // ─────────────────────────────────────────
    //  CÁLCULO DE DISTÂNCIA
    // ─────────────────────────────────────────

    /**
     * Calcula a distância euclidiana entre este local e outro ponto do mapa.
     *
     * <p>A fórmula aplicada é: √((x₂ − x₁)² + (y₂ − y₁)²). A unidade é
     * implicitamente a mesma das coordenadas fornecidas no cadastro dos locais.</p>
     *
     * @param outro local de destino para o qual se deseja calcular a distância
     * @return distância euclidiana entre os dois pontos
     */
    public double distancia(Local outro) {
        double soma = Math.pow(outro.x - this.x, 2) + Math.pow(outro.y - this.y, 2);
        return Math.sqrt(soma);
    }

    // ─────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────

    /** @return nome de exibição do local */
    public String getNome()     { return nome; }

    /** @return endereço textual do local */
    public String getEndereco() { return endereco; }

    /** @return coordenada X no plano cartesiano */
    public double getX()        { return x; }

    /** @return coordenada Y no plano cartesiano */
    public double getY()        { return y; }

    // ─────────────────────────────────────────
    //  EXIBIÇÃO
    // ─────────────────────────────────────────

    /**
     * Retorna o local no formato {@code "Nome — Endereço"}.
     *
     * @return representação textual do local
     */
    @Override
    public String toString() {
        return nome + " — " + endereco;
    }
}
