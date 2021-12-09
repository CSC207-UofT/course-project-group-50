package entities;

import java.util.Arrays;

public class Director {
    private BoardBuilder builder;

    /* The main benefit of using a Builder pattern is for extensible design in future versions.
        We could create many board "themes" (ie. UofT board, regular, Halloween),
        each with different "city" names, different arrangement of tiles, etc. */

    /**
     * Directs builder to create a regular Monopoly board, ie. the default themes and cities
     *
     * @param builder the Builder object that will assemble the board
     */
    public void makeRegularBoard(BoardBuilder builder) {
        builder.reset();
        builder.createTiles(Arrays.asList(
                new StartTile(),
                new City("Rio", 100, 10, 1),
                new City("Delhi", 100, 10, 1),
                new City("Bangkok", 150, 15, 2),
                new PublicProperty("Harbour", 100, 35),
                new City("Cairo", 150, 15, 2),
                new City("Madrid", 150, 15, 2),
                new SurpriseTile(),
                new City("Jakarta", 180, 20, 3),
                new City("Berlin", 180, 20, 3),
                new City("Moscow", 200, 30, 4),
                new PublicProperty("Railway", 150, 35),
                new City("Toronto", 200, 30, 4),
                new City("Seoul", 200, 30, 4),
                new JailTile(),
                new City("Zurich", 250, 35, 5),
                new City("Riyadh", 250, 35, 5),
                new City("Sydney", 250, 35, 5),
                new PublicProperty("Electricity", 200, 70),
                new City("Beijing", 300, 40, 6),
                new City("Dubai", 300, 40, 6),
                new AuctionTile(),
                new City("Paris", 300, 40, 6),
                new City("Hong Kong", 350, 50, 7),
                new City("London", 350, 50, 7),
                new PublicProperty("Airport", 250, 35),
                new City("Tokyo", 350, 50, 7),
                new City("New York", 350, 50, 7))
        );
    }
}