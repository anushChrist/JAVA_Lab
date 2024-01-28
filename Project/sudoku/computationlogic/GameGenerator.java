package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

/*public class GameGenerator {
    public static int[][] getNewGameGrid() {
        return unsolvedGame(getSolvedGame());
    }

    private static int[][] unsolvedGame(int[][] solvedGame) { //
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false; // a puzzle might not be solvable so we must ensure that it is
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (!solvable) {
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            int index = 0;
            // removing 40 numbers
            while (index < 40) {
                int xCoodinate = random.nextInt(GRID_BOUNDARY);
                int yCoodinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoodinate][yCoodinate] != 0) {
                    solvableArray[xCoodinate][yCoodinate] = 0;
                    index++;
                }
            }
            // attempt to solve the new puzzle to ensure it is solvable
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        return solvableArray; // returns an array that can be solved
    }

    // two stage back tracking algorithm
    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis()); // seeding random number generator
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            int allocations = 0; // number of times a given number is allocated (9 times)
            int interrupt = 0; // if allocation done too many times increment interrupt
            List<Coordinates> allocTracker = new ArrayList<>(); // list of Coordinats (objects) for backtracking / holds
                                                                // the Coordinates where the current 'value' is put

            int attempts = 0; // fail safe

            while (allocations < GRID_BOUNDARY) { // allcations < 9 keep attempting

                if (interrupt > 200) { // if too many invalid attempts then backtrack
                    allocTracker.forEach(coord -> { // lambda exp Coordinate object
                        newGrid[coord.getX()][coord.getY()] = 0; // set each coordinate value to 0
                    });

                    interrupt = 0; // reset board for that number
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500) { // if even that fails then clear whole board
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }
                int xCoordinate = random.nextInt(GRID_BOUNDARY); // randomly select a square
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0) { // if no value present then allocate else skip
                    newGrid[xCoordinate][yCoordinate] = value;

                    if (GameLogic.sudokuIsInvalid(newGrid)) { // if board invalid then reverse and increment interrupt
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else { // if everything's fine then add to backtracking list and increment allocations
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}*/





class GameGenerator {
    public static int[][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }


    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        //Value represents potential values for each square. Each value must be allocated 9 times.
        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            //allocations refers to the number of times in which a square has been given a value.
            int allocations = 0;

            //If too many allocation attempts are made which end in an invalid game, we grab the most recent
            //allocations stored in the List below, and reset them all to 0 (empty).
            int interrupt = 0;

            //Keep track of what has been allocated in the current frame of the loop
            List<Coordinates> allocTracker = new ArrayList<>();

            //As a failsafe, if we keep rolling back allocations on the most recent frame, and the game still
            //keeps breaking, after 500 times we reset the board entirely and start again.
            int attempts = 0;

            while (allocations < GRID_BOUNDARY) {

                if (interrupt > 200) {
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500) {
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value;

                    //if value results in an invalid game, then re-assign that element to 0 and try again
                    if (GameLogic.sudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    }
                    //otherwise, indicate that a value has been allocated, and add it to the allocation tracker.
                    else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return newGrid;
    }


    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;

        //note: not actually solvable until the algorithm below finishes!
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (solvable == false){

            //Take values from solvedGame and write to new unsolved; i.e. reset to initial state
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            //remove 40 random numbers
            int index = 0;
            while (index < 40) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);
            //check if result is solvable
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);

            //TODO Delete after tests
            //System.out.println(solvable);
        }

        return solvableArray;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }

}
