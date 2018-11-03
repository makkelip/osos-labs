package lab04b;

import java.util.Arrays;

public class BignumMy {

    byte[] byteNum;

    public BignumMy(String s1) {
        char zero = '0';
        byteNum = new byte[s1.length()];
        char[] chars = s1.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            byteNum[i] = (byte)(chars[i]-zero);
        }
    }

    public BignumMy mulBigNum(BignumMy n2) {

        return null;
    }

    private byte[] multiply(byte[] arrA, byte[] arrB) {

        if (arrA.length != arrB.length) {
            if (arrA.length > arrB.length)
                arrB = resize(arrB, arrA.length);
            else
                arrA = resize(arrA, arrB.length);
        }
        if (arrA.length == 0) return new byte[1];
        if (arrA.length == 1)
            //return arrA[0] * arrB[0];
            ;

        int mid = arrA.length / 2;
        byte[] arrAl = Arrays.copyOfRange(arrA, 0, mid-1);
        byte[] arrAr = Arrays.copyOfRange(arrA, mid, arrA.length-1);
        byte[] arrBl = Arrays.copyOfRange(arrA, 0, mid-1);
        byte[] arrBr = Arrays.copyOfRange(arrA, mid, arrA.length-1);

        byte[] P1 = multiply(arrAl, arrBl);
        //byte[] P2 = multiply(arrAl + arrAr, arrBl + arrBr);
        byte[] P3 = multiply(arrAr, arrBr);

        //return P1* Math.pow(10, arrA.length) + (P2 - P1 - P3) * Math.pow(10, arrA.length/2) + P3;
        return new byte[2];
    }

    private byte[] resize(byte[] arr, int n) {
        byte[] newArr = new byte[n];
        for (int i = arr.length - 1; i >= 0; i--) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    public int rclMulCounter() {
        return 1;
    }


    public static void main(String[] args) {
        BignumMy b = new BignumMy("0123456789");
        System.out.println(b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b: byteNum)
            sb.append(b);
        return sb.toString();
    }
}
