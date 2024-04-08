package ui.tabs;

import javax.swing.*;
import java.awt.*;

// Represents a tab in the aquarium's graphical user interface.
public abstract class Tab extends JPanel {
    public static final Color COLOUR = Color.decode("#d1edff");

    // EFFECTS: creates a new Tab
    public Tab() {
        setLayout(new BorderLayout());
        setBackground(COLOUR);
    }
}
