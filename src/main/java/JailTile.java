import java.util.Objects;

public class JailTile extends SpecialTile {

    public void interact(Token token){
        if (token.getJailDays() > 0 && token.isInJail()){
            tokenAlreadyInJail(token);
            // Calls helper method which reduces the remaining days and asks the player if they want to pay to get
            // released early.
        } else if (token.getJailDays() == 0 && token.isInJail()) {
            System.out.println("Congratulations " + token.getPlayer().getUsername() +
                    ", you have been let out of jail!");
            token.setInJail(false);
        }
        else{
            token.setInJail(true);
            token.setJailDays(3);
            System.out.println(token.getPlayer().getUsername() + ", you are in Jail! You will be released after " +
                    token.getJailDays() + " turns.");
        }
    }

    private void tokenAlreadyInJail(Token token) {
        token.setJailDays(token.getJailDays()-1);
        System.out.println(token.getPlayer().getUsername() + ", you are still in Jail! You can pay $50 to be released.");
        String input;
        do{
            System.out.println("Would you like to pay $50 to get out of the jail early? Enter Y for yes and N for no.");
            input = CmdLineUI.scanner.nextLine();
        } while (!Objects.equals(input, "Y") && !Objects.equals(input, "N"));

        responseHandler(token, input);
        // Calls helper method which deals with the response of the user and prints information respectively.
    }

    private void responseHandler(Token token, String input) {
        Player player = token.getPlayer();
        if (Objects.equals(input, "Y")){
            player.addCash(-50);
            System.out.println("Congratulations " + player.getUsername() + ", you have been let out of jail!" +
                    "We thank you for the payment.");
            token.setJailDays(0);
            token.setInJail(false);
        }
        else{
            System.out.println("You will remain in Jail for " + token.getJailDays() + " more turns!");
        }
    }

}
