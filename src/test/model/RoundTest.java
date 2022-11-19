/*
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.UserInterface;

import java.util.ArrayList;

public class RoundTest {
    Round round;
    Player player;
    Hand dealer;
    int betamount;
    int numwins;
    int numlosses;

    @BeforeEach void setup() {
        player = new Player(1000);
        dealer = new Hand();
        betamount = 100;
        round = new Round(player, dealer, betamount, true);
    }

    @Test
    public void testRound() {
        assertEquals(round.getPlayer().getBalance(), 1000);

        assertTrue(round.getDealer().getHandValue() >= 4);
        assertTrue(round.getDealer().getHandValue() <= 27);
        assertTrue(round.getDealer().getMyCards().size() <= 30);
        //assertEquals(round.getDealer().getMyCards().size(), 2);

        assertEquals(betamount, 100);
    }

    @Test
    public void testRound2() {
        Round r = new Round(1,2);
        assertEquals(1, r.getBetAmount());
        assertEquals(2, r.getResult());
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
        assertEquals(round.getPlayer().getBalance(), 1000);
    }

    @Test
    public void testGetResult() {
        assertEquals(0, round.getResult());
    }

    @Test
    public void testToJson() {
        Round r = new Round(10, 0);
        JSONObject json = new JSONObject();
        json.put("betamount", 10);
        json.put("result", 0);
        JSONObject roundjson = r.toJson();
        assertEquals(roundjson.getInt("betamount"), json.getInt("betamount"));
        assertEquals(roundjson.getInt("result"), json.getInt("result"));
    }

    @Test
    public void testDealersTurn() {
        Round r = new Round(1,0);
        ArrayList<Card> loc = new ArrayList<>();
        loc.add(new Card("2"));
        loc.add(new Card("3"));
        Hand dealer = new Hand(loc);
        assertTrue(dealer.getHandValue() < 16);
        r.dealersTurn(dealer);
        assertTrue(dealer.getHandValue() >= 16);
        assertTrue(dealer.getHandValue() <= 27);
    }
}

 */
