package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Species.JELLYFISH;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Tests for the Jellyfish class.
public class JellyfishTest {
    Jellyfish testJellyfish;
    Person testPerson;

    @BeforeEach
    void runBefore() {
        testJellyfish = new Jellyfish("jellyfish");
        Person.getInstance().reset();
        testPerson = Person.getInstance();
    }

    @Test
    void testConstructor() {
        assertEquals("jellyfish", testJellyfish.getName());
    }

    @Test
    void testGetSpecies() {
        assertEquals(JELLYFISH, testJellyfish.getSpecies());
    }

    @Test
    void testPlay() {
        try {
            testJellyfish.play();
            assertEquals(1, testJellyfish.getNeedCount());
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }

    @Test
    void testPlayWhenHungry() {
        try {
            playUntilHungry();
            assertTrue(testJellyfish.isHungry());
            assertEquals(0, testJellyfish.getNeedCount());
            testJellyfish.play();
            fail("expected HungryException");
        } catch (HungryException e) {
            // expected
        }
    }

    @Test
    void testFeedNotHungry() {
        try {
            assertFalse(testJellyfish.isHungry());
            testJellyfish.feed();
            fail("expected NotHungryException");
        } catch (NotHungryException e) {
            // expected
        }
        assertFalse(testJellyfish.isHungry());
    }

    @Test
    void testFeed() {
        playUntilHungry();

        assertTrue(testJellyfish.isHungry());

        try {
            testJellyfish.feed();
            assertFalse(testJellyfish.isHungry());
        } catch (NotHungryException e) {
            fail("unexpected NotHungryException");
        }
    }

    @Test
    void testTreat() {
        testPerson.depositSeaDollars(Jellyfish.TREAT_COST);

        try {
            testJellyfish.treat();
            assertFalse(testJellyfish.isHungry());
            assertEquals(0, testJellyfish.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatHungry() {
        testPerson.depositSeaDollars(Jellyfish.TREAT_COST);

        playUntilHungry();

        try {
            testJellyfish.treat();
            assertFalse(testJellyfish.isHungry());
            assertEquals(0, testJellyfish.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatInsufficientSeaDollars() {
        try {
            testJellyfish.treat();
            fail("expected SeaDollarException");
        } catch (SeaDollarException e) {
            // expected
        }

    }

    // MODIFIES: testJellyFish
    // EFFECTS: helper to call play() on testJellyfish five times
    private void playUntilHungry() {
        try {
            testJellyfish.play();
            testJellyfish.play();
            testJellyfish.play();
            testJellyfish.play();
            testJellyfish.play();
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }
}