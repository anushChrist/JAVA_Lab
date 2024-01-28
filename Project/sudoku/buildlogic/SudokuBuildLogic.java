package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.problemdomain.SudokuGame;
import sudoku.storage.IStorage;
import sudoku.storage.CStorage;
import sudoku.userinterface.IUserInterface;
import sudoku.userinterface.CEventListener;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterface.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new CStorage();

        try {
            initialState = storage.getGameData(); // if already exists
        } catch (IOException e) { // if doesn't
            initialState = GameLogic.getNewGame(); // new game
            storage.updateGameData(initialState); // stored
        }

        IUserInterface.EventListener uiLogic = new CEventListener(storage, userInterface);

        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
