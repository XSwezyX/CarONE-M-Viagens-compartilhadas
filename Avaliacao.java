/**
 *  * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA:10748053
 * Representa a avaliação entre dois usuários do sistema.
 *
 * As avaliações podem ser feitas por passageiro para motorista e por motorista
 * para passageiro, sempre com nota de 1 a 5 e comentário opcional.
 */
public class Avaliacao {

    // Usuário que fez a avaliação.
    private Usuario avaliador;
    // Usuário que está sendo avaliado.
    private Usuario avaliado;
    // Nota numérica fornecida pelo avaliador.
    private int nota; // 1 a 5
    // Comentário livre opcional.
    private String comentario; // opcional

    /**
     * Cria avaliação garantindo nota dentro do intervalo válido.
     *
     * Se a nota estiver fora do intervalo esperado, ela é ajustada para 1
     * e o sistema notifica no console.
     */
    public Avaliacao(Usuario avaliador, Usuario avaliado, int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            System.out.println("Nota inválida! Deve ser entre 1 e 5.");
            nota = 1;
        }
        this.avaliador = avaliador;
        this.avaliado = avaliado;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Getters retornam os detalhes da avaliação.
    public Usuario getAvaliador()  { return avaliador; }
    public Usuario getAvaliado()   { return avaliado; }
    public int getNota()           { return nota; }
    public String getComentario()  { return comentario; }

    /**
     * Apresenta avaliação em formato legível, incluindo comentário quando houver.
     *
     * Isso facilita o uso em listas do console e o entendimento do contexto.
     */
    @Override
    public String toString() {
        String base = avaliador.getNome() + " → " + avaliado.getNome()
                    + " | Nota: " + nota + "/5";
        if (comentario != null && !comentario.isEmpty()) {
            base += " | \"" + comentario + "\"";
        }
        return base;
    }
}