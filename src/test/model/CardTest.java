/*
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
        assertTrue(card.getValue() <= 11 );
    }

    @Test
    public void testCard2() {
        Card card2 = new Card("A");
        assertEquals(11, card2.getValue());
        assertEquals("A", card2.getName());
    }

    //REDUNDANT
    @Test
    public void testName() {
        assertTrue(card.getName().length() <= 2);
    }

    //REDUNDANT
    @Test
    public void testValue() {
        assertTrue(card.getValue() > 1 );
        assertTrue(card.getValue() <= 11 );
    }

    @Test
    public void testToString() {
        assertEquals(card.getName(), card.toString());
    }

}

 */
