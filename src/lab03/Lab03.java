package lab03;

import util.Stopwatch;

import java.io.PrintWriter;

public class Lab03 {

    static String OUTPUTFILE = "./output/sort-compare.csv";
    static int N = 10;
    static int STARTN = 1000;
    static int INCN = 1000;

    public static void main(String[] args) {
        try {
            int arrayN = STARTN;
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");
            Stopwatch sw = new Stopwatch();
            writer.println("N;Insert sort; Quick sort; Bubble sort");

            for (int i = 0; i < N; i++) {
                System.out.println("round: " + i);

                StringBuilder result = new StringBuilder();
                result.append(arrayN);

                InsertSort  insertSort =    new InsertSort(arrayN);
                Sort        sort       =    new Sort(arrayN);
                BubbleSort  bubbleSort =    new BubbleSort(arrayN);

                sw.measure(insertSort); result.append(";"); sw.toValue(result);
                System.out.println("insertSort: " + sw.toValue());

                sw.measure(sort); result.append(";"); sw.toValue(result);
                System.out.println("Arrays.sort: " + sw.toValue());

                sw.measure(bubbleSort); result.append(";"); sw.toValue(result);
                System.out.println("Bubble sort: " + sw.toValue());

                writer.println(result.toString());
                arrayN += INCN;
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

