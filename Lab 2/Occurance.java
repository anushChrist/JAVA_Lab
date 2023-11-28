import java.util.Scanner;

public class Occurance {
    private static int[] arr;
    private static void TopK(int K) {
        //Frequency Array
        int[] freq = new int[100];
        for (int num : arr) {
            freq[num]++;
        }

        int[][] uniqNums = new int[100][2];
        int count = 0;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                uniqNums[count][0] = i; 
                uniqNums[count][1] = freq[i]; 
                count++;
            }
        }
        //Sorting Unique Numbers Array
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (uniqNums[i][1] < uniqNums[j][1] ||
                    (uniqNums[i][1] == uniqNums[j][1] && uniqNums[i][0] < uniqNums[j][0])) {
                    int[] temp = uniqNums[i];
                    uniqNums[i] = uniqNums[j];
                    uniqNums[j] = temp;
                }
            }
        }
        System.out.print("Output: ");
        for (int i = 0; i < K; i++) {
            System.out.print(uniqNums[i][0] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {

        int[] testCase1 = {3, 1, 4, 4, 5, 2, 6, 1};
        System.out.println("Sample Test Case 1:");
        System.out.println("Input Array: {3, 1, 4, 4, 5, 2, 6, 1}");
        System.out.println("K = 2");
        arr = testCase1;
        TopK(2);
        System.out.println(); 

        int[] testCase2 = {7, 10, 11, 5, 2, 5, 5, 7, 11, 8, 9};
        System.out.println("Sample Test Case 2:");
        System.out.println("Input Array: {7, 10, 11, 5, 2, 5, 5, 7, 11, 8, 9}");
        System.out.println("K = 4");
        arr = testCase2;
        TopK(4);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of your array: ");
        int size = 0;

        while (true) {
            try {
                size = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the array size.");
            }
        }
	arr = new int[size];
        System.out.print("Enter the elements of the array: ");
        for (int i = 0; i < size; i++) {

            while (true) {
                try {
                    arr[i] = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for array element " + (i + 1) + ".");
                }
            }
        }

        System.out.print("Enter the value of K: ");
        int K = 0;
      
        while (true) {
            try {
                K = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the value of K.");
            }
        }
        TopK(K);
        scanner.close();
    }
}


