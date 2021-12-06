package main;

import interfaceadapters.GameSetUp;
import details.CmdLineUI;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        GameSetUp gameSetUp = new GameSetUp();
        CmdLineUI ui = new CmdLineUI();
        gameSetUp.setUpGame(ui);
    }


}