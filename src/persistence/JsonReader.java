package persistence;

import model.Person;
import model.SeaCreature;
import model.Species;
import model.sea_creatures.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

// Represents a reader that reads person's information from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the person from the file
    // throws an IOException if an error occurs when trying to read the cafe log
    public void read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parsePerson(jsonObject);
    }

    // EFFECTS: reads source file and returns it as a string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = lines(get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses cafe log from JSON object
    public void parsePerson(JSONObject jsonObject) {
        Person.getInstance().reset();
        Person person = Person.getInstance();
        int seaDollars = jsonObject.getInt("seaDollars");
        int xp = jsonObject.getInt("xp");
        person.setSeaDollars(seaDollars);
        person.setXP(xp);
        addSeaCreatures(jsonObject);
    }

    // MODIFIES: Person
    // EFFECTS: parses sea creatures from JSON object and adds them to the person's pets
    private void addSeaCreatures(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pets");
        for (Object json : jsonArray) {
            JSONObject seaCreature = (JSONObject) json;
            addSeaCreature(seaCreature);
        }
    }

    // MODIFIES: Person
    // EFFECTS: parses sea creature from JSON object and adds it to the person's pets
    private void addSeaCreature(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        boolean hungry = jsonObject.getBoolean("hungry");
        int needCount = jsonObject.getInt("needCount");
        String speciesName = jsonObject.getString("species");
        Species species = toSpecies(speciesName);

        SeaCreature seaCreature = createSeaCreature(species, name);
        seaCreature.setHungry(hungry);
        seaCreature.setNeedCount(needCount);

        Person.getInstance().addPet(seaCreature);
    }

    // REQUIRES: the given string be the name of a species in Species
    // EFFECTS: helper to convert a string back to species enum
    private Species toSpecies(String speciesName) {
        switch (speciesName) {
            case "GOLDFISH":
                return Species.GOLDFISH;
            case "JELLYFISH":
                return Species.JELLYFISH;
            case "STINGRAY":
                return Species.STINGRAY;
            case "WHALE_SHARK":
                return Species.WHALE_SHARK;
            default:
                return Species.SHARK;
        }
    }

    // EFFECTS: helper to create a sea creature of the appropriate species
    private SeaCreature createSeaCreature(Species species, String name) {
        switch (species) {
            case GOLDFISH:
                return new Goldfish(name);
            case JELLYFISH:
                return new Jellyfish(name);
            case STINGRAY:
                return new Stingray(name);
            case WHALE_SHARK:
                return new WhaleShark(name);
            default:
                return new Shark(name);

        }
    }
}
