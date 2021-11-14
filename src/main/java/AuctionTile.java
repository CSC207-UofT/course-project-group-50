import java.io.Serializable;

public class AuctionTile extends SpecialTile implements Serializable {

    public void interact(Player player1, Player player2, PropertyManager propertymanager) {
        propertymanager.tradeProperties(player1, player2);
    }
}
