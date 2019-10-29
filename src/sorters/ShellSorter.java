package sorters;

public class ShellSorter extends Sorter {
    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            int h = 1;
            while (h < sortingArray.length / 3) h = 3 * h + 1;
            for (int gap = (h); gap > 0 && sorting; gap /= 3) {
                for (int i = gap; i < sortingArray.length && sorting; i += gap) {
                    float toPlace = sortingArray[i];
                    int j = i - gap;
                    while (j >= 0 && sortingArray[j] > toPlace && sorting) {
                        while (paused) Thread.onSpinWait();
                        sortingArray[j + gap] = sortingArray[j];
                        j -= gap;
                    }
                    sortingArray[j + gap] = toPlace;
                    sleep(SHELL_SLEEP);
                }
            }
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }

    @Override
    public String toString() {
        return "Shell Sort";
    }

}
