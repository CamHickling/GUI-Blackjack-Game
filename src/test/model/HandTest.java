package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandTest {

    Hand hand;

    @BeforeEach
    public void setup() {
        hand = new Hand();
    }

    @Test
    public void testHand() {
        assertEquals(hand.getMyCards().size(), 2);
        assertTrue(hand.getHandValue() <= 21);
        assertTrue(hand.getHandValue() >= 4);
    }

    @Test
    public void testHit() {
        assertEquals(hand.getMyCards().size(), 2);
        int beforehit = hand.getHandValue();

        hand.hit();
        assertEquals(hand.getMyCards().size(), 3);
        int afterhit = hand.getHandValue();

        assertTrue(beforehit < afterhit);
    }

    @Test
    public void testStand() {
        assertEquals(hand.getMyCards().size(), 2);
        int beforestand = hand.getHandValue();

        hand.stand();
        assertEquals(hand.getMyCards().size(), 2);
        int afterstand = hand.getHandValue();

        assertEquals(beforestand, afterstand);
    }

    @Test
    public void  testGetMyCards() {
        assertTrue(hand.getMyCards().size() >= 2);
    }

    @Test
    public void testCalculateHandValueHit() {
        int initialvalue = hand.getHandValue();
        hand.hit();
        int recalculatedvalue = hand.getHandValue();
        assertTrue(initialvalue < recalculatedvalue);
    }

    @Test
    public void testCalculateHandValueStand() {
        int initialvalue = hand.getHandValue();
        hand.stand();
        int recalculatedvalue = hand.getHandValue();
        assertTrue(initialvalue == recalculatedvalue);
    }

    @Test
    public void testGetHandValue() {
        assertTrue(hand.getHandValue() <= 21);
        assertTrue(hand.getHandValue() >= 4);
    }

    @Test
    public void testShowMyCardsTwo() {
        String printcards = hand.showMyCards();
        assertEquals(printcards.length(), 4);
        assertEquals(printcards.charAt(1), ' ');
        assertEquals(printcards.charAt(3), ' ');
    }

    @Test
    public void testShowMyCardsThree() {
        hand.hit();
        String printcards = hand.showMyCards();
        assertEquals(printcards.length(), 6);
        assertEquals(printcards.charAt(1), ' ');
        assertEquals(printcards.charAt(3), ' ');
        assertEquals(printcards.charAt(5), ' ');
    }
}
