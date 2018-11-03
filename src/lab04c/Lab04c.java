package lab04c;

import java.util.Arrays;
import java.util.Scanner;

public class Lab04c {

    static int k = -1;
    static int[] board;
    static int size;
    static int tiles;
    static int[] defLoc = {-1,-1};
    static int tileNum = 1;

    static void printBoard() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                sb.append(board[size * j + i]);
                sb.append('\t');
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    static void tileBoard(int tr, int tc, int dr, int dc, int size) {
        if (size == 2) {
            setTiles(tr, tc, dr, dc);
        } else {
            int newSize = size / 2;
            int sr = tr + newSize;
            int sc = tc + newSize;
            int defr = sr;
            int defc = sc;
            if (dr < sr) defr--;
            if (dc < sc) defc--;
            setTiles(sr - 1, sc - 1, defr, defc);

            //top left
            if (dr >= sr || dc >= sc)
                tileBoard(tr, tc, sr - 1, sc - 1, newSize);
            else
                tileBoard(tr, tc, dr, dc, newSize);
            //top right
            if (dr >= sr || dc <= sc)
                tileBoard(tr, sc, sr - 1, sc, newSize);
            else
                tileBoard(tr, sc, dr, dc, newSize);
            //bottom left
            if (dr <= sr || dc >= sc)
                tileBoard(sr, tc, sr, sc - 1, newSize);
            else
                tileBoard(sr, tc, dr, dc, newSize);
            //bottom right
            if (dr <= sr || dc <= sc)
                tileBoard(sr, sc, sr, sc, newSize);
            else
                tileBoard(sr, sc, dr, dc, newSize);
        }
    }

    static void setTiles(int tr, int tc, int dr, int dc) {
        for (int c = tc, c2 = c+2; c < c2; c++) {
            for (int r = tr, r2 = r+2; r < r2; r++) {
                if (r != dr || c != dc)
                    board[size * r + c] = tileNum;
            }
        }
        tileNum++;
    }
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        while (k < 1 || k > 6) {
            System.out.println("Enter k, board size is 2^k\nk should be in the range through 0 6");
            k = reader.nextInt();
        }
        //width & height
        size = (int)Math.pow(2,k);
        while (defLoc[0] > size-1 || defLoc[0] < 0 || defLoc[1] > size-1 || defLoc[1] < 0) {
            System.out.println("Enter location of defect");
            System.out.print("Row: ");
            defLoc[1] = reader.nextInt() - 1;
            System.out.print("Column: ");
            defLoc[0] = reader.nextInt() - 1;
        }
        //actual board
        board = new int[size * size];
        //amount of tiles
        tiles = ((int)Math.pow(size, 2) - 1)/3;
        //create board
        tileBoard(0,0,defLoc[1], defLoc[0], size);
        printBoard();
        Img.createImg(size, tiles, board);
    }
}
