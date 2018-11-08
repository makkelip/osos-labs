package lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class KnapSack {

    private int C;
    private int N;
    private int[] values;
    private int[] weights;
    private int[] solution;

    private double FINALCOMBS = 0;
    private double COMBS = 0;
    private DecimalFormat df = new DecimalFormat("#.##");
    private static final BigDecimal MILLION = new BigDecimal("1000000");

    public boolean readKnapSack(File file) {
        try {
            Scanner scanner = new Scanner(file);
            N = scanner.nextInt();
            values = new int[N];
            weights = new int[N];
            for (int i = 0; i < N; i++) {
                scanner.nextInt();
                values[i] = scanner.nextInt();
                weights[i] = scanner.nextInt();
            }
            C = scanner.nextInt();
            FINALCOMBS = Math.pow(2, N);
            scanner.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printKnapSack() {
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

    public void printSolution(String s) {
        System.out.println(s + "value: " + valueSum(solution) + " weight: " + weightSum(solution) + '\n'
                + Arrays.toString(solution));
    }

    //Put one item to sack and fill rest with greedy algorithm
    public void greedyheuristic() {
        solution = new int[0];
        int[] def = new int[N];
        //init default array from 0 to N-1
        for (int i = 0; i < def.length; i++)
            def[i] = i;

        for (int i = 0; i < N; i++) {
            //Create sorted array without i item
            int[] sorted = new int[N-1];
            int index = 0;
            for (int x: def) {
                if (x != i) {
                    sorted[index] = x;
                    index++;
                }
            }
            sortValueToWeight(sorted);
            //Use greedy algorithm  with added i item
            int[] arr = new int[0];
            for (int j = 0; j < sorted.length && weightSum(arr) + weights[sorted[j]] + weights[i] <= C; j++) {
                arr = Arrays.copyOf(arr, arr.length + 1);
                arr[arr.length - 1] = sorted[j];
            }
            //finally add i item
            arr = Arrays.copyOf(arr, arr.length + 1);
            arr[arr.length - 1] = i;
            //compare to best
            if (valueSum(arr) > valueSum(solution))
                solution = arr;
        }
    }

    //Searches for solution taking the items with the greatest value to weight ratio first
    public void greedy() {
        solution = greedySearch();
    }
    private int[] greedySearch() {
        int[] sorted = new int[N];
        //init with indexes
        for (int i = 0; i < N; i++)
            sorted[i] = i;
        sortValueToWeight(sorted);

        //add items to result
        int[] result = new int[0];
        for (int i = 0; i < sorted.length && weightSum(result) + weights[sorted[i]] <= C; i++) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = sorted[i];
        }
        return result;
    }

    //Goes through every combination
    public void bruteForce() {
        COMBS = 0;
        int oldPriority = Thread.currentThread().getPriority();
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        solution = bruteSearch(0, new int[0]);
        Thread.currentThread().setPriority(oldPriority);
    }
    private int[] bruteSearch(int index, int[] items) {
        //Show progress in percentages
        COMBS++;
        if (COMBS % 1000000 == 0) System.out.println(df.format(COMBS / FINALCOMBS * 100) + "%");

        int[] childItems = new int[0];
        for (int i = index; i < N; i++) {
            int[] b = Arrays.copyOf(items, items.length + 1);
            b[items.length] = i;
            //Recursive. Goes one level deeper
            b = bruteSearch(i + 1, b);
            if (valueSum(b) > valueSum(childItems) && weightSum(b) <= C)
                childItems = b;
        }
        if (valueSum(items) > valueSum(childItems))
            return items;
        return childItems;
    }

    //sorts based on value/weight ratio
    private void sortValueToWeight(int[] sort) {
        for (int s = 1; s < sort.length; s++) {
            int sortable = sort[s];
            int i;
            for (i = s-1; i >= 0 && valueWeightRatio(sortable) > valueWeightRatio(sort[i]); i--)
                sort[i+1] = sort[i];
            sort[i+1] = sortable;
        }
    }

    private float valueWeightRatio(int item) {
        return (float)values[item] / weights[item];
    }

    private int valueSum(int[] items) {
        if (items == null) throw new IllegalArgumentException("item list is null");

        int value = 0;
        for (Integer i: items)
            value += values[i];
        return value;
    }

    private int weightSum(int[] items) {
        if (items == null) throw new IllegalArgumentException("item list is null");

        int weight = 0;
        for (Integer i: items)
            weight += weights[i];
        return weight;
    }
}
