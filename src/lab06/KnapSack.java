package lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class KnapSack implements Runnable {

    private int C;
    private int N;
    private int[] values;
    private int[] weights;
    private int[] solution = new int[0];

    private double FINALCOMBS = 0;
    private double COMBS = 0;
    private DecimalFormat df = new DecimalFormat("#.##");

    //for threading
    private String name;
    private int index;
    private int[] items;
    private int[] from;
    private boolean complete = false;

    public KnapSack() {}

    public KnapSack(String name, int index, int[] items, int[] from) {
        this.name = name;
        this.index = index;
        this.items = items;
        this.from = from;
    }

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

    public void threadedBruteForce() {
        int numInitItems = 2;
        int numThreads;
        int processors;
        List<List<Integer>> integerSubset;
        int[][] knapSacks;
        /*
        processors = Runtime.getRuntime().availableProcessors();
        numInitItems = (int) Math.ceil(Math.log(processors) / Math.log(2));
        */
        numThreads = (int)Math.pow(2, numInitItems);

        List<Integer> nums = new ArrayList();
        for (int i = 0; i < numInitItems; i++)
            nums.add(i);
        integerSubset = combinations(nums);

        int[] burteFoceItems = new int[N-numInitItems];
        for (int i = 0; i < burteFoceItems.length; i++)
            burteFoceItems[i] = i + numInitItems;
        int[][] intSubset = new int[numThreads][0];
        for (int i = 0; i < numThreads; i++) {
            for (int j = 0; j < integerSubset.get(i).size(); j++) {
                intSubset[i] = Arrays.copyOf(intSubset[i], intSubset[i].length + 1);
                intSubset[i][intSubset[i].length - 1] = integerSubset.get(i).get(j);
            }
        }
        KnapSack[] sacks = new KnapSack[numThreads];
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            sacks[i] = new KnapSack("Sack"+i,numInitItems-1,intSubset[i], burteFoceItems);
            sacks[i].weights = this.weights;
            sacks[i].values = this.values;
            sacks[i].N = this.N;
            sacks[i].C = this.C;
            threads[i] = new Thread(sacks[i], "Sack:" + i);
            threads[i].start();
        }
        boolean wait = true;
        while (wait) {
            wait = false;
            for (KnapSack s: sacks) {
                if (s.solution.length == 0) wait = true;
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Starting thread: " + name);
        COMBS = 0;
        solution = bruteSearch(index,items,from);
        complete = true;
    }

    public List<List<Integer>> combinations(List<Integer> num) {
        if (num.size() > 1)
        {
            List<List<Integer>> result = new ArrayList<>();

            for (Integer n : num)
            {
                List<Integer> subIntegers = new ArrayList<>(num);
                subIntegers.remove(n);

                result.add(new ArrayList<>(Arrays.asList(n)));

                for (List<Integer> combinations : combinations(subIntegers))
                {
                    combinations.add(n);
                    result.add(combinations);
                }
            }

            return result;
        }
        else
        {
            List<List<Integer>> result = new ArrayList<>();
            result.add(new ArrayList<>(num));
            return result;
        }
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
        int[] def = new int[N];
        for (int i = 0; i < def.length; i++)
            def[i] = i;
        solution = bruteSearch(0, new int[0], def);
        Thread.currentThread().setPriority(oldPriority);
    }
    public int[] bruteSearch(int index, int[] items, int[] from) {
        //Show progress in percentages
        COMBS++;
        if (COMBS % 1000000 == 0) System.out.println(df.format(COMBS / FINALCOMBS * 100) + "%");

        int[] childItems = new int[0];
        for (int i = index; i < from.length; i++) {
            int[] b = items;
            if (weightSum(items) + weights[i] <= C) {
                b = Arrays.copyOf(items, items.length + 1);
                b[items.length] = from[i];
            }
            //Recursive. Goes one level deeper
            b = bruteSearch(i + 1, b, from);
            if (valueSum(b) > valueSum(childItems))
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
