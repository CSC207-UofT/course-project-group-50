package interfaceadapters;

import java.util.List;

// Interface for UI that allows us to inject our command line UI into this class
public interface UI {
    String getStartInfo();

    List<String> getPlayerNames();

    void printMessage(String message);


    /**
     * Return an input in the form of a String from the user
     *
     * @param message The prompt you want the user to respond to
     */
    String getAnyInput(String message);

    /**
     * Get an input from the user.
     * Preconditions: - acceptedResponse is a list of lowercase Strings
     *
     * @param message           The message to display to the user
     * @param acceptedResponses List of responses we want to allow the user to respond with
     * @return The response entered by the user
     * <p>
     * Postconditions: - the returned String is in acceptedResponses
     */
    String getInput(String message, List<String> acceptedResponses);

    /**
     * Get an input from the user.
     * Preconditions: - acceptedResponse is a list of lowercase Strings
     *
     * @param messages          The messages to display to the user
     * @param acceptedResponses List of responses we want to allow the user to respond with
     * @return The response entered by the user
     * <p>
     * Postconditions: - each message is displayed to the user in a new line
     * - the returned String is in acceptedResponses
     */
    String getInput(List<String> messages, List<String> acceptedResponses);
}
