package sorters;

public class BubbleSorter extends Sorter {

    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            for (int i = 0; i < sortingArray.length && sorting; i++) {
                for (int j = 0; j < sortingArray.length - i - 1 && sorting; j++) {
                    while (paused) Thread.onSpinWait();

                    if (sortingArray[j] > sortingArray[j + 1]) {
                        swap(j, j + 1);
                        sleep(BUBBLE_SLEEP);
                    }
                }
            }
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }

    @Override
    public String toString() {
        return "Bubble Sort";
    }
}
