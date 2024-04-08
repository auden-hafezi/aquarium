package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Species.SHARK;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the Shark class.
public class SharkTest {
    Shark testShark;
    Person testPerson;

    @BeforeEach
    void runBefore() {
        testShark = new Shark("shark");
        Person.getInstance().reset();
        testPerson = Person.getInstance();
    }

    @Test
    void testConstructor() {
        assertEquals("shark", testShark.getName());
        assertFalse(testShark.isHungry());
        assertEquals(0, testShark.getNeedCount());
    }

    @Test
    void testSetName() {
        testShark.setName("name");
        assertEquals("name", testShark.getName());
    }

    @Test
    void testGetSpecies() {
        assertEquals(SHARK, testShark.getSpecies());
    }

    @Test
    void testPlay() {
        try {
            testShark.play();
            assertEquals(1, testShark.getNeedCount());
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }

    @Test
    void testPlayWhenHungry() {
        try {
            playUntilHungry();
            assertTrue(testShark.isHungry());
            assertEquals(0, testShark.getNeedCount());
            testShark.play();
            fail("expected HungryException");
        } catch (HungryException e) {
            // expected
        }
    }

    @Test
    void testUpdateNeedCount() {
        try {
            testShark.play();
            testShark.play();
            testShark.play();
            testShark.play();
            assertEquals(4, testShark.getNeedCount());

            testShark.play();
            assertEquals(0, testShark.getNeedCount());
            assertTrue(testShark.isHungry());

        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }

    @Test
    void testFeedNotHungry() {
        try {
            assertFalse(testShark.isHungry());
            testShark.feed();
            fail("expected NotHungryException");
        } catch (NotHungryException e) {
            // expected
        }
        assertFalse(testShark.isHungry());
    }

    @Test
    void testFeed() {
        playUntilHungry();

        assertTrue(testShark.isHungry());

        try {
            testShark.feed();
            assertFalse(testShark.isHungry());
        } catch (NotHungryException e) {
            fail("unexpected NotHungryException");
        }
    }

    @Test
    void testTreat() {
        testPerson.depositSeaDollars(Shark.TREAT_COST);

        try {
            testShark.treat();
            assertFalse(testShark.isHungry());
            assertEquals(0, testShark.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatHungry() {
        testPerson.depositSeaDollars(Shark.TREAT_COST);

        playUntilHungry();

        try {
            testShark.treat();
            assertFalse(testShark.isHungry());
            assertEquals(0, testShark.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatInsufficientSeaDollars() {
        try {
            testShark.treat();
            fail("expected SeaDollarException");
        } catch (SeaDollarException e) {
            // expected
        }

    }

    // MODIFIES: testShark
    // EFFECTS: helper to call play() on testShark five times
    private void playUntilHungry() {
        try {
            testShark.play();
            testShark.play();
            testShark.play();
            testShark.play();
            testShark.play();
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }
}