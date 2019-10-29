package sorters;

public class QuickSorter extends Sorter {

    private int partition(int beg, int end) {
        float pivot = sortingArray[end];
        int loc = beg - 1;

        for (int i = beg; i < end; i++) {
            while (paused) Thread.onSpinWait();
            if (sortingArray[i] < pivot) {
                swap(++loc, i);
                sleep(QUICK_SLEEP);
            }
        }
        swap(++loc, end);
        return loc;
    }

    private void quickSort(int beg, int end) {
        if (beg < end && sorting) {
            int partition = partition(beg, end);
            quickSort(beg, partition - 1);
            quickSort(partition + 1, end);
        }
    }

    @Override
    public String toString() {
        return "Quick Sort";
    }

    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            quickSort(0, sortingArray.length - 1);
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }
}
