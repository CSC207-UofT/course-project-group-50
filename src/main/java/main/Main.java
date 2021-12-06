package main;

import details.GameStateSerializer;
import interfaceadapters.GameSetUp;
import details.CmdLineUI;
import interfaceadapters.SerializerBoundary;


public class Main {
    public static void main(String[] args) {
        CmdLineUI ui = new CmdLineUI();
        SerializerBoundary serializerBoundary = new GameStateSerializer();
        GameSetUp gameSetUp = new GameSetUp(ui, serializerBoundary);
        gameSetUp.setUpGame();
    }

}