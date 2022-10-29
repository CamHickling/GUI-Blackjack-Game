package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Reader;
import ui.UserInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Game {

    private boolean gameover;
    private boolean playagain;
    private int numwins;
    private int numlosses;
    private ArrayList<Round> roundlist = new ArrayList<>();
    private Player player;
    private Hand dealer;
    private Round round;

    private int savegamebalance;

    public Game(String name, int balance, boolean testassignment) {
        this.gameover = false;
        this.playagain = true;
        this.player = new Player(name, balance);
        this.savegamebalance = 0;

        if (!testassignment) {
            new Game(true, this.player);
        }
    }

    public Game(boolean gameover, boolean playagain, int numwins, int numlosses, Player player, ArrayList<Round> rl,
                int savegamebalance) {
        this.gameover = gameover;
        this.playagain = playagain;
        this.numwins = numwins;
        this.numlosses = numlosses;
        this.roundlist = rl;
        this.player = player;
        this.savegamebalance = savegamebalance;

        new Game(playagain, player);
    }

    public Game(boolean playagain, Player player) {
        // loop that plays one rounds then repeats unless the user asks to leave, or reaches 0 balance and loses
        this.playagain = playagain;
        this.player = player;
        while (this.playagain && !this.gameover) {
            int betamount = UserInterface.askBetAmount(this.player.getBalance());
            this.player.drawHand();
            this.dealer = new Hand();
            this.round = new Round(this.player, this.dealer, betamount, false, false);

            //record result and add to the list of rounds
            recordResult(player, round.judgeWinner(player.getHand().getHandValue(), dealer.getHandValue()));
            roundlist.add(round);

            //determine whether to play another round
            continuePlaying(this.player);
        }
        File f = new File("./data/rounds.json");
        if (f.length() > 0) {
            UserInterface.printMessage("Your save game has a balance of: " + getSaveGameBalance());
        }
        UserInterface.askToSave(this);
    }

    private String getSaveGameBalance() {
        Reader r = new Reader("./data/rounds.json");
        try {
            int sgb = r.readSaveGameBalance();
            return String.valueOf(sgb);
        } catch (IOException e) {
            return String.valueOf(savegamebalance);
        }

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
            UserInterface.roundMessage(player, "You had", numwins, numlosses, getWinrate());
        }
    }

    //REQUIRES: player not null, result is either 0,1,2
    //MODIFIES: this
    //EFFECTS: adjusts wins and losses, player balance, and calls user interface to send out a message
    public void recordResult(Player player, int result) {
        String message;
        ArrayList<String> messagelist = new ArrayList<>();
        messagelist.add("You tied.");
        messagelist.add("You lost.");
        messagelist.add("You won!");
        if (result == 2) {
            this.numwins += 1;
        } else if (result == 1) {
            this.numlosses += 1;
        }
        if (result != 0) {
            int direction = ((result * 2) - 3);
            if (!isNull(round)) {
                player.adjustBalance(direction * round.getBetAmount());
            }
        }
        float winrate = getWinrate();
        UserInterface.roundMessage(player, messagelist.get(result), this.numwins, this.numlosses, winrate);
    }

    //EFFECTS: returns the number of wins
    public int getNumwins() {
        return this.numwins;
    }

    //EFFECTS: returns the number of losses
    public int getNumlosses() {
        return this.numlosses;
    }

    //EFFECTS: returns a winrate representing the ratio of the player's wins to total games excluding ties
    //          will return a 0% winrate on no wins no losses
    public float getWinrate() {
        int gamesplayed = Math.max(this.numwins + this.numlosses, 1);
        return ((float) this.numwins / gamesplayed) * 100;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Round getRound() {
        return this.round;
    }

    public boolean getGameOver() {
        return this.gameover;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameover", gameover);
        json.put("playagain", playagain);
        json.put("numwins", numwins);
        json.put("numlosses", numlosses);
        json.put("player", player.toJson());
        json.put("savegamebalance", savegamebalance);

        JSONArray jarray = new JSONArray();
        for (Round r: roundlist) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("round", r.toJson());
            jarray.put(jsonObject);
        }
        json.put("rounds", jarray);

        return json;
    }

}
