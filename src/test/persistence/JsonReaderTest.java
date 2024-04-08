package persistence;

import model.Person;
import model.SeaCreature;
import model.sea_creatures.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests for the JSONReader class.
public class JsonReaderTest extends JsonTest {

    @Test
    void testNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist.json");
        try {
            reader.read();
            fail("IOException expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testPersonNoPets() {
        JsonReader reader = new JsonReader("./data/testReaderNoPets.json");
        try {
            reader.read();
            List<SeaCreature> pets = Person.getInstance().getPets();
            assertEquals(0, pets.size());

        } catch (IOException e) {
            fail("should have been able to read file");
        }
    }

    @Test
    void testReader() {
        JsonReader reader = new JsonReader("./data/testReader.json");
        try {
            reader.read();
            List<SeaCreature> pets = Person.getInstance().getPets();;
            assertEquals(5, pets.size());

            Goldfish goldfish = new Goldfish("boba");

            Jellyfish jellyfish = new Jellyfish("jello");
            jellyfish.setNeedCount(3);

            Stingray stingray = new Stingray("cappuccino");
            stingray.setHungry(true);

            WhaleShark whaleShark = new WhaleShark("auden");
            whaleShark.setNeedCount(4);

            Shark shark = new Shark("strawberry");
            shark.setNeedCount(2);

            checkSeaCreature(goldfish, pets.get(0));
            checkSeaCreature(jellyfish, pets.get(1));
            checkSeaCreature(stingray, pets.get(2));
            checkSeaCreature(whaleShark, pets.get(3));
            checkSeaCreature(shark, pets.get(4));

        } catch (IOException e) {
            fail("should have been able to read file");
        }
    }
}
