package lab03;

import util.Stopwatch;

import java.security.SecureRandom;
import java.util.Arrays;

public class Sort implements Stopwatch.Test {
    Comparable[] array;
    int N;

    public Sort(int N) {
        this.N = N;
    }

    @Override
    public void setup() {
        this.array = new Comparable[N];
        for (int i = 0; i < array.length; i++)
            array[i] = randomString(32);
    }

    @Override
    public void test() {
        Arrays.sort(array);
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
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
