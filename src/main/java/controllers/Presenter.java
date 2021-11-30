package controllers;

import usecases.BankManager;
import usecases.BoardManager;
import usecases.PropertyManager;

public class Presenter {
    // Methods in this will be called from game loop to output to user
    private final BoardManager boardManager;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;

    private Presenter(){
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
        this.boardManager = new BoardManager(this.bankManager, this.propertyManager);
    }


}
