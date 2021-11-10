

public class JailTile extends SpecialTile{

    public void interact(Player player){
        if (player.getJailDays() > 0 || player.isInJail()){
            player.setJailDays(-1);
            System.out.println(player.getUsername() + ", you are still in Jail! You can pay $50 to be released.");
            System.out.println("Would you like to pay $50 to get out of the jail early?");

    }
        else{
        player.setInJail();
        player.setJailDays(3);
        System.out.println(player.getUsername() + " is in Jail! You will be released after " + player.getJailDays() + " turns.");
        }
    }

}
