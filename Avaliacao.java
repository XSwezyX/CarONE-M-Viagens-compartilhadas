/*
 Henrique Haramaki Mataveli RA:10752924 Moabe Guedes RA: 10748053
 Representa uma avaliação feita por um usuário sobre outro após uma viagem compartilhada.

 A avaliação consiste em uma nota obrigatória de 1 a 5 e um comentário opcional.
 Apenas usuários que participaram da mesma viagem podem se avaliar mutuamente.
*/
public class Avaliacao {

    // Usuário que registrou a avaliação.
    private Usuario avaliador;

    // Usuário que recebeu a avaliação.
    private Usuario avaliado;

    // Nota atribuída, entre 1 (mínimo) e 5 (máximo).
    private int nota;

    // Comentário descritivo, pode ser nulo ou vazio quando não informado.
    private String comentario;

    /*
     Cria uma nova avaliação entre dois usuários.

     Se a nota estiver fora do intervalo permitido (1–5), ela é ajustada
     para o valor mínimo (1) e uma mensagem de erro é exibida.

     avaliador   usuário que está avaliando
     avaliado    usuário que está sendo avaliado
     nota        nota de 1 a 5; valores inválidos são corrigidos para 1
     comentario  texto livre opcional (pode ser null ou vazio)
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

    // retorna o usuário que registrou esta avaliação
    public Usuario getAvaliador() { return avaliador; }

    // retorna o usuário que recebeu esta avaliação
    public Usuario getAvaliado()  { return avaliado; }

    // retorna a nota atribuída (1 a 5)
    public int getNota()          { return nota; }

    // retorna o comentário opcional, que pode ser null ou vazio
    public String getComentario() { return comentario; }

    // ─────────────────────────────────────────
    //  EXIBIÇÃO
    // ─────────────────────────────────────────

    // retorna uma representação textual da avaliação
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
