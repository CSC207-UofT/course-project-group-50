package usecases;

import entities.City;
import entities.Player;
import entities.PropertyTile;

import java.util.ArrayList;
import java.util.Arrays;

public class TileManagerPayRent {

    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final UseCaseOutputBoundary outBound;

    public TileManagerPayRent(BankManager bankManager, UseCaseOutputBoundary outBound,
                              PropertyManager propertyManager) {
        this.bankManager = bankManager;
        this.propertyManager = propertyManager;
        this.outBound = outBound;
    }

    public void payRent(Player player1, PropertyTile property) {
        Player player2 = property.getOwner();
        if(player1.getCash() >= property.getRent()){
            bankManager.payRent(player2, player1, property);
            if(property instanceof City) {
                this.outBound.notifyUser(player1.getUsername() + ", you paid " +
                        property.getRent() + " to " + player2.getUsername());
            } else {
                this.outBound.notifyUser(player1.getUsername() + ", you just paid " +
                        property.getRent() + " to the city!");
            }
        }else{
            String response = noMoneyHelper(player1);
            if(response.equals("sell")){
                payRent(player1, property);
            }else{
                bankManager.payRent(player1, player2, property);
            }
        }
    }

    private String noMoneyHelper(Player player1){
        this.outBound.notifyUser(player1.getUsername() + ", you do not have enough to pay.");
        this.outBound.notifyUser("You can either sell a property or declare bankruptcy.");
        String response = this.outBound.getResponse("Please enter either \"sell\" or \"bankrupt\"",
                new ArrayList<>(Arrays.asList("sell", "bankrupt")));
        // make sure they own at least one property to sell
        if(response.equals("sell") && propertyManager.propertiesOwnedByPlayer(player1).size() != 0){
            sellHelper(player1);
            return "sell";
        }else { // input must be "bankrupt"
            bankruptPlayer(player1);
            return "bankrupt";
        }
    }

    private void sellHelper(Player player) {
        String propertyString = this.outBound.getAnyResponse("Which property would you like to sell?");
        PropertyTile propToSell = propertyManager.stringToPropertyTile(propertyString);
        if(propToSell.getOwner().equals(player)){
            sellProperty(player, propToSell);
        }else{ // Invalid input (either not owned or incorrect spelling)
            outBound.notifyUser("Invalid Input");
            sellHelper(player);
        }
    }

    public void sellProperty(Player player, PropertyTile property){
        propertyManager.sellProperty(property);
        bankManager.addSellbackOfProperty(player, property);
    }

    public void bankruptPlayer(Player player){
        player.setBankrupt();
        propertyManager.resetProperties(player);
    }


}
