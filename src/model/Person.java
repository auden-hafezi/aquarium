package model;

import exceptions.SeaDollarException;
import model.sea_creatures.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents the person who owns the aquarium (the user).
public class Person extends Observable implements Writable {
    public static final String EVENT_STATS_CHANGED = "stats changed";
    public static final String EVENT_SEA_CREATURE = "sea creature";
    public static final String EVENT_JELLYFISH = "jellyfish unlocked";
    public static final String EVENT_STINGRAY = "stingray unlocked";
    public static final String EVENT_WHALE_SHARK = "whale shark unlocked";
    public static final String EVENT_SHARK = "shark unlocked";
    private static Person singleton;
    private List<SeaCreature> pets;
    private int seaDollars;
    private int xp;


    // EFFECTS: creates a person with no pets, no xp, and 20 sea dollars
    protected Person() {
        pets = new ArrayList<>();
        seaDollars = 20;
        xp = 0;
        notifyObservers(EVENT_STATS_CHANGED);
        notifyObservers(EVENT_SEA_CREATURE);
    }

    // MODIFIES: this
    // EFFECTS: resets the singleton to null
    public void reset() {
        singleton = null;
        pets = null;
        seaDollars = 0;
        xp = 0;
    }

    // EFFECTS: returns this person
    public static Person getInstance() {
        if (singleton == null) {
            singleton = new Person();
        }
        return singleton;
    }

    // MODIFIES: this
    // EFFECTS: awards a random amount between high and low sea dollars to the person
    public void awardSeaDollars(int low, int high) {
        Random random = new Random();
        int seaDollars = random.nextInt(high - low) + low;
        this.depositSeaDollars(seaDollars);
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: adds the given amount of sea dollars to the person
    public void depositSeaDollars(int amount) {
        seaDollars += amount;
        notifyObservers(EVENT_STATS_CHANGED);
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: adds the given amount of xp to the person
    // checks if sufficient xp has been earned to add a new pet
    public void addXP(int amount) {
        xp += amount;
        updatePets();
        notifyObservers(EVENT_STATS_CHANGED);
    }

    // MODIFIES: this
    // EFFECTS: if the minimum xp has been earned to unlock the next pet, adds it to the list of pets
    // updates the ownership status of that species to true
    private void updatePets() {
        if (pets.size() == 0 && xp >= 0) {
            addPet(new Goldfish("goldfish"));
        } else if (pets.size() == 1 && xp >= 100) {
            addPet(new Jellyfish("jellyfish"));
            notifyObservers(EVENT_JELLYFISH);
        } else if (pets.size() == 2 && xp >= 200) {
            addPet(new Stingray("stingray"));
            notifyObservers(EVENT_STINGRAY);
        } else if (pets.size() == 3 && xp >= 400) {
            addPet(new WhaleShark("whale shark"));
            notifyObservers(EVENT_WHALE_SHARK);
        } else if (pets.size() == 4 && xp >= 800) {
            addPet(new Shark("shark"));
            notifyObservers(EVENT_SHARK);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given sea creature to the person's pets
    public void addPet(SeaCreature seaCreature) {
        pets.add(seaCreature);
        notifyObservers(EVENT_SEA_CREATURE);
    }

    // REQUIRES: cost <= this.seaDollars
    // Note: this is ensured, as playing with a sea creature earns at least the cost of its food, and new
    // sea creatures are initialized as not hungry
    // MODIFIES: this
    // EFFECTS: buys food for sea creature if possible, and returns true
    public void buyFood(Species species, int cost) {
        seaDollars -= cost;
        notifyObservers(EVENT_STATS_CHANGED);
    }

    // MODIFIES: this
    // EFFECTS: buys treat for sea creature if possible
    // if insufficient sea dollars, throws SeaDollarException
    public void buyTreat(Species species, int cost) throws SeaDollarException {
        if (cost > seaDollars) {
            throw new SeaDollarException(species);
        }

        seaDollars -= cost;
        notifyObservers(EVENT_STATS_CHANGED);
    }

    // MODIFIES: this;
    // EFFECTS: sets sea dollars to the given amount
    public void setSeaDollars(int seaDollars) {
        this.seaDollars = seaDollars;
    }

    // MODIFIES: this;
    // EFFECTS: sets xp to the given amount
    public void setXP(int xp) {
        this.xp = xp;
    }

    @Override
    // TODO
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pets", seaCreaturesToJson());
        json.put("seaDollars", seaDollars);
        json.put("xp", xp);
        return json;
    }

    // EFFECTS: returns the person's pets as a JSONArray
    private JSONArray seaCreaturesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SeaCreature seaCreature : pets) {
            jsonArray.put(seaCreature.toJson());
        }

        return jsonArray;
    }

    // getters

    public int getSeaDollars() {
        return seaDollars;
    }

    public int getXP() {
        return xp;
    }

    public List<SeaCreature> getPets() {
        return pets;
    }

}
