/**
 * Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 * Representa uma avaliação feita por um usuário sobre outro após uma viagem compartilhada.
 *
 * <p>A avaliação consiste em uma nota obrigatória de 1 a 5 e um comentário opcional.
 * Apenas usuários que participaram da mesma viagem podem se avaliar mutuamente.</p>
 */
public class Avaliacao {

    /** Usuário que registrou a avaliação. */
    private Usuario avaliador;

    /** Usuário que recebeu a avaliação. */
    private Usuario avaliado;

    /** Nota atribuída, entre 1 (mínimo) e 5 (máximo). */
    private int nota;

    /** Comentário descritivo, pode ser nulo ou vazio quando não informado. */
    private String comentario;

    /**
     * Cria uma nova avaliação entre dois usuários.
     *
     * <p>Se a nota estiver fora do intervalo permitido (1–5), ela é ajustada
     * para o valor mínimo (1) e uma mensagem de erro é exibida ao usuário.</p>
     *
     * @param avaliador  usuário que está avaliando
     * @param avaliado   usuário que está sendo avaliado
     * @param nota       nota de 1 a 5; valores inválidos são corrigidos para 1
     * @param comentario texto livre opcional (pode ser {@code null} ou vazio)
     */
    public Avaliacao(Usuario avaliador, Usuario avaliado, int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            System.out.println("Nota inválida! Deve ser entre 1 e 5.");
            nota = 1;
        }
        this.avaliador  = avaliador;
        this.avaliado   = avaliado;
        this.nota       = nota;
        this.comentario = comentario;
    }

    // ─────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────

    /** @return usuário que registrou esta avaliação */
    public Usuario getAvaliador() { return avaliador; }

    /** @return usuário que recebeu esta avaliação */
    public Usuario getAvaliado()  { return avaliado; }

    /** @return nota atribuída (1 a 5) */
    public int getNota()          { return nota; }

    /** @return comentário opcional, podendo ser {@code null} ou vazio */
    public String getComentario() { return comentario; }

    // ─────────────────────────────────────────
    //  EXIBIÇÃO
    // ─────────────────────────────────────────

    /**
     * Retorna uma representação textual da avaliação no formato:
     * {@code NomeAvaliador → NomeAvaliado | Nota: X/5 | "comentário"}.
     * O comentário só é exibido quando presente.
     *
     * @return string formatada com os dados da avaliação
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
