package ui;

import model.Observer;
import model.Person;
import model.SeaCreature;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

import static model.Person.EVENT_SEA_CREATURE;
import static ui.tabs.Tab.COLOUR;

// A panel for the list of the user's sea creatures.
public class SeaCreatureList extends JPanel implements ListSelectionListener, Observer {
    private static final String FONT_NAME = "Sans Serif";
    private static final int FONT_SIZE = 14;
    private static final Font FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);

    private JList list;
    private DefaultListModel listModel;


    // EFFECTS: creates a new list
    public SeaCreatureList() {
        super(new BorderLayout());
        listModel = new DefaultListModel<>();
        initializeList();
        JScrollPane listScrollPane = new JScrollPane(list);

        JLabel title =  new JLabel("\tsea creatures: ");
        title.setFont(FONT);

        add(title, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        setBackground(COLOUR);

        Person.getInstance().addObserver(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes the list
    private void initializeList() {
        list = new JList(listModel);
        list.setFont(FONT);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(-1);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
    }

    // MODIFIES: this
    // EFFECTS: loads the cafe list from file
    public void loadSeaCreatures() {
        List<SeaCreature> seaCreatures = Person.getInstance().getPets();
        listModel.clear();
        for (SeaCreature seaCreature : seaCreatures) {
            String name = seaCreature.getName();
            listModel.addElement(name);
        }
    }

    // EFFECTS: returns the index of the value selected in the list
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }


    // required by ListSelectionListener
    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    @Override
    public void update(Object event) {
        if (EVENT_SEA_CREATURE.equals(event)) {
            loadSeaCreatures();
        }

    }
}
