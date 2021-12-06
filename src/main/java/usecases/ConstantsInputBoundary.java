package usecases;

import entities.ConstantsOutputBoundary;

import java.io.Serializable;

/**
 * Class that acts as the input boundary for entities that require the BOARD_SIZE constant
 */
public class ConstantsInputBoundary implements ConstantsOutputBoundary, Serializable {

    public int getBoardSize() {
        return BoardManager.BOARD_SIZE;
    }

}
