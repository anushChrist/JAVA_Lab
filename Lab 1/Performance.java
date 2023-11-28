import java.util.Scanner;

public class Performance {

   
    int[] marks;

    Performance() {
        marks = new int[10]; 

    void readMarks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter marks for 10 students:");
        for (int i = 0; i < 10; i++) {
            System.out.print("Student " + (i + 1) + ": ");
            marks[i] = scanner.nextInt();
        }
    }

    public int highestMark() {
        int highest = marks[0];
        for (int i = 1; i < 10; i++) {
            if (marks[i] > highest) {
                highest = marks[i];
            }
        }
        return highest;
    }

    public int leastMark() {
        int least = marks[0];
        for (int i = 1; i < 10; i++) {
            if (marks[i] < least) {
                least = marks[i];
            }
        }
        return least;
    }

    public int getMode() {
        int[] frequency = new int[101]; // Assuming marks range from 0 to 100
        for (int mark : marks) {
            frequency[mark]++;
                    }

        int mode = marks[0];
        int maxFrequency = frequency[marks[0]];

        for (int i = 1; i < 101; i++) {
            if (frequency[i] > maxFrequency || (frequency[i] == maxFrequency && i > mode)) {
                mode = i;
                maxFrequency = frequency[i];
            }
        }

        return mode;
    }

    public int getFreqAtMode() {
        int mode = getMode();
        int frequency = 0;
        for (int mark : marks) {
            if (mark == mode) {
                frequency++;
            }
        }
        return frequency;
    }

    float getMean() {
        
        int total=0;
        int len = marks.length;
        for (int i =0;i<len;i++) {
            total +=marks[i];
           // System.out.println(marks[i]);
        }
        System.out.println("Total marks: "+total);
        System.out.println("Number of records: "+len);
        

        return (float)total/len;
    }



    public void display() {
        System.out.println("Highest Mark: " + highestMark());
        System.out.println("Least Mark: " + leastMark());
        System.out.println("Mode: " + getMode());
        System.out.println("Frequency at Mode: " + getFreqAtMode());
        System.out.println("Mean marks of class: " + getMean());
    }

    public static void main(String[] args) {
        Performance performance = new Performance();
        performance.readMarks();        
        performance.display();
    }
}
