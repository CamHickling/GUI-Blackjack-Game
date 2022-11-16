package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Reader;
import ui.UserInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

    //EFFECTS: instantiates a game object
    public Game(String name, int balance, boolean test) {
        this.gameover = false;
        this.playagain = true;
        this.player = new Player(name, balance);
        this.savegamebalance = 0;
        this.numwins = 0;
        this.numlosses = 0;

        if (!test) {
            playGame(test);
        }
    }

    //EFFECTS:  instantiates a game object
    public Game(boolean gameover, boolean playagain, int numwins, int numlosses, ArrayList<Round> rl,
                int savegamebalance, boolean test) {
        this.gameover = gameover;
        this.playagain = true;
        this.numwins = numwins;
        this.numlosses = numlosses;
        this.roundlist = rl;
        this.savegamebalance = savegamebalance;
        this.player = new Player("", this.savegamebalance);
        this.dealer = new Hand();

        playGame(test);
    }

    //EFFECTS: plays through a game of blackjack
    public void playGame(boolean test) {

        while (this.playagain && !this.gameover) {

            int betamount = UserInterface.askBetAmount(this.player.getBalance(), test);

            this.player.drawHand();
            this.dealer = new Hand();
            this.round = new Round(this.player, this.dealer, betamount, test);

            //record result and add to the list of rounds
            recordResult(player, round.judgeWinner(player.getHand().getHandValue(), dealer.getHandValue()));
            roundlist.add(round);

            //determine whether to play another round
            continuePlaying(this.player, test);
        }
        showCurrentSaveGameBalance("./data/game.json");
        UserInterface.askToSave(this, test);
    }

    //MODIFIES: this
    //EFFECTS: gets the save game balance if it is there
    public String fetchSaveGameBalance(boolean test) {
        Reader r = new Reader("./data/game.json");
        try {
            int sgb = r.readSaveGameBalance(test);
            this.savegamebalance = sgb;
            return String.valueOf(sgb);
        } catch (IOException e) {
            return String.valueOf(this.savegamebalance);
        }
    }

    //EFFECTS: if a file exists print the current balance
    //          otherwise set it from player balance
    public void showCurrentSaveGameBalance(String path) {
        File f = new File(path);
        if (f.length() > 3) {
            UserInterface.printMessage("Your save game has a balance of: " + fetchSaveGameBalance(false));
        } else {
            this.savegamebalance = getPlayer().getBalance();
        }
    }

    //EFFECT: return savegamebalance
    public int getSaveGameBalance() {
        return this.savegamebalance;
    }

    //REQUIRES: player not null
    //MODIFIES: this
    //EFFECTS: set game over to true if the player has no more money, then calls a user interface to tell the user
    //         otherwise calls user interface to ask user if they want to play again
    public void continuePlaying(Player player, boolean test) {
        this.gameover = player.getBalance() == 0;
        if (!this.gameover) {
            this.playagain = UserInterface.playAgain(test);
        } else {
            UserInterface.gameOver();
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

    //EFFECTS: returns player in game
    public Player getPlayer() {
        return this.player;
    }

    //EFFECTS: returns dealer in game
    public Hand getDealer() {
        return this.dealer;
    }

    //EFFECTS: returns current round object in game
    public Round getRound() {
        return this.round;
    }

    //EFFECTS: returns game over bool
    public boolean getGameOver() {
        return this.gameover;
    }

    //EFFECTS: returns playagain bool
    public boolean getPlayAgain() {
        return this.playagain;
    }

    //EFFECTS: returns roundlist
    public ArrayList<Round> getRoundList() {
        return this.roundlist;
    }

    //EFFECTS: returns fields as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameover", gameover);
        json.put("playagain", playagain);
        json.put("numwins", numwins);
        json.put("numlosses", numlosses);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return gameover == game.gameover
                && playagain == game.playagain
                && numwins == game.numwins
                && numlosses == game.numlosses
                && savegamebalance == game.savegamebalance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameover, playagain, numwins, numlosses, savegamebalance);
    }
}
