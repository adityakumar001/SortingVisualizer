package sorters;

public class MergeSorter extends Sorter {

    private void merge(float[] leftArray, float[] rightArray, int beg, int end) {
        int i = beg, lCounter = 0, rCounter = 0;
        while (i <= end && lCounter < leftArray.length && rCounter < rightArray.length) {
            while (paused) Thread.onSpinWait();

            if (rightArray[rCounter] < leftArray[lCounter]) {
                sortingArray[i] = rightArray[rCounter++];
            } else {
                sortingArray[i] = leftArray[lCounter++];
            }

            i++;
            sleep(MERGE_SLEEP);

        }
        while (lCounter < leftArray.length && i <= end) {
            while (paused) Thread.onSpinWait();

            sortingArray[i++] = leftArray[lCounter++];
            sleep(MERGE_SLEEP);

        }
        while (rCounter < rightArray.length && i <= end) {
            while (paused) Thread.onSpinWait();

            sortingArray[i++] = rightArray[rCounter++];
            sleep(MERGE_SLEEP);
        }
    }

    private void mergeSort(int beg, int end) {

        if (beg < end && sorting) {
            int mid = (beg + end) / 2;
            float[] leftArray = new float[mid - beg + 1];
            float[] rightArray = new float[end - mid];
            mergeSort(beg, mid);
            mergeSort(mid + 1, end);
            System.arraycopy(sortingArray, beg, leftArray, 0, leftArray.length);
            System.arraycopy(sortingArray, mid + 1, rightArray, 0, rightArray.length);
            merge(leftArray, rightArray, beg, end);

        }

    }

    @Override
    public String toString() {
        return "Merge Sort";
    }

    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            mergeSort(0, sortingArray.length - 1);
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }
}
