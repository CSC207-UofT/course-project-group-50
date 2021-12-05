package interfaceadapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains constants that give the location of the centre of each tile and a mapping from
 * tile number to the location of the tile on the board
 */
public class GameBoardConstants {
    // Locations
    protected static final int XPADDING = 60;
    protected static final int YPADDING = 60;

    protected static final int START_X = Presenter.WIDTH - XPADDING;
    protected static final int START_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int RIO_X = 6 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int RIO_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int DEHLI_X = 5 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int DELHI_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int HARBOUR_X = 4 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int HARBOUR_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int CAIRO_X = 3 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int CAIRO_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int MADRID_X = 2 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int MADRID_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int SURPRISE_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int SURPRISE_Y = Presenter.HEIGHT - Presenter.HEIGHT / 7 + YPADDING;

    protected static final int JAIL_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int JAIL_Y = YPADDING;

    protected static final int SEOUL_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int SEOUL_Y = Presenter.HEIGHT / 7 + YPADDING;

    protected static final int TORONTO_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int TORONTO_Y = 2 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int RAILWAY_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int RAILWAY_Y = 3 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int BERLIN_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int BERLIN_Y = 4 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int JAKARTA_X = Presenter.WIDTH / 7 - XPADDING;
    protected static final int JAKARTA_Y = 5 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int ZURICH_X = 2 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int ZURICH_Y = YPADDING;

    protected static final int RIYADH_X = 3 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int RIYADH_Y = YPADDING;

    protected static final int ELECTRICITY_X = 4 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int ELECTRICITY_Y = YPADDING;

    protected static final int BEIJING_X = 5 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int BEIJING_Y = YPADDING;

    protected static final int DUBAI_X = 6 * Presenter.WIDTH / 7 - XPADDING;
    protected static final int DUBAI_Y = YPADDING;

    protected static final int AUCTION_X = Presenter.WIDTH - XPADDING;
    protected static final int AUCTION_Y = YPADDING;

    protected static final int PARIS_X = Presenter.WIDTH - XPADDING;
    protected static final int PARIS_Y = Presenter.HEIGHT / 7 + YPADDING;

    protected static final int HONG_KONG_X = Presenter.WIDTH - XPADDING;
    protected static final int HONG_KONG_Y = 2 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int AIRPORT_X = Presenter.WIDTH - XPADDING;
    protected static final int AIRPORT_Y = 3 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int TOKYO_X = Presenter.WIDTH - XPADDING;
    protected static final int TOKYO_Y = 4 * Presenter.HEIGHT / 7 + YPADDING;

    protected static final int NEW_YORK_X = Presenter.WIDTH - XPADDING;
    protected static final int NEW_YORK_Y = 5 * Presenter.HEIGHT / 7 + YPADDING;

    // Hashmap mapping tile number to location
    protected static final Map<Integer, Integer[]> NUM_TO_LOC = new HashMap<>();
    // Do not remove these parentheses, they are necessary because numToLoc is static
    static {
        NUM_TO_LOC.put(0, new Integer[]{START_X, START_Y});
        NUM_TO_LOC.put(1, new Integer[]{RIO_X, RIO_Y});
        NUM_TO_LOC.put(2, new Integer[]{DEHLI_X, DELHI_Y});
        NUM_TO_LOC.put(3, new Integer[]{HARBOUR_X, HARBOUR_Y});
        NUM_TO_LOC.put(4, new Integer[]{CAIRO_X, CAIRO_Y});
        NUM_TO_LOC.put(5, new Integer[]{MADRID_X, MADRID_Y});
        NUM_TO_LOC.put(6, new Integer[]{SURPRISE_X, SURPRISE_Y});
        NUM_TO_LOC.put(7, new Integer[]{JAKARTA_X, JAKARTA_Y});
        NUM_TO_LOC.put(8, new Integer[]{BERLIN_X, BERLIN_Y});
        NUM_TO_LOC.put(9, new Integer[]{RAILWAY_X, RAILWAY_Y});
        NUM_TO_LOC.put(10, new Integer[]{TORONTO_X, TORONTO_Y});
        NUM_TO_LOC.put(11, new Integer[]{SEOUL_X, SEOUL_Y});
        NUM_TO_LOC.put(12, new Integer[]{JAIL_X, JAIL_Y});
        NUM_TO_LOC.put(13, new Integer[]{ZURICH_X, ZURICH_Y});
        NUM_TO_LOC.put(14, new Integer[]{RIYADH_X, RIYADH_Y});
        NUM_TO_LOC.put(15, new Integer[]{ELECTRICITY_X, ELECTRICITY_Y});
        NUM_TO_LOC.put(16, new Integer[]{BEIJING_X, BEIJING_Y});
        NUM_TO_LOC.put(17, new Integer[]{DUBAI_X, DUBAI_Y});
        NUM_TO_LOC.put(18, new Integer[]{AUCTION_X, AUCTION_Y});
        NUM_TO_LOC.put(19, new Integer[]{PARIS_X, PARIS_Y});
        NUM_TO_LOC.put(20, new Integer[]{HONG_KONG_X, HONG_KONG_Y});
        NUM_TO_LOC.put(21, new Integer[]{AIRPORT_X, AIRPORT_X});
        NUM_TO_LOC.put(22, new Integer[]{TOKYO_X, TOKYO_Y});
        NUM_TO_LOC.put(23, new Integer[]{NEW_YORK_X, NEW_YORK_Y});
    }
}
