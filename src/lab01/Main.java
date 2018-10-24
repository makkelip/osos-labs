package lab01;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final int N = 10;
    private static final int STARTN = 10000;
    private static final int INCN = 5000;
    private static final String OUTPUTFILE = "./output/data-str.csv";

    public static void main(String[] args) {


        try {
            int arrayN = STARTN;
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");
            Stopwatch sw = new Stopwatch();
            writer.println("N;Linear;Binary");

            for (int i = 0; i < N; i++) {
                System.out.println("round: " + i);
                String[] intList = generateRandomStringArray(arrayN);
                Arrays.sort(intList);

                StringBuilder result = new StringBuilder();
                result.append(arrayN);

                MySearch.Linear lin = new MySearch.Linear();
                lin.setArray(intList); lin.setSearchables();

                MySearch.Binary bin = new MySearch.Binary();
                bin.setArray(intList); bin.setSearchables();

                sw.measure(lin); result.append(";"); sw.toValue(result);
                System.out.println("linear to value: " + sw.toValue());

                sw.measure(bin); result.append(";"); sw.toValue(result);
                System.out.println("binary to value: " + sw.toValue());

                writer.println(result.toString());
                arrayN += INCN;
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Integer[] generateRandomIntegerArray(int n){
        Integer[] list = new Integer[n];
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list[i] = random.nextInt();
        }
        return list;
    }

    public static String[] generateRandomStringArray(int n){
        String[] list = new String[n];
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            byte[] array = new byte[2];
            random.nextBytes(array);
            list[i] = new String(array, Charset.forName("UTF-8"));
        }
        return list;
    }
}
