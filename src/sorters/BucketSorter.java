package sorters;

public class BucketSorter extends Sorter {
    @Override
    public void sort(float[] sortingArray) {
        sortThread = new Thread(this::bucketSort);
        sortThread.start();

    }

    private void bucketSort() {
        Node[] buckets = new Node[sortingArray.length];
        for (float v : sortingArray) {
            int index = (int) (v * sortingArray.length);
            if (buckets[index] == null) {
                buckets[index] = new Node(v);
            } else {
                Node current = buckets[index];
                while (current.next != null && current.next.value < v) {
                    current = current.next;
                }
                if (current.next == null) {
                    current.next = new Node(v);
                } else {
                    Node currentNext = current.next;
                    current.next = new Node(v);
                    current.next.next = currentNext;
                }
            }
        }
        int j = 0;
        for (Node bucket : buckets) {
            Node current = bucket;
            while (current != null && j < sortingArray.length && sorting) {
                while (paused) Thread.onSpinWait();
                sortingArray[j++] = current.value;
                current = current.next;
                sleep(BUCKET_SLEEP);
            }
        }
        sortedListener.triggerEvent();
    }

    @Override
    public String toString() {
        return "Bucket Sort";
    }

    private static class Node {
        float value;
        Node next;

        Node(float value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "\t";
        }
    }
}
