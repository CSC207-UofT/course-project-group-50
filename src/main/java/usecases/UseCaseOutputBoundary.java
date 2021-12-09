package usecases;

import java.util.List;

public interface UseCaseOutputBoundary {

    /**
     * Return a response in the form of a String from the user
     *
     * @param message The prompt you want the user to respond to
     */
    String getAnyResponse(String message);

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

    /**
     * Get a response from a user.
     * Preconditions: - acceptedResponse is a list of lowercase Strings
     *
     * @param messages          The messages to display to the user
     * @param acceptedResponses List of responses we want to allow the user to respond with
     * @return The response entered by the user
     * <p>
     * Postconditions: - each message is displayed in a new line
     * - the returned String is in acceptedResponses
     */
    String getResponse(List<String> messages, List<String> acceptedResponses);

}
