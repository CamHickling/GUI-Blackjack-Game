package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.GUI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;


// this code is based on the Json Serialize Demo @ https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Reader {

    private String source;
    private GUI gui;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source, GUI gui) {
        this.source = source;
        this.gui = gui;
    }

    // EFFECTS: reads round from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read(String path, boolean test) throws IOException {
        String jsonData = readFile(path);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject, test);
    }

    //EFFECTS: reads save game balance from file
    public int readSaveGameBalance(boolean test) throws IOException {
        if (test) {
            throw new IOException();
        }
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSaveGameBalance(jsonObject);
    }

    //EFFECTS: parses the JSON object to find save game balance
    public int parseSaveGameBalance(JSONObject jsonObject) {
        return jsonObject.getInt("savegamebalance");
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses the game object from a JSON object
    public Game parseGame(JSONObject jsonObject, boolean test) {
        //Boolean gameover = jsonObject.getBoolean("gameover");
        int numwins = jsonObject.getInt("numwins");
        int numlosses = jsonObject.getInt("numlosses");
        int savegamebalance = jsonObject.getInt("savegamebalance");

        ArrayList<Round> roundlist = new ArrayList<>();
        for (Object json : jsonObject.getJSONArray("rounds")) {
            JSONObject jround = (JSONObject) json;
            Round round = parseRound(jround.getJSONObject("round"));
            roundlist.add(round);
        }

        if (test) {
            return new Game(savegamebalance, test, gui, false);
        } else {
            return new Game(numwins, numlosses, roundlist, savegamebalance, test, gui);
        }
    }

    // EFFECTS: parses Round from JSON object and returns it
    public Round parseRound(JSONObject jsonObject) {
        int betamount = parseBetAmount(jsonObject);
        Result result = parseResult(jsonObject);
        return new Round(betamount, result, gui);
    }

    //EFFECTS: parses bet amount from JSON and returns it
    public int parseBetAmount(JSONObject jsonObject1) {
        int betamount = jsonObject1.getInt("betamount");
        return betamount;
    }

    //EFFECTS: parses result from json object and returns it
    public Result parseResult(JSONObject jsonObject1) {
        String result = jsonObject1.getString("result");
        Result r = Result.TIE;
        if (result.equals("Win")) {
            r = Result.WIN;
        } else if (result.equals("Loss")) {
            r = Result.LOSS;
        }
        return r;
    }

    //EFFECTS: returns source
    public String getSource() {
        return this.source;
    }

}
