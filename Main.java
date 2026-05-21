public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Menu menu = new Menu(sistema);
        menu.iniciar();
    }
}