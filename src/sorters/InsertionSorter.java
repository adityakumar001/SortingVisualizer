package sorters;

public class InsertionSorter extends Sorter {
    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            for (int i = 1; i < sortingArray.length && sorting; i++) {
                float toPlace = sortingArray[i];
                int j = i - 1;
                while (j > -1 && sortingArray[j] > toPlace && sorting) {
                    while (paused) Thread.onSpinWait();
                    sortingArray[j + 1] = sortingArray[j--];
                }
                sortingArray[j + 1] = toPlace;
                sleep(INSERTION_SLEEP);
            }
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }

    @Override
    public String toString() {
        return "Insertion Sort";
    }

}
