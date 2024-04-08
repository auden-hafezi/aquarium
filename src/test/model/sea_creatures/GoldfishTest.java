package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Species.GOLDFISH;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Tests for the Goldfish class.
public class GoldfishTest {
    Goldfish testGoldfish;
    Person testPerson;

    @BeforeEach
    void runBefore() {
        testGoldfish = new Goldfish("goldfish");
        Person.getInstance().reset();
        testPerson = Person.getInstance();
    }

    @Test
    void testConstructor() {
        assertEquals("goldfish", testGoldfish.getName());
    }

    @Test
    void testGetSpecies() { assertEquals(GOLDFISH, testGoldfish.getSpecies()); }

    @Test
    void testPlay() {
        try {
            testGoldfish.play();
            assertEquals(1, testGoldfish.getNeedCount());
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }

    @Test
    void testPlayWhenHungry() {
        try {
            playUntilHungry();
            assertEquals(0, testGoldfish.getNeedCount());
            assertTrue(testGoldfish.isHungry());
            testGoldfish.play();
            fail("expected HungryException");
        } catch (HungryException e) {
            // expected
        }
    }

    @Test
    void testFeedNotHungry() {
        try {
            assertFalse(testGoldfish.isHungry());
            testGoldfish.feed();
            fail("expected NotHungryException");
        } catch (NotHungryException e) {
            // expected
        }
        assertFalse(testGoldfish.isHungry());
    }

    @Test
    void testFeed() {
        playUntilHungry();

        assertTrue(testGoldfish.isHungry());

        try {
            testGoldfish.feed();
            assertFalse(testGoldfish.isHungry());
        } catch (NotHungryException e) {
            fail("unexpected NotHungryException");
        }
    }

    @Test
    void testTreat() {
        testPerson.depositSeaDollars(Goldfish.TREAT_COST);

        try {
            testGoldfish.treat();
            assertFalse(testGoldfish.isHungry());
            assertEquals(0, testGoldfish.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatHungry() {
        testPerson.depositSeaDollars(Goldfish.TREAT_COST);

        playUntilHungry();

        try {
            testGoldfish.treat();
            assertFalse(testGoldfish.isHungry());
            assertEquals(0, testGoldfish.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatInsufficientSeaDollars() {
        try {
            testGoldfish.treat();
            fail("expected SeaDollarException");
        } catch (SeaDollarException e) {
            // expected
        }

    }

    // MODIFIES: testGoldfish
    // EFFECTS: helper to call play() on testGoldfish five times
    private void playUntilHungry() {
        try {
            testGoldfish.play();
            testGoldfish.play();
            testGoldfish.play();
            testGoldfish.play();
            testGoldfish.play();
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }
}