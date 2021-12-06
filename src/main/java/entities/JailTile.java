package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JailTile extends SpecialTile {

    /**
     * Interact with the tile
     * @param token The token object that is interacting with the tile
     * @param outBound TileOutputBoundary object which allows the tile to interact with the player while following
     *                 clean architecture
     */
    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        if (token.getJailDays() > 0 && token.isInJail()){
            tokenAlreadyInJail(token, outBound);
            // Calls helper method which reduces the remaining days and asks the player if they want to pay to get
            // released early.
        } else if (token.getJailDays() == 0 && token.isInJail()) {
            outBound.notifyUser("Congratulations " + token.getPlayer().getUsername() +
                    ", you have been let out of jail! You still cannot roll this turn, however.");
            token.setInJail(false);
        }
        else{
            token.setInJail(true);
            token.setJailDays(3);
            outBound.notifyUser(token.getPlayer().getUsername() + ", you are in Jail! You will be released after " +
                    token.getJailDays() + " turns.");
        }
    }

    /**
     * Interact with the tile while the player is already in jail
     * @param token The token object that is interacting with the tile
     * @param outBound TileOutputBoundary object which allows the tile to interact with the player while following
     *                 clean architecture
     */
    private void tokenAlreadyInJail(Token token, TileOutputBoundary outBound) {
        token.setJailDays(token.getJailDays()-1);
        List<String> acceptedResponses = new ArrayList<>();
        acceptedResponses.add("y");
        acceptedResponses.add("n");

        outBound.notifyUser(token.getPlayer().getUsername() +
                ", you are still in Jail! You can pay $50 to be released.");
        String input = outBound.getResponse("Would you like to pay $50 to get out of the jail early? " +
                "Enter Y / N.", acceptedResponses);
        // Calls helper method which deals with the response of the user and prints information respectively.
        responseHandler(token, input, outBound);
    }

    /**
     * Helper method to handle response from the user
     * @param token The token object that is interacting with the tile
     * @param input The response given by the user
     * @param outBound TileOutputBoundary object which allows the tile to interact with the player while following
     *                 clean architecture
     */

    private void responseHandler(Token token, String input, TileOutputBoundary outBound) {
        Player player = token.getPlayer();
        if (input.equalsIgnoreCase("Y")){
            player.addCash(-50);
            outBound.notifyUser("Congratulations " + player.getUsername() + ", you have been let out of jail!" +
                    " We thank you for the payment.");
            token.setJailDays(0);
            token.setInJail(false);
        }
        else{
            outBound.notifyUser("You will remain in Jail for " + token.getJailDays() + " more turn(s)!");
        }
    }

}