package usecases;

import entities.*;

import java.util.List;

public class TileManagerFacade implements TileOutputBoundary {
    UseCaseOutputBoundary outBound;
    BoardManager boardManager;
    BankManager bankManager;
    PropertyManager propertyManager;

    public TileManagerFacade(UseCaseOutputBoundary outBound, BoardManager boardManager,
                             BankManager bankManager, PropertyManager propertyManager) {
        this.outBound = outBound;
        this.boardManager = boardManager;
        this.bankManager = bankManager;
        this.propertyManager = propertyManager;
    }

    public void notifyUser(String message) {
        this.outBound.notifyUser(message);
    }

    public String getResponse(String message, List<String> acceptedResponses) {
        return this.outBound.getResponse(message, acceptedResponses);
    }

    public void payRent(Player player1, PropertyTile property) {
        new TileManagerPayRent(bankManager, outBound, propertyManager).payRent(player1, property);
    }

    public void upgradeProperty(Player player, City city) {
        new TileManagerUpgradeProperty(outBound).upgradeProperty(player, city);
    }

    public boolean buyProperty(Player player, PropertyTile property) {
        return new TileManagerBuyProperty(propertyManager, bankManager).buyProperty(player, property);
    }

    public void cardZero(Player player){
        player.getToken().move(1);
        boardManager.interactWithTile(boardManager.getIntFromPlayer(player));
    }

    public void cardOne(Player player){
        player.getToken().move(-1);
        boardManager.interactWithTile(boardManager.getIntFromPlayer(player));
    }

    public void cardTwo(Player player) {
        bankManager.addMoney(player, 100);
    }

    public void cardThree(Player player) {
        bankManager.subtractMoney(player, 100);
    }

    public void auction(Player player) {
        new TileManagerAuction(propertyManager, bankManager, outBound, boardManager).auction(player);
    }

    public void passStart(Player player) {
        bankManager.passStart(player);
    }
}
