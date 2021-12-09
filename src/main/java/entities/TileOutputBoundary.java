package entities;

import java.util.List;

public interface TileOutputBoundary {
    /**
     * Notify the user of some information
     *
     * @param message The information you want to notify the user of
     */
    void notifyUser(String message);

    /**
     * Get a response from a user.
     * Preconditions: - acceptedResponse is a list of lowercase Strings
     *
     * @param message           The message to display to the user
     * @param acceptedResponses List of responses we want to allow the user to respond with
     * @return The response entered by the user
     * <p>
     * Postconditions: - the returned String is in acceptedResponses
     */
    String getResponse(String message, List<String> acceptedResponses);

    void payRent(Player player, PropertyTile property);

    boolean buyProperty(Player player, PropertyTile property);

    void cardTwo(Player player);

    void cardThree(Player player);

    void auction(Player player);

    void passStart(Player player);

    void upgradeProperty(Player player, City city);

}
