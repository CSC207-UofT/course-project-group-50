package usecases;

import datatransfer.PlayerData;
import datatransfer.TileData;

import java.util.List;
import java.util.Map;

public interface GameOutputBoundary {
    public void update(Map<String, TileData> boardData, Map<Integer, PlayerData> playerData);
}
