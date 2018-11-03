package lab04c;

import java.util.Arrays;
import java.util.Scanner;

public class Lab04c {

    static int k = -1;
    static int[] board;
    static int size;
    static int tiles;
    static int[] defLoc = new int[2];
    static int tileNum = 1;

    static void printBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[size * i + j]);
                sb.append(" ");
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    static void tileBoard(int tr, int tc, int dr, int dc, int size) {
        if (size == 2) {
            setTiles(tr, tc, dr, dc);
        } else {
            int sr = tr + size / 2;
            int sc = tc + size / 2;
            int defr = sr;
            int defc = sc;
            if (dr >= sr) defr++;
            if (dc >= sc) defc++;
            setTiles(sr - 1, sc - 1, defr, defc);

            if (dr >= sr && dc >= sc)
                tileBoard(tr, tc, sr-1, sc-1, size / 2);
            else
                tileBoard(tr, tc, dr, dc, size / 2);

            if (dr <= sr && dc >= sc)
                tileBoard(tr, sc, sr, sc-1, size / 2);
            else
                tileBoard(tr, sc, dr, dc, size / 2);

            if (dr >= sr && dc <= sc)
                tileBoard(sr, tc, sr-1, sc, size / 2);
            else
                tileBoard(sr, tc, dr, dc, size / 2);

            if (dr <= sr && dc <= sc)
                tileBoard(sr, sc, sr, sc, size / 2);
            else
                tileBoard(sr, sc, dr, dc, size / 2);
        }
    }

    static void setTiles(int tr, int tc, int dr, int dc) {
        for (int r = tr, r2 = r+2; r < r2; r++) {
            for (int c = tc, c2 = c+2; c < c2; c++) {
                if (r != dr && r != dc)
                    System.out.println(tileNum);
                    board[size * tc + tr] = tileNum;
            }
        }
        tileNum++;
    }
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter k, board size is 2^k\nk should be in the range through 0 6");
        while (k < 1 || k > 6)
            k = reader.nextInt();
        System.out.println("Enter location of defect");
        defLoc[0] = reader.nextInt();
        defLoc[1] = reader.nextInt();
        size = (int)Math.pow(2,k);
        board = new int[size * size];
        tiles = ((int)Math.pow(size, 2) - 1)/3;
        tileBoard(1,1,defLoc[1], defLoc[0], size);
        board[size * defLoc[1] + defLoc[0]] = 2;
        printBoard();
    }
}
