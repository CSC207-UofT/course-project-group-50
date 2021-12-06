package usecases;

import entities.Player;
import entities.PropertyTile;

import java.util.ArrayList;
import java.util.Arrays;

public class TileManagerAuction {

    private final PropertyManager propertyManager;
    private final BankManager bankManager;
    private final UseCaseOutputBoundary outBound;
    private final BoardManager boardManager;

    public TileManagerAuction(PropertyManager propertyManager, BankManager bankManager,
                              UseCaseOutputBoundary outBound, BoardManager boardManager) {
        this.propertyManager = propertyManager;
        this.bankManager = bankManager;
        this.outBound = outBound;
        this.boardManager = boardManager;
    }

    public void auction(Player player1) {
        Player player2 = auctionPlayerHelper(player1);
        if(player2 != null) {
            ArrayList<Object> offer = auctionOfferHelper();
            PropertyTile offeredProperty = (PropertyTile) offer.get(0);
            PropertyTile tradeProperty = (PropertyTile) offer.get(1);
            int additionalCompensation = (int) offer.get(2);
            String response = this.outBound.getResponse(player2.getUsername() +
                    " Do you accept the trade offer from " + player1.getUsername() + " of " + offeredProperty.getName() +
                    " and " + additionalCompensation + " in exchange for " + tradeProperty.getName() +
                    "? Please enter Y / N.", new ArrayList<>(Arrays.asList("y", "n")));
            if(response.equalsIgnoreCase("N")){
                this.outBound.notifyUser("Trade offer has been denied.");
            }else{ // trade accepted
                this.outBound.notifyUser("Trade offer has been accepted!");
                propertyManager.swap_properties(offeredProperty, tradeProperty);
                bankManager.subtractMoney(player1, additionalCompensation);
                bankManager.addMoney(player2, additionalCompensation);
                bankManager.updateCashPropertySwap(player1, player2);
            }
        }
    }

    private Player auctionPlayerHelper(Player player1) {
        String player2String = this.outBound.getAnyResponse("If you would like to trade, please enter the name " +
                "of the player you wish to trade with or type N to skip.");
        if (player2String.equalsIgnoreCase("N")) {
            outBound.notifyUser(player1.getUsername() + ", you chose not to attempt a trade.");
        } else {
            // input is player name not sure if valid
            Player player2 = boardManager.getPlayerFromUsername(player2String);

            // Start again if the name entered is invalid
            if (player2 == null) {
                this.outBound.notifyUser("Invalid name entered.");
                auctionPlayerHelper(player1);
            }
            // Start again if the player trading does not have any properties
            else if (propertyManager.propertiesOwnedByPlayer(player1).size() == 0) {
                this.outBound.notifyUser("You do not own any properties to trade with!");
                auctionPlayerHelper(player1);
            }
            // Start again if the selected player does not have any properties
            else if (propertyManager.propertiesOwnedByPlayer(player2).size() == 0) {
                this.outBound.notifyUser(player2String + " does not own any properties to trade with!");
                auctionPlayerHelper(player1);
            }
            // Start again if the name entered is the trader
            else if (player1.getUsername().equals(player2.getUsername())) {
                this.outBound.notifyUser("You cannot choose yourself!");
                auctionPlayerHelper(player1);
            }
            else {
                return player2;
            }
        }
        return null;
    }

    private ArrayList<Object> auctionOfferHelper(){
        PropertyTile offeredProperty = null;
        PropertyTile tradeProperty = null;
        while(offeredProperty == null | tradeProperty == null){
            String offeredPropertyString = this.outBound.getAnyResponse(
                    "Enter name of property you would like to offer in the trade");
            offeredProperty = propertyManager.stringToPropertyTile(offeredPropertyString);
            String tradePropertyString = this.outBound.getAnyResponse(
                    "Enter name of property you would like to receive in the trade");
            tradeProperty = propertyManager.stringToPropertyTile(tradePropertyString);
        }
        int additionalCompensation = Integer.parseInt(this.outBound.getAnyResponse("How much do you offer " +
                "in addition to the property in the offer? Negative values imply you would like money."));
        return new ArrayList<>(Arrays.asList(offeredProperty, tradeProperty, additionalCompensation));
    }

}
