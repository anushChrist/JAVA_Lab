package sudoku.userinterface;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;

import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.Players;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;
import java.util.List;

public class CView implements IUserInterface.View, EventHandler<KeyEvent> {
    private final Stage stage; // background window
    private final Group root; // like a div in html, container

    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates; // keep track of 81 text fields
    // List<Players> leader~~board;

    private IUserInterface.EventListener listener; // pass events user causes > listener interpret events pass
                                                   // messages between view, UI, frontend, backend

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 138);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "Sudoku";

    public CView(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.show(); // reveal user interface
    }

    private void drawLeaderBoard(Group root) {
        // draws a leader board to the right of the SudokuBoard
        VBox leaderboardBox = new VBox();
        leaderboardBox.setLayoutX(WINDOW_X - 20);
        leaderboardBox.setLayoutY(150);

        TableView<Players> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.setPrefWidth(220);

        TableColumn<Players, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Players, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        tableView.getColumns().addAll(usernameColumn, winsColumn);

        List<Players> leaderboard = listener.onLeaderboardRequest();
        tableView.getItems().addAll(leaderboard);

        leaderboardBox.getChildren().add(tableView);
        root.getChildren().add(leaderboardBox);
    }

    public void getUsername() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Username");
        dialog.setHeaderText("Please enter your username:");
        dialog.setContentText("Username:");

        // Traditional way to get the response value.
        java.util.Optional<String> result = dialog.showAndWait();

        String username = result.orElse(null);

        if (username.equals("")) listener.onUserNameInput("Default");
        else listener.onUserNameInput(result.orElse("Default"));
    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X + 220, WINDOW_Y); // background / view group
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE); // set font colour
        title.setFont(Font.font("", FontWeight.BOLD, 43));
        root.getChildren().add(title);
    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);

    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50; // start here
        final int yOrigin = 50;

        final int xAndYDelta = 64; // move after every loop

        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex, yIndex); // extends javafx TextField

                styleSudokuTile(tile, x, y); // helper method for styling sudoku tile number

                tile.setOnKeyPressed(this); // can pass this because implements EventHandler<ActionEvent> ->
                                            // handle(ActionEvent actionEvent)

                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile); // hashmap; everytime we draw a
                                                                                 // textfields we add it to the hashmap

                root.getChildren().add(tile); // forgot to add text field tiles to group
            }
        }
    }

    // each square 64x64
    private void drawGridLines(Group root) {
        int xAndY = 114; // start at
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3; // sepearate squares
            } else {
                thickness = 2;
            }
            // drawing line moving 64 units by index -> series of grid lines
            // x, y , height, width
            Rectangle verticalLine = getLine(xAndY + 64 * index, BOARD_PADDING, BOARD_X_AND_Y, thickness);

            Rectangle horizontalLine = getLine(BOARD_PADDING, xAndY + 64 * index, thickness, BOARD_X_AND_Y);

            // add UI element in // super class of UI Elements is Node?
            root.getChildren().addAll(verticalLine, horizontalLine);

            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);

        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x); // relative to window/stage
        tile.setLayoutY(y);

        tile.setPrefHeight(64); // 64*64 SIZE
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    // implementing VIEW interfacei

    @Override
    public void setListener(IUserInterface.EventListener listener) {
        this.listener = listener; // build logic control logic to this view
        getUsername();
        drawLeaderBoard(root);

    }

    // input handled in control logic class
    @Override
    public void updateSquare(int x, int y, int input) { // after input
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));

        String value = Integer.toString(input);

        if (value.equals("0"))
            value = "";

        tile.textProperty().setValue(value);
    }


    // update whole board when the game restarts and print the new unsolved game
    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                String value = Integer.toString(game.getCopyOfGridState()[xIndex][yIndex]);

                if (value.equals("0"))
                    value = "";

                tile.setText(value);

                //if (game.getGameState() == GameState.NEW) {
                    if (value.equals("")) { // if square empty
                        tile.setStyle("-fx-opacity: 1;"); // set to less opaque font / CSS used here
                        //tile.setEditable(true);
                        // tile.setDisable(true); // enable text field
                    } else { // if number already coloured in
                        tile.setStyle("-fx-opacity: 0.5;");
                        tile.setDisable(true); // read only tile
                    }
                //}
            }
        }
    }



    @Override
    public void showDialog(String message) { // when game logic shows game completed properly
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK); // contratulation new game?
                                                                                        // alert
        dialog.showAndWait(); // wait for user

        if (dialog.getResult() == ButtonType.OK)
            listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK); // failed to read/write game state?
        dialog.showAndWait();
    }

    public void handle(KeyEvent event) { // from event handler
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().matches("[0-9]")) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource()); // source object that was clicked
            } else if (event.getCode() == KeyCode.BACK_SPACE) { // clear entry -> 0
                handleInput(0, event.getSource());
            } else {
                ((TextField) event.getSource()).setText("");
            }
        }
        event.consume(); // once event gets consumed it doesn't propagate through the application
    }

    // listner -> control logic class/ presenter/ manages interaction between user,
    // user interface and backend (computational logic)
    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value);
    }

    class SudokuTextField extends TextField {
        private final int x;
        private final int y;

        public SudokuTextField(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public void replaceText(int i, int i1, String s) {
            if (!s.matches("[0-9]")) {
                super.replaceText(i, i1, s);
            }
        }

        @Override
        public void replaceSelection(String s) {
            if (!s.matches("[0-9]")) {
                super.replaceSelection(s);
            }
        }
    }

}
