package lab05;

import java.io.File;

public class Lab05 {

    public static void main(String[] args) {
        File file = new File("./output/maze.grh");
        Graph graph = new Graph();
        graph.readGraph(file);
        graph.printGraph();
    }
}
