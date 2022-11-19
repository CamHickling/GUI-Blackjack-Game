/*
import model.Game;
import model.Round;
import org.json.JSONObject;
import persistence.Reader;
import org.junit.jupiter.api.Test;
import persistence.Writer;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WriterReaderTest {

    @Test
    public void testReader() {
        String s = "";
        Reader r = new Reader(s);
        assertEquals(s, r.getSource());
    }

    @Test
    public void testRead() {
        Reader r = new Reader("./data/test.json");
        Writer w = new Writer("./data/test.json");
        Game game = new Game("", 100, true);
        try {
            try {
                w.open();
                w.write(game);
                w.close();
            } catch(Exception e) {
                fail();
            }

            String jsonData = r.readFile("./data/test.json");
            JSONObject jsonObject = new JSONObject(jsonData);
            try {
                Game readgame = r.read("./data/test.json", true);
                assertEquals(readgame, r.parseGame(jsonObject, true));
            } catch (Exception e) {
                fail();
            }
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testReadSaveGameBalance() {
        Reader r = new Reader("./data/test.json");
        Writer w = new Writer("./data/test.json");
        Game game = new Game(false, false, 0,0, new ArrayList<Round>(), 0, true);

        try {
            w.open();
            w.write(game);
            w.close();
        } catch(Exception e) {
            fail();
        }

        try {
            int i = r.readSaveGameBalance(false);
            assertTrue((int) i == i);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testReadSaveGameBalanceFail() {
        Reader r = new Reader("./data/test.json");
        try {
            r.readSaveGameBalance(true);
            fail();
        } catch (IOException e) {}
    }


    @Test
    public void testParseSaveGameBalance() {
        Reader r = new Reader("./data/test.json");
        JSONObject j = new JSONObject();
        j.put("savegamebalance", 1);
        assertEquals(r.parseSaveGameBalance(j), j.getInt("savegamebalance"));
    }

    @Test
    public void testReadFile() {
        Reader r = new Reader("./data/test.json");
        try {
            assertTrue(r.readFile("./data/test.json") instanceof String);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReadFileFail() {
        Reader r = new Reader("./data/test2.json");
        try {
            r.readFile("./data/test2.json");
            fail();
        } catch (IOException e) {}
    }

    @Test
    public void testParseGame() {
        Reader r = new Reader("./data/test.json");
        Game game = new Game("", 0, true);
        JSONObject j = game.toJson();

        Boolean gameover = j.getBoolean("gameover");
        Boolean playagain = j.getBoolean("playagain");
        int numwins = j.getInt("numwins");
        int numlosses = j.getInt("numlosses");
        int savegamebalance = j.getInt("savegamebalance");

        ArrayList<Round> roundlist = new ArrayList<>();
        for (Object json : j.getJSONArray("rounds")) {
            JSONObject jround = (JSONObject) json;
            Round round = r.parseRound(jround.getJSONObject("round"));
            roundlist.add(round);
        }

        assertTrue(r.parseGame(j,true).equals(new Game("", 0, true)));
    }

    @Test
    public void testParseRound() {
        Reader r = new Reader("./data/test.json");
        Round round = new Round(0, 0);
        JSONObject j = round.toJson();
        int betamount = r.parseBetAmount(j);
        int result = r.parseResult(j);
        assertTrue(r.parseRound(j).equals(new Round(betamount, result)));
    }

    @Test
    public void testParseBetAmount() {
        Reader r = new Reader("./data/test.json");
        JSONObject j = new JSONObject();
        j.put("betamount", 0);
        assertEquals(r.parseBetAmount(j), j.getInt("betamount"));
    }

    @Test
    public void testParseResult() {
        Reader r = new Reader("./data/test.json");
        JSONObject j = new JSONObject();
        j.put("result", 0);
        assertEquals(r.parseResult(j), j.getInt("result"));
    }


}

 */




