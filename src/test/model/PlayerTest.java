package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPLayer() {
        Player player = new Player("", 1);
        assertEquals(player.getName(), "");
        assertEquals(player.getBalance(), 1);
        assertEquals(player.getHand().getMyCards().size(), 2);
    }

    @Test
    public void testPlayerTwo() {
        Player player = new Player("Cam", 1000);
        assertEquals(player.getName(), "Cam");
        assertEquals(player.getBalance(), 1000);
        assertEquals(player.getHand().getMyCards().size(), 2);
    }

    @Test
    public void drawHand() {
        Player player = new Player("", 1);
        Hand initialhand = player.getHand();
        player.drawHand();
        Hand newhand = player.getHand();
        assertNotEquals(initialhand, newhand);
    }

    @Test
    public void testHit() {
        Player player = new Player("", 1);
        assertEquals(player.getHand().getMyCards().size(), 2);
        int initialhandvalue = player.getHand().getHandValue();
        player.hit();
        assertEquals(player.getHand().getMyCards().size(), 3);
        int newhandvalue = player.getHand().getHandValue();
        assertTrue(initialhandvalue < newhandvalue);
    }

    @Test
    public void testStand() {
        Player player = new Player("", 1);
        assertEquals(player.getHand().getMyCards().size(), 2);
        int initialhandvalue = player.getHand().getHandValue();
        player.stand();
        assertEquals(player.getHand().getMyCards().size(), 2);
        int newhandvalue = player.getHand().getHandValue();
        assertTrue(initialhandvalue == newhandvalue);
    }

    @Test
    public void testAdjustBalanceLoss() {
        Player player = new Player("", 10);
        player.adjustBalance(-5);
        assertEquals(player.getBalance(), 5);
    }

    @Test
    public void testAdjustBalanceEqualToZero() {
        Player player = new Player("", 10);
        player.adjustBalance(-10);
        assertEquals(player.getBalance(), 0);
    }

    @Test
    public void testAdjustBalanceBelowZero() {
        Player player = new Player("", 10);
        player.adjustBalance(-20);
        assertEquals(player.getBalance(), 0);

    }

    @Test
    public void testAdjustBalanceWin() {
        Player player = new Player("", 10);
        player.adjustBalance(20);
        assertEquals(player.getBalance(), 30);
    }


    //REDUNDANT
    @Test
    public void testGetHand() {
        Player player = new Player("", 1);
        assertEquals(player.getHand().getMyCards().size(), 2);
    }

    //REDUNDANT
    @Test
    public void testGetBalance() {
        Player player = new Player("", 1);
        assertEquals(player.getBalance(), 1);
    }

    //REDUNDANT
    @Test
    public void testGetName() {
        Player player = new Player("Cam", 1000);
        assertEquals(player.getName(), "Cam");
    }

}
