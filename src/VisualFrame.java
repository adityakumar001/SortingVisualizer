import javax.swing.*;
import java.awt.*;

public class VisualFrame extends JFrame {

    public static void main(String[] args) {

        SortingPanel sortingPanel = new SortingPanel(Options.DEFAULTS);
        OptionsPanel optionsPanel = new OptionsPanel(sortingPanel);
        JFrame frame = new JFrame("Sorting Visualizer");
        frame.add(sortingPanel, BorderLayout.CENTER);
        frame.add(optionsPanel, BorderLayout.SOUTH);
        frame.setResizable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
