package lab05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {

    private LinkedList<Integer>[] adjList;

    public Graph() {
        adjList = new LinkedList[5];
        adjList[0] = new LinkedList<>();
    }
    public int nodes() {
        return 0;
    }

    public boolean readGraph(File file) {
        try {
            Scanner scanner = new Scanner(file);
            LinkedList<String> lines = new LinkedList<>();
            //Split lines
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            adjList = new LinkedList[lines.size()];
            for (String s: lines) {
                //Split numbers and set to adjList array
                String[] numbers = s.split(" ");
                int index = Integer.parseInt(numbers[0]);
                adjList[index] = new LinkedList<>();
                for (int i = 1; i < numbers.length; i++)
                    adjList[index].add(Integer.parseInt(numbers[i]));
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adjList.length; i++) {
            sb.append(i); sb.append(':');
            for (Integer adj: adjList[i]) {
                sb.append(' ');
                sb.append(adj);
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    public void dfs(int start, boolean visited[], int pred[]) {

    }
}
