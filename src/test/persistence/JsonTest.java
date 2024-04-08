package persistence;

import model.Person;
import model.SeaCreature;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Methods for testing JSON classes.
public class JsonTest {

    @BeforeEach
    void runBefore() {
        Person.getInstance().reset();
    }

    // EFFECTS: checks if the two sea creatures have the same name, hunger status,
    // need count, and are of the same species
    public void checkSeaCreature(SeaCreature seaCreature, SeaCreature readSeaCreature) {
        assertEquals(seaCreature.getName(), readSeaCreature.getName());
        assertEquals(seaCreature.isHungry(), readSeaCreature.isHungry());
        assertEquals(seaCreature.getNeedCount(), readSeaCreature.getNeedCount());
        assertEquals(seaCreature.getSpecies(), readSeaCreature.getSpecies());
    }
}
