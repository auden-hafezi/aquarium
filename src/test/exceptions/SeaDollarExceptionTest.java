package exceptions;

import org.junit.jupiter.api.Test;

import static model.Species.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeaDollarExceptionTest {
    SeaDollarException testException;

    @Test
    void testGetMessageGoldfish() {
        testException = new SeaDollarException(GOLDFISH);
        assertEquals("couldn't afford goldfish treat! (;-;)", testException.getMessage());
    }

    @Test
    void testGetMessageJellyFish() {
        testException = new SeaDollarException(JELLYFISH);
        assertEquals("couldn't afford jellyfish treat! (;-;)", testException.getMessage());
    }

    @Test
    void testGetMessageStingray() {
        testException = new SeaDollarException(STINGRAY);
        assertEquals("couldn't afford stingray treat! (;-;)", testException.getMessage());
    }

    @Test
    void testGetMessageWhaleShark() {
        testException = new SeaDollarException(WHALE_SHARK);
        assertEquals("couldn't afford whale shark treat! (;-;)", testException.getMessage());
    }

    @Test
    void testGetMessageShark() {
        testException = new SeaDollarException(SHARK);
        assertEquals("couldn't afford shark treat! (;-;)", testException.getMessage());
    }
}
