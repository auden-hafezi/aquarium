package ui.tabs;

import model.Observer;
import model.Person;
import model.SeaCreature;
import ui.StatsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static model.Person.EVENT_SEA_CREATURE;

// A tab showing the user's aquarium and stats.
public class AquariumTab extends Tab implements Observer {
    private static final ImageIcon GOLDFISH_ICON = new ImageIcon("./data/images/goldfish tank.png");
    private static final ImageIcon JELLYFISH_ICON = new ImageIcon("./data/images/jellyfish tank.png");
    private static final ImageIcon STINGRAY_ICON = new ImageIcon("./data/images/stingray tank.png");
    private static final ImageIcon WHALE_SHARK_ICON = new ImageIcon("./data/images/whale shark tank.png");
    private static final ImageIcon SHARK_ICON = new ImageIcon("./data/images/shark tank.png");

    private JLabel aquariumIconLabel;

    // EFFECTS: creates new AquariumTab
    public AquariumTab() {
        super();

        StatsPanel statsPanel = new StatsPanel();
        add(statsPanel, BorderLayout.NORTH);

        addAquariumIcon();

        Person.getInstance().addObserver(this);
    }

    // MODIFIES: this
    // EFFECTS: adds the appropriate initial aquarium icon to the tab
    private void addAquariumIcon() {
        ImageIcon icon = getAquariumIcon();
        aquariumIconLabel = new JLabel(icon);
        add(aquariumIconLabel, BorderLayout.CENTER);
    }

    // EFFECTS: returns the aquarium image containing the correct sea creatures
    private ImageIcon getAquariumIcon() {
        List<SeaCreature> pets = Person.getInstance().getPets();
        int numPets = pets.size();
        switch (numPets) {
            case 1:
                return GOLDFISH_ICON;
            case 2:
                return JELLYFISH_ICON;
            case 3:
                return STINGRAY_ICON;
            case 4:
                return WHALE_SHARK_ICON;
            default:
                return SHARK_ICON;
        }
    }

    @Override
    public void update(Object event) {
        if (EVENT_SEA_CREATURE.equals(event)) {
           aquariumIconLabel.setIcon(getAquariumIcon());
           repaint();
        }
    }
}
