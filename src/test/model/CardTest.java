package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {

    Card card;

    @BeforeEach
    public void setup() {
        card = new Card();
    }

    @Test
    public void testCard() {
        assertTrue(card.getName().length() == 1 || card.getName().length() == 2);
        assertTrue(card.getValue() >= 2 );
        assertTrue(card.getValue() <= 10 );
    }

    //REDUNDANT
    @Test
    public void testName() {
        assertEquals(card.getName().length(), 1);
    }

    //REDUNDANT
    @Test
    public void testValue() {
        assertTrue(card.getValue() > 1 );
        assertTrue(card.getValue() <= 10 );
    }
}
