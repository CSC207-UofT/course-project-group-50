package usecases;

import entities.ConstantsOutputBoundary;

/**
 * Class that acts as the input boundary for entities that require the BOARD_SIZE constant
 */
public class ConstantsInputBoundary implements ConstantsOutputBoundary {

    public int getBoardSize() {
        return BoardManager.BOARD_SIZE;
    }

}
