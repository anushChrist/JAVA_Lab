package sudoku.userinterface;

import sudoku.computationlogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.Players;
import sudoku.problemdomain.SudokuGame;
import sudoku.storage.IStorage;

import java.io.IOException;
import java.util.List;

public class CEventListener implements IUserInterface.EventListener {

    // communicating using interfaces
    private IStorage storage;

    private IUserInterface.View view;

    private String currentUser;

    public CEventListener(IStorage storage, IUserInterface.View view) {
        this.storage = storage;
        this.view = view;
    }

    public void onUserNameInput(String username) {
        currentUser = username;
        storage.setUsername(username);
    }

    @Override
    public List<Players> onLeaderboardRequest() { return storage.getLeaderboard(); }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;
            // SudokuGame objects immutable thus we need to create new ones from the old
            // ones
            gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState);

            storage.updateGameData(gameData);

            view.updateSquare(x, y, input);

            if (gameData.getGameState() == GameState.COMPLETE) {
                storage.updateWins(currentUser);
                view.showDialog(Messages.GAME_COMPLETE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(GameLogic.getNewGame());
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }
}
