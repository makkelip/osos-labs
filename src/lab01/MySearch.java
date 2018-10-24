package lab01;

import java.util.Random;

public class MySearch {
    public static class Binary implements Stopwatch.Test {

        private Comparable[] array;
        private Comparable[] searchFor;

        int search(Comparable elem) {
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

        public void setArray(Comparable[] array) {
            this.array = array;
        }

        public void setSearchables() {
            Random r = new Random();
            searchFor = new Comparable[500];
            for (int i = 0; i < 500; i++) {
                searchFor[i] = array[r.nextInt(array.length - 1)];
            }
        }

        @Override
        public void test() {
            for (Comparable e: searchFor) {
                search(e);
            }
        }
    }

    public static class Linear implements Stopwatch.Test {

        private Comparable[] array;
        private Comparable[] searchFor;

        int search(Comparable elem) {
            for (int i = 0; i < array.length; i++) {
                if (elem == array[i]) {
                    return i;
                }
            }
            return -1;
        }

        public void setArray(Comparable[] array) {
            this.array = array;
        }

        public void setSearchables() {
            Random r = new Random();
            searchFor = new Comparable[500];
            for (int i = 0; i < 500; i++) {
                searchFor[i] = array[r.nextInt(array.length - 1)];
            }
        }

        @Override
        public void test() {
            for (Comparable e: searchFor) {
                search(e);
            }
        }
    }
}
