public class AlphabetWarGame {

    int DS_W = 4;
    int DS_P = 3;
    int DS_B = 2;
    int DS_S = 1;
    int DS_M = 4;
    int DS_Q = 3;
    int DS_D = 2;
    int DS_Z = 1;

    int strengthW;
     int strengthP;
     int strengthB;
     int strengthS;
     int strengthM;
     int strengthQ;
     int strengthD;
    int strengthZ;

    public AlphabetWarGame() {
        this.strengthW = DS_W;
        this.strengthP = DS_P;
        this.strengthB = DS_B;
        this.strengthS = DS_S;
        this.strengthM = DS_M;
        this.strengthQ = DS_Q;
        this.strengthD = DS_D;
        this.strengthZ = DS_Z;
    }

    public AlphabetWarGame(int strengthW, int strengthP, int strengthB, int strengthS,
                           int strengthM, int strengthQ, int strengthD, int strengthZ) {
        this.strengthW = strengthW;
        this.strengthP = strengthP;
        this.strengthB = strengthB;
        this.strengthS = strengthS;
        this.strengthM = strengthM;
        this.strengthQ = strengthQ;
        this.strengthD = strengthD;
        this.strengthZ = strengthZ;
    }

    public void determineWinner(String word) {
        int leftStrength = calculateStrength(word, true);
        int rightStrength = calculateStrength(word, false);
        printResult(leftStrength, rightStrength);
    }

    public void determineWinner(String leftWord, String rightWord) {
        int leftStrength = calculateStrength(leftWord, true);
        int rightStrength = calculateStrength(rightWord, false);
        printResult(leftStrength, rightStrength);
    }

    private int calculateStrength(String word, boolean isLeft) {
        int totalStrength = 0;
        for (char letter : word.toCharArray()) {
            totalStrength += isLeft ? getLeftStrength(letter) : getRightStrength(letter);
        }
        return totalStrength;
    }

    private int getLeftStrength(char letter) {
        switch (letter) {
            case 'w':
                return strengthW;
            case 'p':
                return strengthP;
            case 'b':
                return strengthB;
            case 's':
                return strengthS;
            default:
                return 0;
        }
    }

    private int getRightStrength(char letter) {
        switch (letter) {
            case 'm':
                return strengthM;
            case 'q':
                return strengthQ;
            case 'd':
                return strengthD;
            case 'z':
                return strengthZ;
            default:
                return 0;
        }
    }

    private void printResult(int leftStrength, int rightStrength) {
        if (leftStrength > rightStrength) {
            System.out.println("Left side wins!");
        } else if (leftStrength < rightStrength) {
            System.out.println("Right side wins!");
        } else {
            System.out.println("Let's fight again!");
        }
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            new AlphabetWarGame().determineWinner(args[0]);
        } else if (args.length == 2) {
            new AlphabetWarGame().determineWinner(args[0], args[1]);
        } else {
            System.out.println("Usage: java AlphabetWarGame <word1> [word2]");
        }


    }
}
