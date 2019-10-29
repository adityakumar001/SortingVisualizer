package sorters;

public class SelectionSorter extends Sorter {
    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            for (int i = 0; i < sortingArray.length &&sorting; i++) {
                int minIndex = i;
                for (int j = i; j < sortingArray.length &&sorting; j++) {

                    while (paused) Thread.onSpinWait();

                    if (sortingArray[minIndex] > sortingArray[j]) {
                        minIndex = j;
                    }
                }
                swap(minIndex, i);
                sleep(SELECTION_SLEEP);
            }
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }

    @Override
    public String toString() {
        return "Selection Sort";
    }

}
