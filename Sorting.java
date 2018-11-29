/******************************************************************************
 *  Compilation:  javac Sorting.java
 *  Execution:    java Sorting input.txt AlgorithmUsed
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Author: Ryan Cocuzzo ( with some assistance from the creator of the assignment in the creation of certain provided
        elements of this project)
 *
 *  A program to play with various sorting algorithms. 
 *
 *
 *  Example run:
 *  % java Sorting 2Kints.txt  2
 *
 ******************************************************************************/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class Sorting {

    /**
     * Test all possible input cases (TEST FUNCTION)
     *
     */
    static void testAll() {
        // Instantiate a 6x6 table for each possible output
        double[][] a_table = new double[6][6];
        double[][] b_table = new double[6][6];
        double[][] c_table = new double[6][6];
        double[][] d_table = new double[6][6];
        // Iterate through each possible sorting algorithm i
        for (int i = 5; i >= 0; i--) {
            // Test each file with the algorithm
            double[] oneK = runTest("1Kints.txt", i);ln();
            double[] twoK = runTest("2Kints.txt", i);ln();
            double[] fourK = runTest("4Kints.txt", i);ln();
            double[] eightK = runTest("8Kints.txt", i);ln();
            double[] sxtnK = runTest("16Kints.txt", i);ln();
            double[] thrtyTwoK = runTest("32Kints.txt", i);ln();

            // Retrieve the time complexity of sorting each array and store it in the row's times for this algo (for the array)
            a_table[i][0] = oneK[0];
            a_table[i][1] = twoK[0];ln();
            a_table[i][2] = fourK[0];ln();
            a_table[i][3] = eightK[0];ln();
            a_table[i][4] = sxtnK[0];ln();
            a_table[i][5] = thrtyTwoK[0];ln();

            // Retrieve the time complexity of sorting each array and store it in the row's times for this algo (for the array)
            b_table[i][0] = oneK[1];ln();
            b_table[i][1] = twoK[1];ln();
            b_table[i][2] = fourK[1];ln();
            b_table[i][3] = eightK[1];ln();
            b_table[i][4] = sxtnK[1];ln();
            b_table[i][5] = thrtyTwoK[1];ln();

            // Retrieve the time complexity of sorting each array and store it in the row's times for this algo (for the array)
            c_table[i][0] = oneK[2];
            c_table[i][1] = twoK[2];
            c_table[i][2] = fourK[2];
            c_table[i][3] = eightK[2];
            c_table[i][4] = sxtnK[2];
            c_table[i][5] = thrtyTwoK[2];

            // Retrieve the time complexity of sorting each array and store it in the row's times for this algo (for the array)
            d_table[i][0] = oneK[3];
            d_table[i][1] = twoK[3];
            d_table[i][2] = fourK[3];
            d_table[i][3] = eightK[3];
            d_table[i][4] = sxtnK[3];
            d_table[i][5] = thrtyTwoK[3];
        }

        // Print each table of times (each row represents an algo - each column represents an input)
        print2DArray(a_table);ln();
        print2DArray(b_table);ln();
        print2DArray(c_table);ln();
        print2DArray(d_table);ln();

    }


    /**
     * Run a test for a certain input sequence
     *
     * @param filePath the file path of the input file
     * @param sortingMethod the sorting method used to solve the problem
     * @return an array of the time it took to sort each array a to d [ a0 = time to sort a,..a3 = time it took to sort d ]
     */
    static double[] runTest(String filePath, int sortingMethod) {
        // Get inputs from file
        In in = new In(filePath);

        // Store file input in an array
        int[] a = in.readAllInts();

        // TASK COMMENT: b contains sorted integers from a (You can use Java Arrays.sort() method)
        int[] b = a.clone();
        Arrays.sort(b);
        // TASK COMMENTc contains all integers stored in reverse order
        // TASK COMMENT: (you can have your own O(n) solution to get c from b
        int[] c = b.clone();
        flip(c);
        // TASK COMMENT: d contains almost sorted array
        // TASK COMMENT: (You can copy b to a and then perform (0.1 * d.length)  many swaps to acheive this.
        int num_swaps = (int) (0.01*c.length);
        int[] d = a.clone();
        // perform the partial swap
        partialSwap(num_swaps, b, d);

        // Sort each array and measure the amount of time it takes
        double a_time = sortAndMeasure(a, sortingMethod, "a", filePath);
        double b_time = sortAndMeasure(b, sortingMethod, "b", filePath);
        double c_time = sortAndMeasure(c, sortingMethod, "c", filePath);
        double d_time = sortAndMeasure(d, sortingMethod, "d", filePath);

        // Create array of return times
        double[] return_times = {a_time, b_time, c_time, d_time};

        // Write each array to it's respective file
        writeArrayToFile(a, 'a');
        writeArrayToFile(b, 'b');
        writeArrayToFile(c, 'c');
        writeArrayToFile(c, 'd');

        return return_times;
    }

    /**
     *  Sorts an array and returns the amount of time it took to complete ( + logs the interaction)
     *
     * @param arr the array to be sorted
     * @param sortingAlgorithm (0-5) the respective sorting algorithm to be used
     * @param arrayUsed the string representation of the array being sorted
     * @param argParam the string representation of the text file that we are sorting the inputs from.
     * @return the amount of time it took to sort arr
     */
    static double sortAndMeasure(int[] arr, int sortingAlgorithm, String arrayUsed, String argParam) {
        // Create new instance of Stopwatch -- PROVIDED IN SOURCE
        Stopwatch timer = new Stopwatch();

        // Initialize string interpretation of the algo we are using
        String algoString = "";

        switch (sortingAlgorithm) {
            case 0:
                // Give algo a string value
                algoString = ".sort() Sort";
                // Sort the array
                Arrays.sort(arr);
                break;
            case 1:
                // Give algo a string value
                algoString = "Bubble Sort";
                // Sort the array
                bubbleSort(arr);
                break;
            case 2:
                // Give algo a string value
                algoString = "Selection Sort";
                // Sort
                selectionSort(arr);
                break;
            case 3:
                // Give algo a string value
                algoString = "Insertion Sort";
                // Sort
                insertionSort(arr);
                break;
            case 4:
                // Give algo a string value
                algoString = "Merge Sort";
                // Sort
                mergeSort(arr);
                break;
            case 5:
                // Give algo a string value
                algoString = "Quick Sort";
                // Sort
                quickSort(arr);
                break;
        }
        /*
         * Remaining code in this function below mostly provided in source
         */
        double time = timer.elapsedTimeMillis();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String netID = "RCOCUZZO";

        StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algoString, arrayUsed, time, timeStamp, netID, argParam);
        return time;
    }

 /**
     * 
     * Sorts the numbers present in the file based on the algorithm provided.
     * 0 = Arrays.sort() (Java Default)
     * 1 = Bubble Sort
     * 2 = Selection Sort 
     * 3 = Insertion Sort 
     * 4 = Mergesort
     * 5 = Quicksort
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  {
        // Get the path of the input file
        String filePath = args[0];
        // Get the provided sorting method
        int sortingMethod = Integer.parseInt(args[1]);
        // Test the algo & input
        runTest(filePath, sortingMethod);
    }


    /**
     * Writes an array to a file. This function is a middleman before writeToFile() to wrap the array into
     * a file-writeable value.
     *
     * @param arr the array
     * @param inputLetter the input letter (filename)
     */
    public static void writeArrayToFile(int[] arr, char inputLetter) {
        // The string representation of the array
        String arrayString = "";
        // Iterate through arr elements
        for (int i = 0; i < arr.length; i++) {
            // Append element to string we are writing to file
            arrayString += arr[i] + "\n";
        }
        // Write the string to the file
        writeToFile(arrayString, inputLetter);
    }


    /**
     * Writes a string to a file
     *
     * @param fileContent the string content to be written
     * @param inputLetter the input letter (filename)
     */
    public static void writeToFile(String fileContent, char inputLetter) {

        // Use try/catch to handle errors in writing the file
        try {
            // Instantiate the filepath
            String fileName = inputLetter + ".txt";
            // Instantiate the writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            // Write the content to the file
            writer.write(fileContent);
            // Close rthe input stream
            writer.close();
        } catch (IOException e) {
            // Handle error e
            e.printStackTrace();
        }

    }

    /**
     * Printer newline wrapper function
     */
    public static void ln() { System.out.println(); }

    /**
     * Prints an array
     * @param a the array to print
     */
    public static void printArray(int[] a) {
        // New line
        System.out.println();
        // Iterate through arr
        for (int i: a)
            // Print
            System.out.print(i + " ");
    }

    /**
     * Bubble sort
     *
     * @param a the array to be sorted
     */
    public static void bubbleSort(int[] a) {
        // Iterate through the array
        for (int i = 0; i < a.length; i++) {
            // Bubble backwards to put element in proper place
            for (int j = 0; j < i; j++) {
                // check that element is greater than the past
                if (a[j] > a[j+1]) {
                    // Swap elements
                    swap(a, j, j+1);
                }
            }
        }
    }


    /**
     * Insertion sort
     *
     * @param a the array to be sorted
     */
    public static void insertionSort(int[] a) {
        // Iterate through the array
        for (int i = 1; i < a.length; i++) {
            // Bubble backwards to put element in proper place
            for (int j = i; j > 0; j--) {
                // check that element is greater than the past
                if (a[j] > a[j-1]) {
                    // Swap elements
                    swap(a, j, j-1);
                }
            }
        }
    }

    /**
     * Selection sort
     *
     * @param a the array to be sorted
     */
    public static void selectionSort(int[] a) {

        // Iterate through the array
        for (int i = 0; i < a.length; i++) {
            // Track lowest element index
            int minPos = i;
            // Track lowest element
            int min = a[i];
            // Bubble backwards to put element in proper place
            for (int j = i; j < a.length; j++) {
                // check that element is greater than the past
                if (a[j] < min) {
                    // Set new min
                    min = a[j];
                    // Set new min position
                    minPos = j;
                }

            }
            // Swap elements
            swap(a, i, minPos);
        }
    }


    /**
     * Flips an array
     * @param a the array to be flipped
     */
    public static void flip(int[] a) {
        // Establish temp array
        int[] temp = a.clone();
        // Iterate through an array
        for (int i = a.length-1; i >= 0; i--)
            // Add element to inverse position of new temp array
            a[a.length-1-i] = temp[i];

    }

    /**
     * Swaps two positions in an array
     *
     * @param array the array to have its posiitons moved
     * @param p1 the first posiiton
     * @param p2 the second position
     */
    public static void swap(int[] array, int p1, int p2) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }

    /**
     * Merge sort
     *
     * @param a the array to be sorted
     */
    static void mergeSort(int[] a) {
        // Exit if length 1
        if (a.length == 1) return;
        // Get length
        int n = a.length;
        // Get middle
        int m = n / 2;
        // Create two subarrays, left and right, split down the middle of a
        int[] first = subarray(a, 0, m);
        int[] last = subarray(a, m, n);

        // Call mergesort on the two halves
        mergeSort(first);
        mergeSort(last);
        // Merge the two arrays
        int[] t = merge(first, last);
        // Iterate through new merged array and copy over elements
        for (int i = 0; i < t.length; i++)
            // Copy element
            a[i] = t[i];
    }

    /**
     * Gets subarray of an array given the bounds
     *
     * @param arr the larger array
     * @param start the start position of the subarray
     * @param end the end position of the subarray
     * @return the subarray that is the elements within the bounds of start and end of arr
     */
    static int[] subarray(int[] arr, int start, int end) {
        return Arrays.copyOfRange(arr, start, end);
    }

    /**
     * Merge two sorted parts of an array to be one larger part of the subarray
     * @param a the array
     * @param startLeft the starting point of the leftmost array
     * @param startRight the starting point of the rightmost array
     * @return the merged, combined array
     */
    public static int[] merge(int[] a, int startLeft, int startRight) {
        return merge(subarray(a, startLeft, startRight), subarray(a, startRight, a.length));
    }

    /**
     * Merge two arrays into one array
     *
     * @param a the first array
     * @param b the second array
     * @return the merged array
     */
    public static int[] merge(int[] a, int[] b) {
        // Make new temporary array to hold combined values
        int[] temp = new int[a.length + b.length];
        // Index pointer for left array
        int i = 0;
        // Index pointer for right array
        int j = 0;
        // Index pointer for the merged array
        int tempIndex = 0;
        // Iterate while both pointers are within their respective bounds
        while (i < a.length && j < b.length) {
            // If the left pointer is smaller
            if (a[i] < b[j]) {
                // Put it in the new array and increment respective fields
                temp[tempIndex] = a[i];
                i++;
                tempIndex++;
            } else {
                // Put right element in the new array and increment respective fields
                temp[tempIndex] = b[j];
                j++;
                tempIndex++;
            }
        }
        /*
            Now that the last loop broke, one or neither of the pointers is out of its respective bounds.
            This means that we want to add the remaining sorted elements from the array of
            the pointer that is still within its respective bounds, if it exists.
         */
        // If this pointer is within bounds
        if (i < a.length) {
            // Iterate and add
            while (i < a.length) {
                temp[tempIndex] = a[i];
                i++;
                tempIndex++;
            }

        } else if (j < b.length) { // If it is this pointer
            // Iterate and add
            while (j < b.length) {
                temp[tempIndex] = b[j];
                j++;
                tempIndex++;
            }
        }

        return temp;
    }

    /**
     * Quick Sort
     *
     * @param a the array to sort
     */
    public static void quickSort(int[] a) {
        // Get the size of the array
        int n = a.length;
        if (n <= 1) return;
        // Last element = pivot
        int pivot = a[n-1];
        // Create the three buckets to add elements into.
        ArrayList<Integer> lessThan = new ArrayList<Integer>();
        ArrayList<Integer> equalTo = new ArrayList<Integer>();
        ArrayList<Integer> greaterThan = new ArrayList<Integer>();

        // Iterate through elements, placing them into their respective bucket
        for (int i = 0; i < n-1; i++) {
            // If element is greater
            if (a[i] > pivot)
                // Place into bucket
                greaterThan.add(a[i]);
            else if (a[i] < pivot)
                // Place into bucket
                lessThan.add(a[i]);
            else
                // Place into bucket
                equalTo.add(a[i]);
        }

        // Make array equivalents to the array lists to be able to call the function recursively without
        // function overloading. For each, fill the elements from the arraylist version.
        int[] less = new int[lessThan.size()];
        for (int i = 0; i < lessThan.size(); i++) {
            less[i] = lessThan.get(i).intValue();
        }
        int[] equal = new int[equalTo.size()];
        for (int i = 0; i < equalTo.size(); i++) {
            equal[i] = equalTo.get(i).intValue();
        }
        int[] greater = new int[greaterThan.size()];
        for (int i = 0; i < greaterThan.size(); i++) {
            greater[i] = greaterThan.get(i).intValue();
        }

        // Quicksort the unsorted buckets (equal bucket is already sorted)
        quickSort(less);
        quickSort(greater);
        // Make a new array the size of all the buckets
        int[] arr = new int[less.length + equal.length + greater.length];
        // Track the index of arr as we fill it
        int arrIndex = 0;
        // Fill in sorted elements less than
        for (int i: less) {
            arr[arrIndex] = i;
            arrIndex++;
        }
        // Fill in equal elements
        for (int i: equal) {
            arr[arrIndex] = i;
            arrIndex++;
        }
        // Fill in sorted elements greater than
        for (int i: greater) {
            arr[arrIndex] = i;
            arrIndex++;
        }
        // edit the inputted array
        a =  arr.clone();


    }

    /**
        Wrapper for printing
     */
    static void print(String s) {
        System.out.println(s);
    }

    /**
     * Swap in elements to an array
     * @param swaps # of swaps to perform
     * @param from array to swap from
     * @param to array to swap into
     */
    static void partialSwap(int swaps, int[] from, int[] to) {
        // Iterate & push element at random index into to
        for (int i = 0; i < swaps; i++) {
            to[i] = from[new Random().nextInt(to.length)];
        }
    }


    /**
     * Prints a 2D Array. The arr param must specify the 2D int Array
     * to be printed.
     *
     * @param  arr  The 2D int Array to be printed
     */
    public static void print2DArray(double[][] arr) {
        // Iterate through each column of arr
        for (int i = 0; i < arr.length; i++) {
            // Iterate through each row of arr
            for (int j = 0; j < arr[i].length; j++)
                System.out.print(arr[i][j] + "|"); // Print element
            ln(); // New Line
        }
    }

} 


