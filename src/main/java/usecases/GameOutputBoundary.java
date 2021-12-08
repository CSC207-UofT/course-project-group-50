package usecases;

import datatransferobj.PlayerDTO;
import datatransferobj.TileDTO;

import java.util.Map;

public interface GameOutputBoundary {
    void update(Map<String, TileDTO> boardData, Map<Integer, PlayerDTO> playerData);
}
