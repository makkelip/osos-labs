package lab01;

import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final int N = 6;
    private static final int STARTN = 1000;
    private static final int INCN = 500;
    private static final String OUTPUTFILE = "./output/data.csv";

    public static void main(String[] args) {

        Stopwatch sw = new Stopwatch();

        Integer[] intList = generateRandomArray(STARTN);
        Arrays.sort(intList);

        MySearch lin = new MySearch(false); lin.setArray(intList);
        MySearch bin = new MySearch(true);  bin.setArray(intList);

        sw.measure(lin);
    }

    public static Integer[] generateRandomArray(int n){
        Integer[] list = new Integer[n];
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list[i] = random.nextInt();
        }
        return list;
    }
}
