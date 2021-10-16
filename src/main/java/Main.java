public class Main {
    public static void main(String[] args) {
        GameController gc = new GameController();
        CmdLineUI ui = new CmdLineUI();
        ui.runPlayerSetup(gc);
    }
}
