package persistence;

import model.Person;
import model.SeaCreature;
import model.sea_creatures.Goldfish;
import model.sea_creatures.Jellyfish;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests for the JSONWriter class.
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoPets() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterNoPets.json");
            writer.open();
            writer.write();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoPets.json");
            reader.read();
            List<SeaCreature> pets = Person.getInstance().getPets();
            assertEquals(0, pets.size());

        } catch (IOException e) {
            fail("unexpected IOException");
        }
    }

    @Test
    void testWriter() {
        try {
            Person person = Person.getInstance();
            SeaCreature goldfish = new Goldfish("boba");

            Jellyfish jellyfish = new Jellyfish("jello");
            jellyfish.setHungry(true);
            jellyfish.setNeedCount(0);

            person.addPet(goldfish);
            person.addPet(jellyfish);

            JsonWriter writer = new JsonWriter("./data/testWriter.json");
            writer.open();
            writer.write();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter.json");
            reader.read();
            person = Person.getInstance();
            List<SeaCreature> pets = person.getPets();
            assertEquals(2, pets.size());
            SeaCreature readGoldfish = pets.get(0);
            SeaCreature readJellyfish = pets.get(1);

            checkSeaCreature(goldfish, readGoldfish);
            checkSeaCreature(jellyfish, readJellyfish);

        } catch (IOException e) {
            fail("unexpected IOException");
        }
    }
}