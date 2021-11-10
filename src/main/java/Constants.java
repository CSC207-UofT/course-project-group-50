import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public final class Constants {
    private Constants(){}

    // Property List
    public static final List<String> Propertylist= new ArrayList<>(Arrays.asList(
            "Mediterranean Avenue",
            "Baltic Avenue"
    ));

    // Property Dictionary {Name(String) -> ArrayList(price(int), rent(int))
    public static final Hashtable<String, ArrayList<Integer>> PropertyDetails = new Hashtable<>(){
        {
            put("Mediterranean Avenue", new ArrayList<>(Arrays.asList(60, 2)));
            put("Baltic Avenue", new ArrayList<>(Arrays.asList(60, 4)));
        }
    };

    // Property List
    public static final List<PropertyTile> properties= new ArrayList<>(){
        {
            add(new PropertyTile(Propertylist.get(0),
                    PropertyDetails.get(Propertylist.get(0)).get(0),
                    PropertyDetails.get(Propertylist.get(0)).get(1)));
            add(new PropertyTile(Propertylist.get(1),
                    PropertyDetails.get(Propertylist.get(1)).get(0),
                    PropertyDetails.get(Propertylist.get(1)).get(1)));
        }
    };


    // Upgrade Dictionary {Name(String) -> ArrayList(upgrade_cost(int), upgraded_rents(ArrayList))
    public static final Hashtable<String, ArrayList<Object>> Rents = new Hashtable<>(){
        {
            put("Mediterranean Avenue", new ArrayList<>(Arrays.asList(50,
                    new ArrayList<>(Arrays.asList(10, 30, 90, 160, 250)))));
            put("Baltic Avenue", new ArrayList<>(Arrays.asList(50,
                    new ArrayList<>(Arrays.asList(20, 60, 180, 320, 450)))));
        }
    };

    // Property Dictionary {Name(String) -> ArrayList(price(int), rent(int))
    public static final List<Tile> Board = new ArrayList<>(){
        {
            add(new StartTile());
            add(properties.get(0));
            add(new SurpriseTile());
            add(properties.get(1));

        }
    };


}
