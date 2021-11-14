import java.util.List;
import java.util.Arrays;

public class Board {
    public final List<Tile> tiles;

    public Board(){
        this.tiles = Arrays.asList(
                new StartTile(),
                new City("Rio", 100,10,"Green"),
                new City("Delhi", 100, 10,"Green"),
                new City("Bangkok",130, 15, "Blue"),
                new PublicProperty("Harbour", 100, 35),
                new City("Cairo", 150, 15, "Blue"),
                new City("Madrid", 150, 15, "Blue"),
                new SurpriseTile(),
                new City("Jakarta", 170, 20, "Pink"),
                new City("Berlin", 180, 20, "Pink"),
                new City("Moscow", 200, 30, "Yellow"),
                new PublicProperty("Railway",150, 35),
                new City("Toronto", 200, 30, "Yellow"),
                new City("Seoul", 200, 30, "Yellow"),
                new JailTile(),
                new City("Zurich", 250, 35, "Green"),
                new City("Riyadh", 250, 35, "Green"),
                new City("Sydney", 300, 40, "Brown"),
                new PublicProperty("Electricity", 200, 70),
                new City("Beijing",300,40, "Brown"),
                new City("Dubai",300,40,"Brown"),
                new AuctionTile(),
                new City("Paris", 350, 45, "Purple"),
                new City("Hong Kong", 350, 50, "Purple"),
                new City("London", 420, 70, "Red"),
                new PublicProperty("Airport", 250, 35),
                new City("Tokyo", 420, 70, "Red"),
                new City("New York", 450, 80, "Red")
        );
    }

    public Tile getTileAt(int index){
        return this.tiles.get(index);
    }

}
