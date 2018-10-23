package lab01;

public class MySearch implements Stopwatch.Test {

    private Comparable[] array;
    private boolean binaryS;
    private Integer find;

    public MySearch(boolean binarySearch) {
        this.binaryS = binarySearch;
        find = 0;
    }

    public void setFind(Integer integer) {
        find = integer;
    }

    void setArray(Comparable[] array) {
        this.array = array;
    }

    int LinearSearch(Comparable elem) {
        for (int i = 0; i < array.length; i++) {
            if (elem.compareTo(array[i]) == 0) {
                return i;
            }
        }
        return -1;
    }

    int BinarySearch(Comparable elem) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int ix = (low + high) / 2;
            if (elem.compareTo(array[ix]) == 0) {
                return ix;
            } else if (elem.compareTo(array[ix]) > 0) {
                low = ix + 1;
            } else {
                high = ix - 1;
            }
        }
        return -1;
    }

    @Override
    public void test() {
        if (binaryS) {
            for (int i = 0; i < 500; i++) {
                BinarySearch(find);
            }
        } else {
            for (int i = 0; i < 500; i++) {
                LinearSearch(find);
            }
        }
    }



}
