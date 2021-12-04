package usecases;

import entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileManager implements TileOutputBoundary {
    UseCaseOutputBoundary outBound;
    BoardManager boardManager;
    BankManager bankManager;
    PropertyManager propertyManager;

    public TileManager(UseCaseOutputBoundary outBound, BoardManager boardManager,
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
        Player player2 = property.getOwner();
        if(player1.getCash() >= property.getRent()){
            bankManager.payRent(player2, player1, property);
            if(property instanceof City) {
                notifyUser(player1.getUsername() + ", you paid " + property.getRent() + " to " + player2.getUsername());
            } else {
                notifyUser(player1.getUsername() + ", you just paid " + property.getRent() + " to the city!");
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

    public void upgradeProperty(Player player, City city){
        int upgrade_cost = (int) Math.round(city.getPrice() * 0.4);
        if(player.getCash() >= upgrade_cost){
            Building upgradeBuilding = new Building(upgrade_cost, city.getRent());
            player.subtractCash(upgrade_cost);
            city.addBuilding(upgradeBuilding);
            city.setPrice(city.getPrice() + upgrade_cost);
            city.setRent(city.getRent() * 2);
            this.outBound.notifyUser("Property has been upgraded, new rent is " + city.getRent());
        }else{
            this.outBound.notifyUser(player.getUsername() + ", you do not have enough to pay.");
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
        propertyManager.sellProperty(player, property);
        bankManager.addSellbackOfProperty(player, property);
    }

    public void bankruptPlayer(Player player){
        player.setBankrupt();
        propertyManager.resetProperties(player);
    }

    public boolean buyProperty(Player player, PropertyTile property) {
        if(player.getCash() >= property.getPrice()){
            propertyManager.buyProperty(player, property);
            bankManager.deductCostOfProperty(player, property);
            return true;
        }
        return false;
    }

    public void cardTwo(Player player) {
        player.addCash(100);
        player.addNetWorth(100);
    }

    public void cardThree(Player player) {
        player.subtractCash(100);
        player.subtractNetWorth(100);
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

    public void passStart(Player player) {
        bankManager.passStart(player);
    }
}
