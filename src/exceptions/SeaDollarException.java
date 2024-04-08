package exceptions;

import model.Species;

// An exception for when the user has insufficient sea dollars.
public class SeaDollarException extends Exception {
    Species species;

    public SeaDollarException(Species species) {
        this.species = species;
    }

    @Override
    public String getMessage() {
        String name = Species.speciesToString(species);
        return "couldn't afford " + name + " treat! (;-;)";
    }
}
