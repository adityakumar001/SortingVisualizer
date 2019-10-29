import lombok.*;
import sorters.*;

import javax.swing.*;
import java.awt.*;



class OptionsPanel extends JPanel {
    OptionsPanel(SortingPanel sortingPanel) {
        setLayout(new GridLayout(2, 4));

        JLabel algorithmLabel = new JLabel("Use Algorithm");
        JComboBox<Sorter> sortAlgorithmComboBox = new JComboBox<>(new Sorter[]{
                Options.BUBBLE, Options.HEAP, Options.SHELL, Options.SELECTION,
                Options.BUCKET, Options.INSERTION, Options.MERGE, Options.QUICK
        });

        JLabel shapeLabel = new JLabel("Shape");
        JComboBox<String> shapeComboBox =
                new JComboBox<>(new String[]{Options.STRIPS, Options.SQUARES, Options.WHEEL});

        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");
        JButton randomColorsButton = new JButton("Random colors");
        JButton sortButton = new JButton("Sort !!");

        shapeComboBox.addItemListener(item -> {
            sortingPanel.getOptions().setShape((String) item.getItem());

            sortingPanel.initializeHSBValues();
            sortingPanel.generateRandomColors();
            sortingPanel.repaint();
        });


        sortButton.addActionListener(e -> {
            Sorter sorter = sortAlgorithmComboBox.getItemAt(sortAlgorithmComboBox.getSelectedIndex());
            sorter.setSortedListener(
                    new SortedListener() {
                        public void itemsSorted() {
                            sortingPanel.sorting = false;
                            setEnabled(true, shapeComboBox, sortAlgorithmComboBox, randomColorsButton);
                        }
                    }
            );
            sortingPanel.getOptions().setSorter(sorter);
            setEnabled(false, shapeComboBox, sortAlgorithmComboBox, randomColorsButton);
            sortingPanel.startSorting();
        });
        randomColorsButton.addActionListener(e -> {
            sortingPanel.initializeHSBValues();
            sortingPanel.generateRandomColors();
            sortingPanel.repaint();
        });

        stopButton.addActionListener(e -> {
            sortingPanel.getOptions().getSorter().stop();
            setEnabled(true, shapeComboBox, sortAlgorithmComboBox, randomColorsButton);
            if (pauseButton.getText().equals("Resume"))
                pauseButton.setText("Pause");
            sortingPanel.getOptions().setSorter(null);
        });

        pauseButton.addActionListener(e -> {
            if (pauseButton.getText().equals("Pause")) {
                sortingPanel.paused = true;
                sortingPanel.getOptions().getSorter().pause();
                pauseButton.setText("Resume");
            } else {
                sortingPanel.paused = false;
                sortingPanel.getOptions().getSorter().resume();
                pauseButton.setText("Pause");
            }

        });

        addComponents(algorithmLabel, sortAlgorithmComboBox, shapeLabel, shapeComboBox);

        addComponents(pauseButton, stopButton, randomColorsButton, sortButton);
    }

    private static void setEnabled(boolean status, JComponent... components) {
        for (JComponent component : components) {
            component.setEnabled(status);
        }
    }


    private void addComponents(JComponent... components) {
        for (JComponent component : components) {
            add(component);
        }
    }

}

@ToString
@AllArgsConstructor
@NoArgsConstructor
class Options {

    public static final Options DEFAULTS = new Options(Options.BUBBLE, Options.WHEEL);
    static final Sorter HEAP = new HeapSorter();
    static final String SQUARES = "Squares", STRIPS = "Strips", WHEEL = "Color Wheel";
    static final Sorter SELECTION = new SelectionSorter(), BUBBLE = new BubbleSorter(), BUCKET = new BucketSorter(), INSERTION = new InsertionSorter(),
            SHELL = new ShellSorter(), MERGE = new MergeSorter(), QUICK = new QuickSorter();
    @Setter
    @Getter
    private Sorter sorter;

    @NonNull
    @Getter
    @Setter
    private String shape;

}
