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
            int additional_compensation = (int) offer.get(2);
            String response = this.outBound.getResponse(player2.getUsername() +
                    " Do you accept the trade offer from " + player1.getUsername() + " of " + offeredProperty.getName() +
                    " and " + additional_compensation + " in exchange for " + tradeProperty.getName() +
                    "? Please enter Y / N.", new ArrayList<>(Arrays.asList("y", "n")));
            if(response.equalsIgnoreCase("N")){
                this.outBound.notifyUser("Trade offer has been denied.");
            }else{ // trade accepted
                this.outBound.notifyUser("Trade offer has been accepted!");
                propertyManager.swap_properties(offeredProperty, tradeProperty);
                bankManager.subtractMoney(player1, additional_compensation);
                bankManager.addMoney(player2, additional_compensation);
                bankManager.updateCashPropertySwap(player1, player2);
            }
        }
    }

    private Player auctionPlayerHelper(Player player1) {
        String player2String = this.outBound.getAnyResponse("If you would like to trade, please enter the name " +
                "of the player you wish to trade with or type N.");
        if (player2String.equalsIgnoreCase("N")) {
            outBound.notifyUser(player1.getUsername() + "You chose not to attempt a trade.");
        } else {
            // input is player name not sure if valid
            Player player2 = boardManager.stringToPlayer(player2String);
            if (player2 == null | propertyManager.propertiesOwnedByPlayer(player1).size() == 0 |
                    propertyManager.propertiesOwnedByPlayer(player2).size() == 0) {
                this.outBound.notifyUser("Invalid name entered.");
                auctionPlayerHelper(player1);
            } else {
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
        int additional_compensation = Integer.parseInt(this.outBound.getAnyResponse("How much do you offer " +
                "in addition to the property in the offer? Negative values imply you would like money."));
        return new ArrayList<>(Arrays.asList(offeredProperty, tradeProperty, additional_compensation));
    }

}
