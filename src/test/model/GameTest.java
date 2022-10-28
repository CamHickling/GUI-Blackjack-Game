package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class GameTest {


    public Game game;

    @BeforeEach
    public void setup() {
        game = new Game("Cam", 1000, true);
    }

    /*
    @Test
    public void Game() {
        // loop that plays one rounds then repeats unless the user asks to leave, or reaches 0 balance and loses
        do {
            int betamount = UserInterface.askBetAmount(player.getBalance());
            player.drawHand();
            Hand dealer = new Hand();
            Round round = new Round(player, dealer, betamount);

            // find out the result of the round
            recordResult(player, round, round.judgeWinner(player.getHand().getHandValue(), dealer.getHandValue()));
            continuePlaying(player);

        } while (this.playagain && !this.gameover);
    }

    //REQUIRES: player not null
    //MODIFIES: this
    //EFFECTS: set game over to true if the player has no more money, then calls a user interface to tell the user
    //         otherwise calls user interface to ask user if they want to play again
    public void continuePlaying(Player player) {
        this.gameover = player.getBalance() == 0;
        if (!this.gameover) {
            this.playagain = UserInterface.playAgain();
        } else {
            UserInterface.gameOver();
        }
    }
     */

    //REDUNDANT
    @Test
    public void testGetNumLosses() {
        assertEquals(game.getNumlosses(),0);
    }

    //REDUNDANT
    @Test
    public void testGetNumWins() {
        assertEquals(game.getNumlosses(),0);
    }

    @Test
    public void testContinuesPlaying() {
        assertFalse(game.getGameOver());
        assertTrue(game.getPlayer().getBalance() > 0);
        game.recordResult(game.getPlayer(), 0);
        assertFalse(game.getGameOver());
    }

    @Test
    public void testRecordResultWin() {
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 0);
        game.recordResult(game.getPlayer(), 2);
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 1);
    }

    @Test
    public void testRecordResultLoss() {
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 0);
        game.recordResult(game.getPlayer(), 1);
        assertEquals(game.getNumlosses(), 1);
        assertEquals(game.getNumwins(), 0);
    }

    @Test
    public void testRecordResultTie() {
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 0);
        game.recordResult(game.getPlayer(), 0);
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 0);
    }

    //REDUNDANT
    @Test
    public void testGetNumwins() {
        assertEquals(game.getNumwins(), 0);
    }

    //REDUNDANT
    @Test
    public void testGetNumlosses() {
        assertEquals(game.getNumlosses(), 0);
    }

    @Test
    public void testGetWinrateZeroWinsOneLoss() {
        game.recordResult(game.getPlayer(), 1);
        assertEquals(game.getNumlosses(), 1);
        assertEquals(game.getNumwins(), 0);
        assertEquals(game.getWinrate(), 0);
    }

    @Test
    public void testGetWinrateOneWinZeroLoss() {
        game.recordResult(game.getPlayer(), 2);
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 1);
        assertEquals(game.getWinrate(), 100);

    }

    @Test
    public void testGetWinrateOneWinOneLoss() {
        game.recordResult(game.getPlayer(), 2);
        game.recordResult(game.getPlayer(), 1);
        assertEquals(game.getNumlosses(), 1);
        assertEquals(game.getNumwins(), 1);
        assertEquals(game.getWinrate(), 50);

    }

    @Test
    public void testGetWinrateOneTie() {
        game.recordResult(game.getPlayer(), 0);
        assertEquals(game.getNumlosses(), 0);
        assertEquals(game.getNumwins(), 0);
        assertEquals(game.getWinrate(), 0);
    }

}
