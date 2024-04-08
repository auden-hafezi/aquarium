package ui;

import model.Person;
import model.SeaCreature;
import model.sea_creatures.Goldfish;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.AquariumTab;
import ui.tabs.SeaCreatureTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Represents the graphical ui for an aquarium game.
public class Aquarium extends JFrame {
    private static final ImageIcon ICON = new ImageIcon("./data/images/whale shark.png");
    private static final Color COLOUR = Color.decode("#3388ff");
    private static final String FILE = "./data/aquarium.json";
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;
    private static final int AQUARIUM_INDEX = 0;
    private static final int SEA_CREATURE_INDEX = 1;

    private JTabbedPane tabs;
    private JsonWriter writer;
    private JsonReader reader;

    // EFFECTS: runs the Aquarium
    public static void main(String[] args) {
        new Aquarium();
    }

    // EFFECTS: creates a new Aquarium
    public Aquarium() {
        writer = new JsonWriter(FILE);
        reader = new JsonReader(FILE);
        load();

        setTitle("aquarium");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createTabbedPane();
        add(tabs);
        addMenu();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads the save state of the game
    // if the person has no pets, adds a goldfish
    private void load() {
        try {
            reader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "sorry, unable to load! (;-;)",
                    "load error", JOptionPane.INFORMATION_MESSAGE, ICON);
        }

        List<SeaCreature> pets = Person.getInstance().getPets();
        if (pets.size() == 0) {
            initialize();
        }
    }

    private void initialize() {
        Goldfish goldfish = new Goldfish("goldfish");
        Person.getInstance().addPet(goldfish);

        JOptionPane.showMessageDialog(null, "welcome to your aquarium! (^-^*)",
                "aquarium", JOptionPane.INFORMATION_MESSAGE, ICON);
        int response = JOptionPane.showOptionDialog(null, "would you like to skip the " +
                        "tutorial?",
                "aquarium", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, ICON, null,
                null);

        if (response == 1) {
            JOptionPane.showMessageDialog(null, "collect sea dollars by playing with and " +
                    "feeding your sea creatures!", "tutorial", JOptionPane.INFORMATION_MESSAGE, ICON);
            JOptionPane.showMessageDialog(null, "to unlock new sea creatures, earn xp by " +
                    "buying treats for your sea creatures", "tutorial", JOptionPane.INFORMATION_MESSAGE, ICON);
            JOptionPane.showMessageDialog(null, "collect all five sea creatures to complete " +
                    "your aquarium!", "tutorial", JOptionPane.INFORMATION_MESSAGE, ICON);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates tabbed pane
    private void createTabbedPane() {
        tabs = new JTabbedPane();
        JPanel aquariumTab = new AquariumTab();
        JPanel seaCreatureTab = new SeaCreatureTab();

        tabs.add(aquariumTab, AQUARIUM_INDEX);
        tabs.setTitleAt(AQUARIUM_INDEX, "aquarium");
        tabs.add(seaCreatureTab, SEA_CREATURE_INDEX);
        tabs.setTitleAt(SEA_CREATURE_INDEX, "sea creatures");

        tabs.setTabPlacement(JTabbedPane.LEFT);
        tabs.setBackground(COLOUR);
    }

    // EFFECTS: adds the menu bar to the Aquarium
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("file");
        addMenuItem(fileMenu, new SaveAction());
        addMenuItem(fileMenu, new ResetProgressAction());

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    // EFFECTS: adds an item with the given handler to the menu bar
    private void addMenuItem(JMenu menu, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        menu.add(menuItem);
    }

    // Represents a save action to save the aquarium.
    private class SaveAction extends AbstractAction {

        // EFFECTS: creates a new SaveAction titled "save"
        SaveAction() {
            super("save");
        }

        // EFFECTS: saves the state of the application to file
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                writeToFile();
                String message = "saved! yippee! (^-^*)";
                JOptionPane.showMessageDialog(null, message, "save",
                        JOptionPane.INFORMATION_MESSAGE, ICON);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "couldn't save", "save error",
                        JOptionPane.ERROR_MESSAGE, ICON);
            }
        }
    }

    // Represents a reset progress action to clear save data.
    private class ResetProgressAction extends AbstractAction {

        // EFFECTS: creates a new ResetProgressAction titled "reset progress"
        ResetProgressAction() {
            super("reset progress");
        }

        // MODIFIES: this
        // EFFECTS: clears the saved data from file
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                Person.getInstance().reset();
                writeToFile();
                load();
                JOptionPane.showMessageDialog(null, "progress reset!", "reset",
                        JOptionPane.INFORMATION_MESSAGE, ICON);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "sorry, unable to reset progress! (;-;)",
                        "reset error", JOptionPane.INFORMATION_MESSAGE, ICON);
            }
        }
    }

    // EFFECTS: writes the current state of the game to file
    private void writeToFile() throws FileNotFoundException {
        writer.open();
        writer.write();
        writer.close();
    }
}
