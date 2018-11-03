package lab04b;

import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;

/**
 *
 * @author Jarkko
 */
public class Bignum {
    private byte[] number;          // least significand digit first (index 0), most significand last (index length-1)
    private static int mulCounter;  // variable to count the number of multiplications


    public Bignum(int n) {
        number = new byte[n];
    }
    
    public Bignum(String s) {
        int     n = s.length();
        number = new byte[n];

        for (int i = n-1; i >= 0; i--)
            number[n-i-1] = (byte)Character.getNumericValue(s.charAt(i));
    }

    
    /* print out the number to the string s */
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (int i = number.length-1; i >= 0; i--)
            s.append(number[i]);
        
        return (s.toString());
    }


    /* print out the given number (for debug only) */
    public void printBigNum(String s) {
        System.out.println(s + ": " + toString());
    }


    /* create a new number whose digits are x[from, to) */
    public Bignum selectBigNum(int from, int to) {
        Bignum r = new Bignum(to-from);

        for (int i = from; i < to; i++)
            r.number[i-from] = number[i];

        return r;
    }


    /* subtract two numbers this - y */
    public Bignum subBigNum(Bignum y) throws Exception {
            Bignum r = new Bignum(number.length);
            int    carry;

            // sub digits, starting from the least significant digit
            carry = 0;
            for (int i = 0; i < number.length; i++) {
                r.number[i] = (byte)(number[i] - (i < y.number.length ? y.number[i] : 0) - carry);
                if (r.number[i] < 0) {
                    carry = 1;
                    r.number[i] += 10;
                } else
                    carry = 0;
        }

        if (carry > 0) {
            throw new Exception("Overflow in subtraction\n");
        }

        return r;
    }


    /* add two numbers together this + y */
    public Bignum addBigNum(Bignum y) {
        Bignum r, a, b;
        int    carry;

        // a is the larger number, b is the smaller
        if (number.length > y.number.length) {
            a = this; b = y;
        } else {
            a = y; b = this;
        }

        r = new Bignum(a.number.length);

        // add digits, starting from the least significant digit
        carry = 0;
        for (int i = 0; i < a.number.length; i++) {
            r.number[i] = (byte)(a.number[i] + (i < b.number.length ? b.number[i] : 0) + carry);
            if (r.number[i] > 9) {
                carry = 1;
                r.number[i] -= 10;
            } else
                carry = 0;
        }

        if (carry > 0) {
            r.number = Arrays.copyOf(r.number, r.number.length+1);
            r.number[r.number.length-1] = 1;
        }

        return r;
    }


    /* multiply two numbers (this * y) together using divide-and-conquer technique */
    public Bignum mulBigNum(Bignum y) throws Exception {
        if (number.length == 1 && y.number.length == 1) {
            mulCounter++;
            return new Bignum(Integer.toString(number[0] * y.number[0]));
        }
        int n = Math.max(number.length,y.number.length);
        int mid = n / 2;

        Bignum Al = selectBigNum(0,mid);
        Bignum Ar = selectBigNum(mid-1, number.length-1);
        Bignum Bl = y.selectBigNum(0,mid);
        Bignum Br = y.selectBigNum(mid-1, number.length-1);

        Bignum P1 = Al.mulBigNum(Bl);
        Bignum P2 = Al.addBigNum(Ar).mulBigNum(Bl.addBigNum(Br));
        Bignum P3 = Ar.mulBigNum(Bl);

        Bignum P4 = P2.subBigNum(P1).subBigNum(P3);
        P1.addZeros(n);
        P4.addZeros(n/2);
        return P1.addBigNum(P4).addBigNum(P3);
    }

    public void addZeros(int n) {
        byte[] newarr = new byte[number.length + n];
        System.arraycopy(number, 0, newarr, newarr.length-number.length, number.length);
        number = newarr;
    }

    public void clrMulCounter() {
        mulCounter = 0;
    }

    public int rclMulCounter() {
        return (mulCounter);
    }

    public static void main(String[] args) {
        Bignum b = new Bignum("12345");
        try {
            Bignum b2 = b.mulBigNum(b);
            System.out.println(b2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
