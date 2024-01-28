package sudoku.problemdomain;

public class Players {
    private final String username;
    private final int wins;

    public Players(String username, int wins) {
        this.username = username;
        this.wins = wins;
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }
}