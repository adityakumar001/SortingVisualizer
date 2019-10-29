package sorters;

public abstract class SortedListener {
    public abstract void itemsSorted();

    void triggerEvent() {
        itemsSorted();
    }
}
