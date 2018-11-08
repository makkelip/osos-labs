package lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Lab06 {
    private static final String PATH = "./src/lab06/hard33.txt";

    private static int C;
    private static int N;
    static int[] values;
    private static int[] weights;

    private static double FINALCOMBS = 0;
    private static double COMBS = 0;
    private static DecimalFormat df = new DecimalFormat("#.##");

    private static void print() {
        StringBuilder stringBuilder = new StringBuilder("N: " + N + '\n');
        stringBuilder.append("C: " + C + '\n');
        for (int i = 0; i < N; i++) {
            stringBuilder.append(i +1);
            stringBuilder.append("\tvalue: " + values[i]);
            stringBuilder.append("\tweight: " + weights[i]);
            stringBuilder.append("\t v/w: " + (float)values[i] / weights[i]);
            stringBuilder.append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    public static void main(String[] args) {
        //Read file for items, sack capacity and item count
        try {
            Scanner scanner = new Scanner(new File(PATH));
            N = scanner.nextInt();
            values = new int[N];
            weights = new int[N];
            for (int i = 0; i < N; i++) {
                scanner.nextInt();
                values[i] = scanner.nextInt();
                weights[i] = scanner.nextInt();
            }
            C = scanner.nextInt();
            FINALCOMBS = Math.pow(2,N);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        print();
        System.out.println("Final combinations: " + FINALCOMBS);
        //Search for optimal solution
        int[] best = bruteSearch(0, new int[0]);
        COMBS = 0;
        System.out.println("Optimal solution: " + valueSum(best) + " " + weightSum(best));
        System.out.println(Arrays.toString(best));
        //Search for greedy solution
        int[] greedy = greedySearch();
        COMBS = 0;
        System.out.println("Feasible solution (not necessarily optimal) found:" + valueSum(greedy) + " " + weightSum(greedy));
        System.out.println(Arrays.toString(greedy));
    }

    private static int[] greedySearch() {
        int[] sorted = sortValueToWeight();
        int[] result = new int[0];
        for (int i = 0; i < sorted.length && weightSum(result) + weights[i] <= C; i++) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = i;
        }
        return result;
    }

    private static int[] bruteSearch(int index, int[] items) {
        COMBS++;
        if (COMBS % 1000000 == 0) System.out.println(df.format(COMBS / FINALCOMBS * 100) + "%");
        int[] childItems = new int[0];
        for (int i = index; i < N; i++) {
            int[] b = Arrays.copyOf(items, items.length + 1);
            b[items.length] = i;
            b = bruteSearch(i + 1, b);
            if (valueSum(b) > valueSum(childItems) && weightSum(b) <= C)
                childItems = b;
        }
        if (valueSum(items) > valueSum(childItems))
            return items;
        return childItems;
    }

    private static int[] sortValueToWeight() {
        int[] sort = new int[N];
        for (int i = 0; i < N; i++)
            sort[i] = i;
        for (int s = 1; s < sort.length; s++) {
            int sortable = sort[s];
            int i;
            for (i = s-1; i >= 0 && valueToWeight(sortable) > valueToWeight(sort[i]); i--)
                sort[i+1] = sort[i];
            sort[i+1] = sortable;
        }
        return sort;
    }

    private static float valueToWeight(int item) {
        return (float)values[item] / weights[item];
    }
    private static int valueSum(int[] items) {
        if (items == null) throw new IllegalArgumentException("item list is null");

        int value = 0;
        for (Integer i: items)
            value += values[i];
        return value;
    }

    private static int weightSum(int[] items) {
        if (items == null) throw new IllegalArgumentException("item list is null");

        int weight = 0;
        for (Integer i: items)
            weight += weights[i];
        return weight;
    }
}
