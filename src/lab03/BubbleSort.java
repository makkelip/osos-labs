package lab03;

import util.Stopwatch;

import java.security.SecureRandom;
import java.util.Arrays;

public class BubbleSort implements Stopwatch.Test {

    Comparable[] array;
    int N;

    public BubbleSort(int N) {
        this.N = N;
    }

    public void bubbleSort() {
        boolean complete = false;
        while (!complete) {
            complete = true;
            for (int i = 1; i < array.length; i++) {
                if (array[i].compareTo(array[i-1]) < 0) {
                    Comparable c = array[i];
                    array[i] = array[i-1];
                    array[i-1] = c;
                    complete = false;
                }
            }
        }
    }

    @Override
    public void setup() {
        this.array = new Comparable[N];
        for (int i = 0; i < array.length; i++)
            array[i] = randomString(32);
    }

    @Override
    public void test() {
        bubbleSort();
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static void main(String[] args) {
        String array[] = {"rabbs", "örsta", "abba", "mike"};
        InsertSort is = new InsertSort(4);
        is.array = array;
        is.insertSort();
        System.out.println(Arrays.toString(array));
    }
}
