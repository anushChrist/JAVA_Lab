package sudoku.storage;

import sudoku.problemdomain.Players;
import sudoku.problemdomain.SudokuGame;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class CStorage implements IStorage {
    private static File GAME_DATA = new File(System.getProperty("user.home"), "gamedata.txt"); // OS home directory

    private static final String URL = "jdbc:mysql://localhost:3306/sudoku";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String USERNAME = "username";
    private static final String WINS = "wins";

    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStrem = new ObjectOutputStream(fileOutputStream);
            objectOutputStrem.writeObject(game);
            objectOutputStrem.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        } catch (ClassNotFoundException e) {
            throw new IOException("File not found!");
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------
    public void setUsername(String username) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            out.println("---------------------DATABASE CONNECTED!-------------------------");
                PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
                checkStatement.setString(1, username);
                ResultSet resultSet = checkStatement.executeQuery();

                if (!resultSet.next()) {
                    // User does not exist, insert with default wins value of 0
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (username, wins) VALUES (?, 0)");
                    insertStatement.setString(1, username);
                    insertStatement.executeUpdate();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWins(String username) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET wins = wins + 1 WHERE username = ?");
            updateStatement.setString(1, username);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*public void setUsername(String username) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (username) VALUES (?)");
            insertStatement.setString(1, username);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public List<Players> getLeaderboard() {
        // JDBC code to retrieve the top 10 players and their number of wins
        List<Players> leaderboard = new ArrayList<>();
        String sql = "SELECT username, wins FROM users ORDER BY wins DESC LIMIT 10";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString(USERNAME);
                int wins = resultSet.getInt(WINS);
                leaderboard.add(new Players(username, wins));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }
}
