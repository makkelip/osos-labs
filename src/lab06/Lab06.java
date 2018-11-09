package lab06;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Lab06 {

    private static final String FILE = "./src/lab06/hard33.txt";
    private final static int ALG = 4;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KnapSack ks = new KnapSack();

        ks.readKnapSack(new File(FILE));
        ks.printKnapSack();

        long tic = System.currentTimeMillis();
        switch (ALG) {
            case 1:
                ks.bruteForce();
                break;

            case 2:
                ks.greedy();
                break;

            case 3:
                ks.greedyheuristic();
                break;
            case 4:
                ks.threadedBruteForce();
                break;
        }
        long tac = System.currentTimeMillis();

        switch (ALG) {
            case 1:
                ks.printSolution("Optimal solution found: ");
                break;

            case 2:
            case 3:
                ks.printSolution("Feasible solution (not necessarily optimal) found: ");
                break;
            case 4:
                ks.printSolution("Threaded: ");
                break;
        }

        System.out.println("\n" + (tac-tic)/1000 + "s elapsed");
    }
}
