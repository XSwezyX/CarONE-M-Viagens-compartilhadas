public class Avaliacao {

    private Usuario avaliador;
    private Usuario avaliado;
    private int nota; // 1 a 5
    private String comentario; // opcional

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

    public Usuario getAvaliador()  { return avaliador; }
    public Usuario getAvaliado()   { return avaliado; }
    public int getNota()           { return nota; }
    public String getComentario()  { return comentario; }

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