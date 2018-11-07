package lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Lab06 {
    private static final String PATH = "./src/lab06/easy20.txt";

    private static int C;
    private static int N;
    static int[] values;
    private static int[] weights;

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
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Search for optimal solution
        LinkedList<Integer> best = new LinkedList<>();
        add(best,best);
        System.out.println("Optimal solution: " + valueSum(best) + " " + weightSum(best));
        System.out.println(best.toString());
    }

    private static LinkedList<Integer> add(LinkedList<Integer> items, LinkedList<Integer> best) {
        for (int i = 0; i < N; i++) {
            if (!items.contains(i) && weightSum(items) + weights[i] <= C) {
                items.add(i);
                if (valueSum(items) > valueSum(best))
                    best = items;
                add(new LinkedList<>(items), best);
            }
        }
        return best;
    }

    private static int valueSum(LinkedList<Integer> items) {
        if (items == null) throw new IllegalArgumentException("item list is null");

        int value = 0;
        for (Integer i: items)
            value += values[i];
        return value;
    }

    private static int weightSum(LinkedList<Integer> items) {
        if (items == null) throw new IllegalArgumentException("item list is null");

        int weight = 0;
        for (Integer i: items)
            weight += weights[i];
        return weight;
    }
}
