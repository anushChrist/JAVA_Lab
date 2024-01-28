package sudoku.storage;

import java.io.IOException;
import java.util.List;

import sudoku.problemdomain.Players;
import sudoku.problemdomain.SudokuGame;

public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;

    SudokuGame getGameData() throws IOException;

    void setUsername(String username);

    void updateWins(String username);

    List<Players> getLeaderboard();

}
