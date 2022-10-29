package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// this code is based on the Json Serialize Demo @ https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Reader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads round from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    public int readSaveGameBalance() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSaveGameBalance(jsonObject);
    }

    public int parseSaveGameBalance(JSONObject jsonObject) {
        return jsonObject.getInt("savegamebalance");
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Game parseGame(JSONObject jsonObject) {
        Boolean gameover = jsonObject.getBoolean("gameover");
        Boolean playagain = jsonObject.getBoolean("playagain");
        int numwins = jsonObject.getInt("numwins");
        int numlosses = jsonObject.getInt("numlosses");
        Player player = parsePlayer(jsonObject.getJSONObject("player"));
        int savegamebalance = jsonObject.getInt("savegamebalance");

        ArrayList<Round> roundlist = new ArrayList<>();
        for (Object json : jsonObject.getJSONArray("rounds")) {
            JSONObject jround = (JSONObject) json;
            Round round = parseRound(jround.getJSONObject("round"));
            roundlist.add(round);
        }

        return new Game(gameover, playagain, numwins, numlosses, player, roundlist, savegamebalance);
    }


    // EFFECTS: parses Round from JSON object and returns it
    private Round parseRound(JSONObject jsonObject) {
        Hand dealer = parseHand(jsonObject.getJSONArray("hand"));
        Player player = parsePlayer(jsonObject.getJSONObject("player"));
        int betamount = parseBetAmount(jsonObject);
        int result = parseResult(jsonObject);

        return new Round(player, dealer, betamount, result);
    }

    private Player parsePlayer(JSONObject jsonObject1) {
        int balance = jsonObject1.getInt("balance");
        String name = jsonObject1.getString("name");
        Hand hand = parseHand(jsonObject1.getJSONArray("hand"));

        return new Player(name, balance, hand);
    }

    private Hand parseHand(JSONArray jsonArray) {
        ArrayList<Card> cards = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jcard = (JSONObject) json;
            Card card = new Card(jcard.getString("card"));
            cards.add(card);
        }
        return new Hand(cards);
    }

    private int parseBetAmount(JSONObject jsonObject1) {
        int betamount = jsonObject1.getInt("betamount");
        return betamount;
    }

    private int parseResult(JSONObject jsonObject1) {
        int result = jsonObject1.getInt("result");
        return result;
    }

    /*
    // MODIFIES: round
    // EFFECTS: parses Player, dealer, betamount, result from JSON object and adds them to workroom
    private void addThingies(Round round, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addRound(wr, nextThingy);
        }
    }

    // MODIFIES: round
    // EFFECTS: parses player from JSON object and adds it to workroom
    private void addRound(Round wr, JSONObject jsonObject) {
        Category category = Category.valueOf(jsonObject.getString("category"));
        Round round = new Round(player, dealer, betamount, result);
        wr.addThingy(thingy);
    }
    */

}
