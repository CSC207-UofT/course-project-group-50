package main;

import controllers.GameSetUp;
import details.CmdLineUI;


public class Main {
    public static void main(String[] args) {
        GameSetUp gameSetUp = new GameSetUp();
        CmdLineUI ui = new CmdLineUI();
        gameSetUp.setUpGame(ui);
    }


}
