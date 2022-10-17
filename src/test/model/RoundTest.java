package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoundTest {
    Round round;
    Player player;
    Hand dealer;
    int betamount;
    int numwins;
    int numlosses;

    @BeforeEach void setup() {
        player = new Player("Cam", 1000);
        dealer = new Hand();
        betamount = 100;
        round = new Round(player, dealer, betamount, true, true);
    }

    @Test
    public void testRound() {
        assertEquals(round.getPlayer().getName(), "Cam");
        assertEquals(round.getPlayer().getBalance(), 1000);

        assertTrue(round.getDealer().getHandValue() >= 4);
        assertTrue(round.getDealer().getHandValue() <= 27);
        assertTrue(round.getDealer().getMyCards().size() <= 30);
        assertEquals(round.getDealer().getMyCards().size(), 2);

        assertEquals(betamount, 100);
    }

    @Test
    private void testDealersTurn() {
        dealer = round.getDealer();
        for (int i = 0; i < 50; i++) {
            while (dealer.getHandValue() < 16) {
                dealer.hit();
            }
            dealer.stand();
            assertTrue(dealer.getHandValue() >= 16);
            assertTrue(dealer.getHandValue() <= 27);
        }

    }

    @Test
    public void testJudgeWinner() {

        //player and dealer both bust -> tie
        assertEquals(round.judgeWinner(22, 22),0);

        //player bust, dealer not bust -> dealer win
        assertEquals(round.judgeWinner(22, 21),1);

        //player not bust, dealer bust -> player win
        assertEquals(round.judgeWinner(21, 22),2);

        //neither bust, player less than dealer -> dealer win
        assertEquals(round.judgeWinner(20, 21),1);

        //neither bust, player/dealer equal hand -> dealer win
        assertEquals(round.judgeWinner(21, 21),1);

        //neither bust, player greater than dealer -> player win
        assertEquals(round.judgeWinner(21, 20),2);
    }

    @Test
    public void testRecordResultWin() {
        assertEquals(round.getNumLosses(), 0);
        assertEquals(round.getNumWins(), 0);
        round.recordResult(round.getPlayer(), 2);
        assertEquals(round.getNumLosses(), 0);
        assertEquals(round.getNumWins(), 1);
    }

    @Test
    public void testRecordResultLoss() {
        assertEquals(round.getNumLosses(), 0);
        assertEquals(round.getNumWins(), 0);
        round.recordResult(round.getPlayer(), 1);
        assertEquals(round.getNumLosses(), 1);
        assertEquals(round.getNumWins(), 0);
    }

    @Test
    public void testRecordResultTie() {
        assertEquals(round.getNumLosses(), 0);
        assertEquals(round.getNumWins(), 0);
        round.recordResult(round.getPlayer(), 0);
        assertEquals(round.getNumLosses(), 0);
        assertEquals(round.getNumWins(), 0);
    }

    //REDUNDANT
    @Test
    public void testGetNumLosses() {
        assertEquals(round.getNumLosses(),0);
    }

    //REDUNDANT
    @Test
    public void testGetNumWins() {
        assertEquals(round.getNumWins(),0);
    }

    //REDUNDANT
    @Test
    public void testGetBetAmount() {
        assertEquals(round.getBetAmount(), 100);
    }

    //REDUNDANT
    @Test
    public void testGetDealer() {
        assertTrue(round.getDealer().getHandValue() >= 4);
        assertTrue(round.getDealer().getHandValue() <= 27);
        assertTrue(round.getDealer().getMyCards().size() <= 30);
    }

    //REDUNDANT
    @Test
    public void testGetPlayer() {
        assertEquals(round.getPlayer().getName(), "Cam");
        assertEquals(round.getPlayer().getBalance(), 1000);
    }
}
