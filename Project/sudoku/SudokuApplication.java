package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;

import sudoku.buildlogic.SudokuBuildLogic;
import sudoku.userinterface.IUserInterface;
import sudoku.userinterface.CView;

import java.io.IOException;

// application class - entry point for fx applications
public class SudokuApplication extends Application {
    private IUserInterface.View uiImpl;

    // stage - top level container
    // scene - container for all content
    @Override
    public void start(Stage primaryStage) throws Exception {
        uiImpl = new CView(primaryStage);
        try {
            SudokuBuildLogic.build(uiImpl);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        // part of javafx / launches
        launch(args);
    }
}
