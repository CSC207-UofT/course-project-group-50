public interface Buyable {

    public int getPrice();
    public int getSalePrice();
    public void purchase(Player buyer);
    public void sell();
}
