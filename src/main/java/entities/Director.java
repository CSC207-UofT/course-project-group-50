package entities;

import java.util.Arrays;

public class Director {
    private BoardBuilder builder;

    /* The main benefit of using a Builder pattern is for extensible design.
        We could create many board "themes" (ie. UofT board, regular, Halloween),
        each with different "city" names, different arrangement of tiles, etc. */

    /* In future versions, should edit the Builder pattern to direct different elements of game setup
        Right now, this is primarily a proof of concept.
        More sophisticated methods will be added as we settle on data structs for storing/reading/handling board data
            1. change the interface for entities.City, so that the entities.BoardBuilder manages the colour blocks
            separately
            2. perhaps expanding to a GameBuilder, managing player setup, token setup, etc */

    /**
     * Directs builder to create a regular Monopoly board, ie. the default themes and cities
     * @param builder the Builder object that will assemble the board
     */
    public void makeRegularBoard(BoardBuilder builder) {
        builder.reset();
        builder.createTiles(Arrays.asList(
                new StartTile(),
                new City("Rio", 100,10,"Green"),
                new City("Delhi", 100, 10,"Green"),
                new PublicProperty("Harbour", 100, 35),
                new City("Cairo", 150, 15, "Blue"),
                new City("Madrid", 150, 15, "Blue"),
                new SurpriseTile(),
                new City("Jakarta", 170, 20, "Pink"),
                new City("Berlin", 180, 20, "Pink"),
                new PublicProperty("Railway",150, 35),
                new City("Toronto", 200, 30, "Yellow"),
                new City("Seoul", 200, 30, "Yellow"),
                new JailTile(),
                new City("Zurich", 250, 35, "Green"),
                new City("Riyadh", 250, 35, "Green"),
                new PublicProperty("Electricity", 200, 70),
                new City("Beijing",300,40, "Brown"),
                new City("Dubai",300,40,"Brown"),
                new AuctionTile(),
                new City("Paris", 350, 45, "Purple"),
                new City("Hong Kong", 350, 50, "Purple"),
                new PublicProperty("Airport", 250, 35),
                new City("Tokyo", 420, 70, "Red"),
                new City("New York", 450, 80, "Red")
        ));
    }
}
