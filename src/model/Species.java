package model;

// Represents a species of sea creature.
public enum Species {
    SHARK, STINGRAY, WHALE_SHARK, JELLYFISH, GOLDFISH;

    // EFFECTS: returns the given species as a string
    public static String speciesToString(Species species) {
        String speciesString = species.toString().toLowerCase().replace("_", " ");
        return speciesString;
    }
}
