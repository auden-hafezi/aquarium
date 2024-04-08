package model.sea_creatures;

import exceptions.HungryException;
import exceptions.NotHungryException;
import exceptions.SeaDollarException;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Species.WHALE_SHARK;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Tests for the WhaleShark class.
public class WhaleSharkTest {
    WhaleShark testWhaleShark;
    Person testPerson;

    @BeforeEach
    void runBefore() {
        testWhaleShark = new WhaleShark("whale shark");
        Person.getInstance().reset();
        testPerson = Person.getInstance();
    }

    @Test
    void testConstructor() {
        assertEquals("whale shark", testWhaleShark.getName());
    }

    @Test
    void testGetSpecies() {
        assertEquals(WHALE_SHARK, testWhaleShark.getSpecies());
    }

    @Test
    void testPlay() {
        try {
            testWhaleShark.play();
            assertEquals(1, testWhaleShark.getNeedCount());
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }

    @Test
    void testPlayWhenHungry() {
        try {
            playUntilHungry();
            assertTrue(testWhaleShark.isHungry());
            assertEquals(0, testWhaleShark.getNeedCount());
            testWhaleShark.play();
            fail("expected HungryException");
        } catch (HungryException e) {
            // expected
        }
    }

    @Test
    void testFeedNotHungry() {
        try {
            assertFalse(testWhaleShark.isHungry());
            testWhaleShark.feed();
            fail("expected NotHungryException");
        } catch (NotHungryException e) {
            // expected
        }
        assertFalse(testWhaleShark.isHungry());
    }

    @Test
    void testFeed() {
        playUntilHungry();

        assertTrue(testWhaleShark.isHungry());

        try {
            testWhaleShark.feed();
            assertFalse(testWhaleShark.isHungry());
        } catch (NotHungryException e) {
            fail("unexpected NotHungryException");
        }
    }

    @Test
    void testTreat() {
        testPerson.depositSeaDollars(WhaleShark.TREAT_COST);

        try {
            testWhaleShark.treat();
            assertFalse(testWhaleShark.isHungry());
            assertEquals(0, testWhaleShark.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatHungry() {
        testPerson.depositSeaDollars(WhaleShark.TREAT_COST);

        playUntilHungry();

        try {
            testWhaleShark.treat();
            assertFalse(testWhaleShark.isHungry());
            assertEquals(0, testWhaleShark.getNeedCount());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testTreatInsufficientSeaDollars() {
        try {
            testWhaleShark.treat();
            fail("expected SeaDollarException");
        } catch (SeaDollarException e) {
            // expected
        }

    }

    // MODIFIES: testWhaleShark
    // EFFECTS: helper to call play() on testWhaleShark five times
    private void playUntilHungry() {
        try {
            testWhaleShark.play();
            testWhaleShark.play();
            testWhaleShark.play();
            testWhaleShark.play();
            testWhaleShark.play();
        } catch (HungryException e) {
            fail("unexpected HungryException");
        }
    }
}