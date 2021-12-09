package interfaceadapters;

public interface SerializerBoundary {

    void save(GameController game, String filename);

    GameController load(String filepath);

}
