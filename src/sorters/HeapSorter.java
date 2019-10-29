package sorters;

public class HeapSorter extends Sorter {

    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(() -> {
            heapify(sortingArray, 0, sortingArray.length);
            for (int i = 0; i < sortingArray.length && sorting; i++) {
                while (paused) Thread.onSpinWait();
                swap(0, sortingArray.length - i - 1);
                sleep(HEAP_SLEEP);
                heapify(sortingArray, 0, sortingArray.length - 1 - i);
            }
            sortedListener.triggerEvent();
        });
        sortThread.start();
    }

    @Override
    public String toString() {
        return "Heap Sort";
    }

    private void heapify(float[] heap, int root, int size) {
        int leftChild = 2 * root + 1, rightChild = 2 * root + 2;
        if (rightChild < size) {
            heapify(heap, leftChild, size);
            heapify(heap, rightChild, size);
            if (heap[leftChild] > heap[root]) {
                if (heap[leftChild] > heap[rightChild]) {
                    swap(leftChild, root);
                } else swap(rightChild, root);
            } else if (heap[rightChild] > heap[root]) {
                swap(rightChild, root);
            }
        }
    }

}
