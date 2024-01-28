package sudoku.userinterface;

import javafx.scene.Group;
import sudoku.problemdomain.Players;
import sudoku.problemdomain.SudokuGame;

import java.util.List;

public interface IUserInterface {
    interface EventListener {
        void onSudokuInput(int x, int y, int input);

        void onUserNameInput(String userName);

        List<Players> onLeaderboardRequest();

        void onDialogClick();
    }

    interface View {
        void setListener(IUserInterface.EventListener listener);

        void getUsername();
        // update just a single square
        void updateSquare(int x, int y, int input);

        // update entire board after game completion/ initial execution
        void updateBoard(SudokuGame game);

        void showDialog(String message);

        void showError(String message);
    }
}
