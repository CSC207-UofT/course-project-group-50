package usecases;

import entities.City;
import entities.Player;
import entities.PropertyTile;
import entities.TileOutputBoundary;

import java.util.List;

public class TileManager implements TileOutputBoundary {
    UseCaseOutputBoundary outBound;
    BoardManager boardManager;

    public TileManager(UseCaseOutputBoundary outBound, BoardManager boardManager) {
        this.outBound = outBound;
        this.boardManager = boardManager;
    }

    public void notifyUser(String message) {
        this.outBound.notifyUser(message);
    }

    public String getResponse(String message, List<String> acceptedResponses) {
        return this.outBound.getResponse(message, acceptedResponses);
    }

    public void payRent(Player player, PropertyTile tile) {
        this.boardManager.payRent(player, tile);
    }

    public boolean buyProperty(Player player, City city) {
        return this.boardManager.buyProperty(player, city);
    }

    public void cardTwo(Player player) {
        player.addCash(100);
        player.addNetWorth(100);
    }

    public void cardThree(Player player) {
        player.subtractCash(100);
        player.subtractNetWorth(100);
    }

    public void auction(Player player) {
        this.boardManager.tileIsAuctionTile(player);
    }

    public void passStart(Player player) {
        // TODO: Use BankManager here instead
        player.addCash(200);
        player.addNetWorth(200);
    }
}
