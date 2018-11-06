package lab04;

public class MinMax {

    private Comparable[] array;
    private int minI;
    private int maxI;
    private int comp;

    MinMax(Comparable[] array) {
        this.array = array;
        comp = 0;
    }

    public int getMax() {
        return maxI;
    }

    public int getMin() {
        return minI;
    }

    public int getComparisons() {
        return comp;
    }

    public void minmax() {
        int i, start;
        if (this.array.length < 1) {
            return;
        } else if (this.array.length == 1) {
            minI = 0;
            maxI = 0;
            return;
        }
        start = 1;

        if (this.array.length % 2 == 1) { // odd lenght
            minI = 0;
            maxI = 0;
        } else { // even lenght
            comp++;
            if (this.array[0].compareTo(this.array[1]) > 0) {
                minI = 1;
                maxI = 0;
            } else {
                minI = 0;
                maxI = 1;
            }
            start = 2;
        }
        // compare remaining pairs
        for (i = start; i < this.array.length; i += 2) {

            comp++;
            if (this.array[i].compareTo(this.array[i + 1]) > 0) {
                comp++;
                if (this.array[i].compareTo(this.array[maxI]) > 0) {
                    maxI = i;
                }
                comp++;
                if (this.array[i + 1].compareTo(this.array[minI]) < 0) {
                    minI = i + 1;
                }
            } else {
                comp++;
                if (this.array[i + 1].compareTo(this.array[maxI]) > 0) {
                    maxI = i + 1;
                }
                comp++;
                if (this.array[i].compareTo(this.array[minI]) < 0) {
                    minI = i;
                }
            }
        }
    }

    public void minmax2() {
        comp = 0;
        minI = 0;
        maxI = 0;
        for (int i = 1; i < array.length; i++) {
            comp++;
            if (array[i].compareTo(array[minI]) < 0) {
                minI = i;
            }
            comp++;
            if (array[i].compareTo(array[maxI]) > 0) {
                maxI = i;
            }
        }
    }
}
