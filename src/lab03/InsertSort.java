package lab03;

import util.Stopwatch;

import java.security.SecureRandom;
import java.util.Arrays;

public class InsertSort implements Stopwatch.Test {

    Comparable[] array;
    int N;

    public InsertSort(int N) {
        this.N = N;
    }

    public void insertSort() {
        for (int s = 1; s < array.length; s++) {
            Comparable sortable = array[s];
            int i;
            for (i = s-1; i >= 0 && sortable.compareTo(array[i]) < 0; i--)
                array[i+1] = array[i];
            array[i+1] = sortable;
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
        insertSort();
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

