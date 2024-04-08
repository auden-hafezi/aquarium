package ui;

import model.Observer;
import model.Person;

import javax.swing.*;
import java.awt.*;
import static ui.tabs.Tab.COLOUR;

// A panel to show the user's stats.
public class StatsPanel extends JPanel implements Observer {
    private static final String SEA_DOLLARS_TEXT = "sea dollars: ";
    private static final String XP_TEXT = "xp: ";
    private static final String FONT_NAME = "Sans Serif";
    private static final int FONT_SIZE = 14;
    private static final Font FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);


    private JLabel seaDollarsLabel;
    private JLabel xpLabel;

    // EFFECTS: creates a new stats panel
    // sets background colours and creates initial labels
    public StatsPanel() {
        setBackground(COLOUR);

        seaDollarsLabel = new JLabel(SEA_DOLLARS_TEXT + Person.getInstance().getSeaDollars());
        xpLabel = new JLabel(XP_TEXT + Person.getInstance().getXP());

        seaDollarsLabel.setFont(FONT);
        xpLabel.setFont(FONT);

        add(seaDollarsLabel);
        add(Box.createHorizontalStrut(50));
        add(xpLabel);

        Person.getInstance().addObserver(this);
    }

    @Override
    public void update(Object event) {
        if (Person.EVENT_STATS_CHANGED.equals(event)) {
            seaDollarsLabel.setText(SEA_DOLLARS_TEXT + Person.getInstance().getSeaDollars());
            xpLabel.setText(XP_TEXT + Person.getInstance().getXP());
            repaint();
        }
    }
}
