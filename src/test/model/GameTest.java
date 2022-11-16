package model;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import ui.UserInterface;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;


public class GameTest {


    public Game game;

    @BeforeEach
    public void setup() {
        game = new Game("Cam", 1000, true);
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
    public void testReturnRound() {
        assertNull(game.getRound());
    }

    @Test
    public void testGetPlayAgain(){
        assertTrue(game.getPlayAgain());
    }

    @Test
    public void testGetGameOver(){
        assertFalse(game.getGameOver());
    }

    @Test
    public void testToJSON() {
        ArrayList<Round> lor = new ArrayList<Round>();
        lor.add(new Round(0,0));
        Game g = new Game(false, true, 0,0, lor, 100, true);
        JSONObject json = new JSONObject();
        json.put("gameover", false);
        json.put("playagain", true);
        json.put("numwins", 0);
        json.put("numlosses", 0);
        json.put("savegamebalance", 0);

        JSONArray jarray = new JSONArray();
        for (Round r: lor) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("round", r.toJson());
            jarray.put(jsonObject);
        }
        json.put("rounds", jarray);

        JSONObject gson = g.toJson();
        assertTrue(gson.getInt("numwins") >= json.getInt("numwins"));
        assertTrue(gson.getInt("numlosses") >= json.getInt("numlosses"));
        //assertEquals(gson.getInt("savegamebalance") >= json.getInt("savegamebalance"));
        assertEquals(gson.getBoolean("gameover"), json.getBoolean("gameover"));
        assertEquals(gson.getBoolean("playagain"), false);

        JSONArray gamerounds = gson.getJSONArray("rounds");
        JSONArray jsonrounds = json.getJSONArray("rounds");

        ArrayList<Integer> grs = new ArrayList<>();
        ArrayList<Integer> jrs = new ArrayList<>();

        for (int i = 0; i < gamerounds.length(); i++) {
            JSONObject gr = gamerounds.getJSONObject(i).getJSONObject("round");
            grs.add(gr.getInt("betamount"));
            grs.add(gr.getInt("result"));
        }

        for (int k = 0; k < jsonrounds.length(); k++) {
            JSONObject jr = jsonrounds.getJSONObject(k).getJSONObject("round");
            jrs.add(jr.getInt("betamount"));
            jrs.add(jr.getInt("result"));
        }

        Collections.sort(grs);
        Collections.sort(jrs);
        for (int j = 0; j < jrs.size(); j++) {
            assertEquals(grs.get(j), jrs.get(j));
        }
    }

    @Test
    public void testContinuePlayingBalanceNonZero() {
        Player play = new Player("", 1);
        assertEquals(false, game.getGameOver());
        assertEquals(true, game.getPlayAgain());
        game.continuePlaying(play, true);
        assertEquals(false, game.getGameOver());
        assertEquals(false, game.getPlayAgain());
    }

    @Test
    public void testContinuePlayingBalanceZero() {
        Player play = new Player("", 0);
        assertEquals(false, game.getGameOver());
        assertEquals(true, game.getPlayAgain());
        game.continuePlaying(play, true);
        assertEquals(true, game.getGameOver());
        assertEquals(true, game.getPlayAgain());
    }

    @Test
    public void testGetSaveGameBalance() {
        String returnvalue = game.fetchSaveGameBalance(false);
        Reader r = new Reader("./data/game.json");
        int sgb = 0;
        try {
            sgb = r.readSaveGameBalance(false);
        } catch (IOException e) {
            fail();
        }
        assertEquals(String.valueOf(sgb), returnvalue);
        assertEquals(sgb, Integer.parseInt(returnvalue));
    }

    @Test
    public void testGetSaveGameBalance2() {
        String returnvalue = game.fetchSaveGameBalance(true);
        Reader r = new Reader("./data/game.json");
        int sgb = 0;
        try {
            sgb = r.readSaveGameBalance(true);
            fail();
        } catch (IOException e) {
            assertEquals(Integer.parseInt(returnvalue), game.getSaveGameBalance());
        }
    }

    @Test
    public void testPlayGame() {
        Game g = new Game(false, true, 0, 0, new ArrayList<Round>(), 1, true);
        assertTrue(g.getPlayer().getHand().getMyCards().size() >= 2);
        assertTrue(g.getDealer().getMyCards().size() >= 2);
        //assertNotNull(game.getRound());
        //assertTrue(g.getRoundList().size() > 0);

    }

    @Test
    public void testShowCurrentSaveGameBalanceFileExists() {
        game.showCurrentSaveGameBalance("./data/game.json");

    }

    @Test
    public void testShowCurrentSaveGameBalanceFileDNE() {
        game.showCurrentSaveGameBalance("./data/x.json");
        assertEquals(game.getSaveGameBalance(), game.getPlayer().getBalance());
    }

    @Test
    public void testGetRoundList() {
        Game game = new Game("", 0, true);
        assertEquals(game.getRoundList(), new ArrayList<>());
    }

}
