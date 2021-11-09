public interface Buyable {

    int getPrice();
    int getSalePrice();
    void purchase(Player buyer);
    void sell();
}
