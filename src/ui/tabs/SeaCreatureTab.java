package ui.tabs;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Observer;
import model.Person;
import model.SeaCreature;
import model.Species;
import model.sea_creatures.*;
import ui.SeaCreatureList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static model.Person.*;

// A tab containing the list of the user's sea creatures and buttons to interact with them.
public class SeaCreatureTab extends Tab implements Observer {
    private static final ImageIcon STRAWBERRY_SHARK = new ImageIcon("./data/images/strawberry shark icon.png");
    private static final ImageIcon GOLDFISH_ICON = new ImageIcon("./data/images/goldfish.png");
    private static final ImageIcon JELLYFISH_ICON = new ImageIcon("./data/images/jellyfish.png");
    private static final ImageIcon STINGRAY_ICON = new ImageIcon("./data/images/stingray.png");
    private static final ImageIcon WHALE_SHARK_ICON = new ImageIcon("./data/images/whale shark.png");
    private static final ImageIcon SHARK_ICON = new ImageIcon("./data/images/shark.png");


    private SeaCreatureList seaCreatureList;
    private JPanel buttonPanel;

    // EFFECTS: creates a new SeaCreatureTab
    public SeaCreatureTab() {
        super();
        seaCreatureList = new SeaCreatureList();
        seaCreatureList.loadSeaCreatures();


        createButtonPanel();
        add(seaCreatureList, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        Person.getInstance().addObserver(this);
    }

    // MODIFIES: this
    // EFFECTS: creates a button panel
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));
        buttonPanel.add(new JButton(new InfoAction()));
        buttonPanel.add(new JButton(new RenameAction()));
        buttonPanel.add(new JButton(new FeedAction()));
        buttonPanel.add(new JButton(new TreatAction()));
        buttonPanel.add(new JLabel(STRAWBERRY_SHARK));
        buttonPanel.add(new JButton(new PlayAction()));
        buttonPanel.setBackground(COLOUR);
    }

    // Represents an action to show information about a sea creature.
    private class InfoAction extends AbstractAction {

        // EFFECTS: creates a new InfoAction titled "info"
        InfoAction() {
            super("info");
        }

        // EFFECTS: shows the given sea creature's information
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = seaCreatureList.getSelectedIndex();
            if (index >= 0) {
                SeaCreature seaCreature = Person.getInstance().getPets().get(index);
                ImageIcon icon = getSpeciesIcon(seaCreature.getSpecies());
                Species species = seaCreature.getSpecies();

                int foodCost = getFoodCost(species);
                int treatCost = getTreatCost(species);

                String message = "name: " + seaCreature.getName() +
                        "\nspecies: " + Species.speciesToString(species) +
                        "\nhungry: " + seaCreature.isHungry() +
                        "\n\nfood cost: " + foodCost + " sea dollars" +
                        "\ntreat cost: " + treatCost + " sea dollars";

                JOptionPane.showMessageDialog(null, message, "info",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            }
        }
    }

    // EFFECTS: returns the food cost for the given species of sea creature
    private int getFoodCost(Species species) {
        switch (species) {
            case GOLDFISH:
                return Goldfish.FOOD_COST;
            case JELLYFISH:
                return Jellyfish.FOOD_COST;
            case STINGRAY:
                return Stingray.FOOD_COST;
            case WHALE_SHARK:
                return WhaleShark.FOOD_COST;
            default:
                return Shark.FOOD_COST;
        }
    }

    // EFFECTS: returns the treat cost for the given species of sea creature
    private int getTreatCost(Species species) {
        switch (species) {
            case GOLDFISH:
                return Goldfish.TREAT_COST;
            case JELLYFISH:
                return Jellyfish.TREAT_COST;
            case STINGRAY:
                return Stingray.TREAT_COST;
            case WHALE_SHARK:
                return WhaleShark.TREAT_COST;
            default:
                return Shark.TREAT_COST;
        }
    }

    // Represents an action to rename a sea creature.
    private class RenameAction extends AbstractAction {

        // EFFECTS: creates a new RenameAction titled "rename"
        RenameAction() {
            super("rename");
        }

        // MODIFIES: this
        // EFFECTS: changes the selected sea creature's name to the given name
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = seaCreatureList.getSelectedIndex();
            if (index >= 0) {
                SeaCreature seaCreature = Person.getInstance().getPets().get(index);
                ImageIcon icon = getSpeciesIcon(seaCreature.getSpecies());

                String name = (String) JOptionPane.showInputDialog(null,
                        "please enter a name:", "rename", JOptionPane.INFORMATION_MESSAGE,
                        icon, null, null);

                if (name != null && !name.equals("")) {
                    seaCreature.setName(name);
                    seaCreatureList.loadSeaCreatures();
                }
            }
        }
    }

    // Represents an action to feed a sea creature.
    private class FeedAction extends AbstractAction {

        // EFFECTS: creates a new FeedAction titled "feed"
        FeedAction() {
            super("feed");
        }

        // MODIFIES: this
        // EFFECTS: feeds the selected sea creature if it is hungry
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = seaCreatureList.getSelectedIndex();
            if (index >= 0) {
                SeaCreature seaCreature = Person.getInstance().getPets().get(index);
                ImageIcon icon = getSpeciesIcon(seaCreature.getSpecies());

                try {
                    seaCreature.feed();
                    String message = seaCreature.getName() + " has been fed! (^-^*)";
                    JOptionPane.showMessageDialog(null, message, "feed",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                } catch (NotHungryException ex) {
                    String message = seaCreature.getName() + " is not hungry";
                    JOptionPane.showMessageDialog(null, message, "feed",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
        }
    }

    // Represents an action to give a treat to a sea creature.
    private class TreatAction extends AbstractAction {

        // EFFECTS: creates a new TreatAction titled "treat"
        TreatAction() {
            super("treat");
        }

        // MODIFIES: this
        // EFFECTS: gives the selected sea creature a treat if the user can afford to.
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = seaCreatureList.getSelectedIndex();
            if (index >= 0) {
                SeaCreature seaCreature = Person.getInstance().getPets().get(index);
                ImageIcon icon = getSpeciesIcon(seaCreature.getSpecies());

                try {
                    seaCreature.treat();
                    String message = seaCreature.getName() + " has been given a treat! yippee! (^-^*)";
                    JOptionPane.showMessageDialog(null, message, "treat",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                } catch (SeaDollarException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "treat",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
        }
    }

    // Represents an action to play with a sea creature.
    private class PlayAction extends AbstractAction {

        // EFFECTS: creates a new RenameAction titled "play"
        PlayAction() {
            super("play");
        }

        // MODIFIES: this
        // EFFECTS: if possible, plays with the selected sea creature
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = seaCreatureList.getSelectedIndex();
            if (index >= 0) {
                SeaCreature seaCreature = Person.getInstance().getPets().get(index);
                ImageIcon icon = getSpeciesIcon(seaCreature.getSpecies());

                try {
                    seaCreature.play();
                    JOptionPane.showMessageDialog(null, "yippee!!", "play",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                } catch (HungryException ex) {
                    String message = seaCreature.getName() + " is too hungry to play! (;-;)";
                    JOptionPane.showMessageDialog(null, message, "play",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
        }
    }

    // EFFECTS: returns the icon for the given species
    public ImageIcon getSpeciesIcon(Species species) {
        switch (species) {
            case GOLDFISH:
                return GOLDFISH_ICON;
            case JELLYFISH:
                return JELLYFISH_ICON;
            case STINGRAY:
                return STINGRAY_ICON;
            case WHALE_SHARK:
                return WHALE_SHARK_ICON;
            default:
                return SHARK_ICON;
        }
    }

    @Override
    public void update(Object event) {
        if (EVENT_JELLYFISH.equals(event)) {
            JOptionPane.showMessageDialog(null, "woah! jellyfish unlocked!",
                    "new sea creature",
                    JOptionPane.INFORMATION_MESSAGE, getSpeciesIcon(Species.JELLYFISH));
        } else if (EVENT_STINGRAY.equals(event)) {
            JOptionPane.showMessageDialog(null, "woah! stingray unlocked!",
                    "new sea creature",
                    JOptionPane.INFORMATION_MESSAGE, getSpeciesIcon(Species.STINGRAY));
        } else if (EVENT_WHALE_SHARK.equals(event)) {
            JOptionPane.showMessageDialog(null, "woah! whale shark unlocked!",
                    "new sea creature",
                    JOptionPane.INFORMATION_MESSAGE, getSpeciesIcon(Species.WHALE_SHARK));
        } else if (EVENT_SHARK.equals(event)) {
            JOptionPane.showMessageDialog(null, "woah! shark unlocked!",
                    "new sea creature",
                    JOptionPane.INFORMATION_MESSAGE, getSpeciesIcon(Species.SHARK));
            JOptionPane.showMessageDialog(null,
                    "congratulations! you've collected all the sea creatures!! (^-^*)",
                    "aquarium complete",
                    JOptionPane.INFORMATION_MESSAGE, getSpeciesIcon(Species.SHARK));
        }
    }
}
