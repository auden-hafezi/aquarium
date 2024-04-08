package model;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a sea creature in the aquarium.
public abstract class SeaCreature implements Writable {
    private String name;
    protected boolean hungry;
    private int needCount;

    // EFFECTS: creates a new sea creature with no name, hunger set to false, and needCount set to 0
    public SeaCreature(String name) {
        this.name = name;
        this.hungry = false;
        this.needCount = 0;
    }

    // MODIFIES: this, Person
    // EFFECTS: if the sea creature isn't hungry updates the need count by one and awards sea dollars
    // otherwise, throws HungryException
    public void play() throws HungryException {
        if (hungry) {
            throw new HungryException();
        }

        this.needCount++;
        updateNeeds();
    }

    // MODIFIES: this
    // EFFECTS: updates the needs of this sea creature depending on the need count
    private void updateNeeds() {
        if (needCount == 5) {
            hungry = true;
            needCount = 0;
        }
    }

    // MODIFIES: this, Person
    // EFFECTS: if the sea creature is hungry,
    // and the person can buy food for this sea creature, feeds it
    // if the creature is not hungry, throws NotHungryException
    public void feed() throws NotHungryException {
        if (!hungry) {
            throw new NotHungryException();
        }
    }

    // MODIFIES: this, Person
    // EFFECTS: helper for feed() that considers the species of sea creature, and it's food's cost
    protected void feedDependingOnSpecies(Species species, int cost) {
        Person person = Person.getInstance();
        person.buyFood(species, cost);

        this.hungry = false;
    }

    // MODIFIES: this, Person
    // EFFECTS: if the person can afford a treat, gives sea creature a treat, setting the needCount to 0
    // awards person xp
    public abstract void treat() throws SeaDollarException;

    // MODIFIES: this, Person
    // EFFECTS: helper for feed() that considers the species of sea creature, and it's food's cost
    protected void treatDependingOnSpecies(Species species, int cost) throws SeaDollarException {
        Person person = Person.getInstance();
        person.buyTreat(species, cost);

        this.hungry = false;
        this.needCount = 0;
    }

    // MODIFIES: this
    // EFFECTS: changes the sea creature's name to the given name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: changes the sea creature's hungry status to the given boolean
    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    // MODIFIES: this
    // EFFECTS: changes the sea creature's need count to the given needCount
    public void setNeedCount(int needCount) {
        this.needCount = needCount;
    }

    // EFFECTS: helper to start representing a sea creature as a JSONObject
    protected JSONObject seaCreatureToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("hungry", hungry);
        jsonObject.put("needCount", needCount);
        return jsonObject;
    }

    // getters

    public String getName() {
        return name;
    }

    public boolean isHungry() {
        return hungry;
    }

    public int getNeedCount() {
        return needCount;
    }

    public abstract Species getSpecies();
}
