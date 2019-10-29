package sorters;

import lombok.Setter;

public abstract class Sorter {

    static final int MERGE_SLEEP = 5, HEAP_SLEEP = 5, QUICK_SLEEP = 5, SHELL_SLEEP = 5, INSERTION_SLEEP = 5,
            BUCKET_SLEEP = 5, BUBBLE_SLEEP = 1, SELECTION_SLEEP = 4;

    @Setter
    volatile boolean paused, sorting;

    Thread sortThread;

    @Setter
    float[] sortingArray;

    @Setter
    SortedListener sortedListener;

    public Sorter(float[] sortingArray) {
        this.sortingArray = sortingArray;
    }

    Sorter() {

    }

    void swap(int from, int to) {
        float temp = sortingArray[from];
        sortingArray[from] = sortingArray[to];
        sortingArray[to] = temp;
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            //  ie.printStackTrace();
        }
    }

    public abstract void sort(float[] sortingArray);

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public void stop() {
        sorting = false;
        sortThread = null;
        sortedListener.triggerEvent();
    }
}
