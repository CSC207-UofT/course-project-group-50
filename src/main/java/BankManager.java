import java.io.Serializable;

public class BankManager implements Serializable {

    public void payRent(Player renter, Player payee, PropertyTile property){
        int rent = property.getRent();

        payee.subtractCash(rent);
        payee.subtractNetWorth(rent);

        renter.addCash(rent);
        renter.addNetWorth(rent);
    }

    public void deductCostOfProperty(Player buyer, PropertyTile property){
        buyer.subtractCash(property.getPrice());
        buyer.subtractNetWorth(property.getPrice());
    }

    public void addSellbackOfProperty(Player seller, PropertyTile property) {
        seller.addCash(property.getSalePrice());
        // net worth goes down the loss the seller incurs
        seller.subtractNetWorth(property.getPrice() - property.getSalePrice());
    }

    public void passStart(Player player) {

    }

    public void calculateNetWorth(){
        // TODO decide if we need this as it will be updated with everything that affects it
    }
}