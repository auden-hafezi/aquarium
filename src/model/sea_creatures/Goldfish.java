package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import model.SeaCreature;
import model.Species;
import org.json.JSONObject;

import static model.Species.GOLDFISH;

// Represents a goldfish.
public class Goldfish extends SeaCreature {
    public static final int FOOD_COST = 10;
    public static final int TREAT_COST = 50;
    private final Species species = GOLDFISH;

    public Goldfish(String name) {
        super(name);
    }

    @Override
    public void play() throws HungryException {
        super.play();

        Person.getInstance().awardSeaDollars(4, 8);
    }

    @Override
    public void feed() throws NotHungryException {
        super.feed();
        feedDependingOnSpecies(species, FOOD_COST);
    }

    @Override
    public void treat() throws SeaDollarException {
        treatDependingOnSpecies(species, TREAT_COST);
        Person.getInstance().addXP(10);
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = seaCreatureToJson();
        jsonObject.put("species", species);
        return jsonObject;
    }
}
