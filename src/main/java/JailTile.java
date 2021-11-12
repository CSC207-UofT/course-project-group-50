import java.util.Objects;

public class JailTile extends SpecialTile{

    public void interact(Player player){
        if (player.getJailDays() > 0 || player.isInJail()){
            playerAlreadyInJail(player);
            // Calls helper method which reduces the remaining days and asks the player if they want to pay to get
            // released early.
        }
        else{
        player.setInJail(true);
        player.setJailDays(3);
        System.out.println(player.getUsername() + ", you are in Jail! You will be released after " +
                player.getJailDays() + " turns.");
        }
    }

    private void playerAlreadyInJail(Player player) {
        player.setJailDays(player.getJailDays()-1);
        System.out.println(player.getUsername() + ", you are still in Jail! You can pay $50 to be released.");
        String input;
        do{
            System.out.println("Would you like to pay $50 to get out of the jail early? Enter Y for yes and N for no.");
            input = CmdLineUI.scanner.nextLine();
        } while (!Objects.equals(input, "Y") && !Objects.equals(input, "N"));

        responseHandler(player, input);
        // Calls helper method which deals with the response of the user and prints information respectively.
    }

    private void responseHandler(Player player, String input) {
        if (Objects.equals(input, "Y")){
            player.addCash(-50);
            System.out.println("Congratulations " + player.getUsername() + ", you have been let out of jail!" +
                    "We thank you for the payment.");
            player.setJailDays(0);
            player.setInJail(false);
        }
        else{
            System.out.println("You will remain in Jail for " + player.getJailDays() + " more turns!");
        }
    }

}
