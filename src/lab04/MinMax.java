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

    public void minmax() {// min and max element locations (indexes) are returned
        comp = 0;
        int[] a = split(array, 0, array.length - 1);
        minI = a[0];
        maxI = a[1];
        System.out.println("min: " + array[minI] + " max: " + array[maxI]);
    }

    public int[] split(Comparable[] arr, int i, int j) {
        int resArr[] = new int[2];
        int mid;

        if (i == j) {
            resArr[0] = resArr[1] = i;
        } else {
            mid = (i + j) / 2;
            //Devide
            int[] left = split(arr, i, mid);
            int[] right = split(arr, mid+1, j);
            //Combine
            //bigger
            comp++;
            if (arr[left[1]].compareTo(arr[right[1]]) > 0)
                resArr[1] = left[1];
            else
                resArr[1] = right[1];
            //smaller
            comp++;
            if (arr[left[0]].compareTo(arr[right[0]]) < 0)
                resArr[0] = left[0];
            else
                resArr[0] = right[0];
        }
        return resArr;
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
        System.out.println("min: " + array[minI] + " max: " + array[maxI]);
    }
}
