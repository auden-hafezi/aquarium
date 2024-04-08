package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Species.STINGRAY;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Tests for the Stingray class.
public class StingrayTest {
    Stingray testStingray;
    Person testPerson;

    @BeforeEach
    void runBefore() {
        testStingray = new Stingray("stingray");
        Person.getInstance().reset();
        testPerson = Person.getInstance();
    }

    @Test
    void testConstructor() {
        assertEquals("stingray", testStingray.getName());
    }

    @Test
    void testGetSpecies() {
        assertEquals(STINGRAY, testStingray.getSpecies());
    }

    @Test
    void testPlay() {
        try {
            testStingray.play();
            assertEquals(1, testStingray.getNeedCount());
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }

    @Test
    void testPlayWhenHungry() {
        try {
            playUntilHungry();
            assertEquals(0, testStingray.getNeedCount());
            assertTrue(testStingray.isHungry());
            testStingray.play();
            fail("expected HungryException");
        } catch (HungryException e) {
            // expected
        }
    }

    @Test
    void testFeedNotHungry() {
        try {
            assertFalse(testStingray.isHungry());
            testStingray.feed();
            fail("expected NotHungryException");
        } catch (NotHungryException e) {
            // expected
        }
        assertFalse(testStingray.isHungry());
    }

    @Test
    void testFeed() {
        playUntilHungry();

        assertTrue(testStingray.isHungry());

        try {
            testStingray.feed();
            assertFalse(testStingray.isHungry());
        } catch (NotHungryException e) {
            fail("unexpected NotHungryException");
        }
    }

    @Test
    void testTreat() {
        testPerson.depositSeaDollars(Stingray.TREAT_COST);

        try {
            testStingray.treat();
            assertFalse(testStingray.isHungry());
            assertEquals(0, testStingray.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatHungry() {
        testPerson.depositSeaDollars(Stingray.TREAT_COST);

        playUntilHungry();

        try {
            testStingray.treat();
            assertFalse(testStingray.isHungry());
            assertEquals(0, testStingray.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatInsufficientSeaDollars() {
        try {
            testStingray.treat();
            fail("expected SeaDollarException");
        } catch (SeaDollarException e) {
            // expected
        }

    }

    // MODIFIES: testStingray
    // EFFECTS: helper to call play() on testStingray five times
    private void playUntilHungry() {
        try {
            testStingray.play();
            testStingray.play();
            testStingray.play();
            testStingray.play();
            testStingray.play();
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }
}