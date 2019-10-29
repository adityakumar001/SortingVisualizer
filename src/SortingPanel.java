import sorters.Sorter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class SortingPanel extends JPanel {

    private static final int SCREEN_SIZE = 500;
    private static final int SQUARE_SIZE = 10, STRIP_WIDTH = 1;
    private static final double ARC_LENGTH = .5;

    boolean sorting, paused = false;
    private Options options;
    private float[] hueValues;

    SortingPanel(Options defaultOptions) {
        this.options = defaultOptions;
        initializeHSBValues();
        generateRandomColors();
        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        repaint();
    }

    void initializeHSBValues() {
        int length = getValuesLength(options.getShape());
        hueValues = new float[length];
    }

    private int getValuesLength(String shape) {
        switch (shape) {
            case Options.SQUARES:
                return SCREEN_SIZE * SCREEN_SIZE / (SQUARE_SIZE * SQUARE_SIZE);
            case Options.STRIPS:
                return SCREEN_SIZE / STRIP_WIDTH;
            case Options.WHEEL:
                return (int) (360 / ARC_LENGTH);

        }
        return 0;
    }

    void generateRandomColors() {
        Random random = new Random();
        for (int i = 0; i < hueValues.length; i++) {
            hueValues[i] = random.nextFloat();
        }

    }

    Options getOptions() {
        return options;
    }


    private void drawShapes(Graphics2D g2d) {
        Rectangle2D.Double rectangle = new Rectangle2D.Double();

        switch (options.getShape()) {

            case Options.SQUARES:
                int control = SCREEN_SIZE / SQUARE_SIZE;
                for (int i = 0; i < hueValues.length; i++) {
                    int x = (i % control) * SQUARE_SIZE;
                    int y = (i / control) * SQUARE_SIZE;
                    g2d.setColor(Color.getHSBColor(hueValues[i], 1.0f, .95f));
                    rectangle.setFrame(x, y, SQUARE_SIZE, SQUARE_SIZE);
                    g2d.fill(rectangle);
                }
                break;

            case Options.STRIPS:
                for (int i = 0; i < hueValues.length; i++) {
                    int x = (i) * STRIP_WIDTH;
                    g2d.setColor(Color.getHSBColor(hueValues[i], 1.0f, .95f));
                    rectangle.setFrame(x, 0, STRIP_WIDTH, SCREEN_SIZE);
                    g2d.fill(rectangle);

                }
                break;

            case Options.WHEEL:
                Arc2D.Double arc = new Arc2D.Double(Arc2D.PIE);
                for (int i = 0; i < hueValues.length; i++) {
                    arc.x = 0;
                    arc.y = 0;
                    arc.start = i * ARC_LENGTH;
                    arc.extent = ARC_LENGTH;
                    arc.height = SCREEN_SIZE;
                    arc.width = SCREEN_SIZE;
                    g2d.setColor(Color.getHSBColor(hueValues[i], 1.0f, 0.95f) /*hsbValues[i][0], hsbValues[i][2])*/);
                    g2d.fill(arc);
                }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawShapes(g2d);
        g2d.dispose();
    }

    void startSorting() {
        sorting = true;
        RefreshThread refreshThread = new RefreshThread(this);
        Sorter sorter = options.getSorter();
        sorter.setSorting(true);
        sorter.setSortingArray(hueValues);
        refreshThread.start();
        sorter.sort(hueValues);
    }

}