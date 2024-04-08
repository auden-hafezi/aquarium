package model;

import exceptions.SeaDollarException;
import model.sea_creatures.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Species.*;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the Person class.
public class PersonTest {
    Person testPerson;

    @BeforeEach
    void runBefore() {
        Person.getInstance().reset();
        testPerson = Person.getInstance();
    }

    @Test
    void testConstructor() {
        assertEquals(20, testPerson.getSeaDollars());
        assertEquals(0, testPerson.getXP());
        assertEquals(0, testPerson.getPets().size());
    }

    @Test
    void testDepositSeaDollars() {
        testPerson.depositSeaDollars(100);
        assertEquals(120, testPerson.getSeaDollars());
    }

    @Test
    // TODO
    void testAwardSeaDollars() {
        for (int i = 0; i < 1000; i++) {
            testPerson.awardSeaDollars(1, 5);
        }
        int seaDollars = testPerson.getSeaDollars();
        assertTrue(seaDollars >= 1020);
        assertTrue(seaDollars <= 5020);

    }

    @Test
    void testAddXP() {
        testPerson.addXP(15);
        assertEquals(15, testPerson.getXP());
    }

    @Test
    void testBuyFood() {
        testPerson.buyFood(GOLDFISH, Goldfish.FOOD_COST);
        assertEquals(10, testPerson.getSeaDollars());
        testPerson.buyFood(GOLDFISH, Goldfish.FOOD_COST);
        assertEquals(0, testPerson.getSeaDollars());
    }

    @Test
    void testBuyTreat() {
        testPerson.depositSeaDollars(Goldfish.TREAT_COST);

        try {
            testPerson.buyTreat(GOLDFISH, Goldfish.TREAT_COST);
            assertEquals(20, testPerson.getSeaDollars());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }
    }

    @Test
    void testBuyTreatExactlyEnoughSeaDollars() {
        testPerson.depositSeaDollars(WhaleShark.TREAT_COST - 20);

        try {
            testPerson.buyTreat(WHALE_SHARK, WhaleShark.TREAT_COST);
            assertEquals(0, testPerson.getSeaDollars());
        } catch (SeaDollarException e) {
            fail("unexpected SeaDollarException");
        }

    }

    @Test
    void testBuyTreatInsufficientSeaDollars() {
        try {
            testPerson.buyTreat(SHARK, Shark.TREAT_COST);
            fail("expected SeaDollarException");
        } catch (SeaDollarException e) {
            // expected
        }

        assertEquals(20, testPerson.getSeaDollars());
    }

    @Test
    void testUpdatePets() {
        testPerson.addXP(0);
        assertEquals(1, testPerson.getPets().size());
        SeaCreature pet = testPerson.getPets().get(0);
        assertTrue(pet instanceof Goldfish);

        testPerson.addXP(100);
        assertEquals(2, testPerson.getPets().size());
        pet = testPerson.getPets().get(1);
        assertTrue(pet instanceof Jellyfish);

        testPerson.addXP(100);
        assertEquals(3, testPerson.getPets().size());
        pet = testPerson.getPets().get(2);
        assertTrue(pet instanceof Stingray);

        testPerson.addXP(200);
        assertEquals(4, testPerson.getPets().size());
        pet = testPerson.getPets().get(3);
        assertTrue(pet instanceof WhaleShark);

        testPerson.addXP(400);
        assertEquals(5, testPerson.getPets().size());
        pet = testPerson.getPets().get(4);
        assertTrue(pet instanceof Shark);
    }

    @Test
    void testUpdatePetsNoChange() {
        testPerson.addXP(10);
        assertEquals(1, testPerson.getPets().size());
    }
}
