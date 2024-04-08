package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import model.SeaCreature;
import model.Species;
import org.json.JSONObject;

import static model.Species.SHARK;

// Represents a whale shark.
public class Shark extends SeaCreature {
    public static final int FOOD_COST = 100;
    public static final int TREAT_COST = 800;
    private final Species species = SHARK;

    public Shark(String name) {
        super(name);
    }

    @Override
    public void play() throws HungryException {
        super.play();

        Person.getInstance().awardSeaDollars(40, 61);
    }

    @Override
    public void feed() throws NotHungryException {
        super.feed();
        feedDependingOnSpecies(species, FOOD_COST);
    }

    @Override
    public void treat() throws SeaDollarException {
        treatDependingOnSpecies(species, TREAT_COST);
        Person.getInstance().addXP(160);
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
