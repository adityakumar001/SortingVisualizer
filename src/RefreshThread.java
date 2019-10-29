public class RefreshThread extends Thread {
    public static final double FPS = 120;
    private SortingPanel sortingPanel;

    RefreshThread(SortingPanel sortingPanel) {
        this.sortingPanel = sortingPanel;
    }

    @Override
    public void run() {

        long recordedTime = System.nanoTime();
        double delta = 0;

        while (sortingPanel.sorting) {
            while (sortingPanel.paused && sortingPanel.sorting) {
                Thread.onSpinWait();
            }

            if ((delta += (System.nanoTime() - recordedTime) / Math.pow(10, 9)) > 1 / FPS) {
                sortingPanel.repaint();
                recordedTime = System.nanoTime();
                delta = 0;
            }

        }

    }
}
