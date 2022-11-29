package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Reader;
import ui.GUI;

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
    private ArrayList<Round> roundlist;
    private Player player;
    private Hand dealer;
    private Round round;

    private int savegamebalance;
    private int balance;
    private GUI gui;

    //EFFECTS: instantiates a game object
    public Game(int balance, boolean test, GUI gui, boolean newgame) {
        this.gameover = balance <= 0;
        this.player = new Player(balance);
        this.dealer = new Hand();
        this.balance = balance;
        this.savegamebalance = 0;
        this.numwins = 0;
        this.numlosses = 0;
        this.gui = gui;
        this.roundlist = new ArrayList<>();
    }

    //EFFECTS:  instantiates a game object
    public Game(int numwins, int numlosses, ArrayList<Round> rl,
                int savegamebalance, boolean test, GUI gui) {
        this.numwins = numwins;
        this.numlosses = numlosses;
        this.roundlist = rl;
        this.savegamebalance = savegamebalance;
        this.balance = savegamebalance;
        this.player = new Player(this.savegamebalance);
        this.dealer = new Hand();
        this.gameover = balance <= 0;
        this.gui = gui;
    }

    //EFFECTS: plays through a game of blackjack
    public void playGame(boolean test, int betamount) {

        //int betamount = UserInterface.askBetAmount(this.player.getBalance(), test);
        this.balance = player.getBalance();
        this.player.drawHand();
        this.dealer = new Hand();
        this.round = new Round(this.player, this.dealer, betamount, test, this.gui);

        //determine whether to play another round
        //continuePlaying(this.player, test);
        showCurrentSaveGameBalance("./data/game.json");
    }

    //MODIFIES: this
    //EFFECTS: gets the save game balance if it is there
    public String fetchSaveGameBalance(boolean test) {
        Reader r = new Reader("./data/game.json", gui);
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
            //UserInterface.printMessage("Your save game has a balance of: " + fetchSaveGameBalance(false));
            this.savegamebalance = getPlayer().getBalance();
        } else {
            this.savegamebalance = 0;
        }
    }

    //EFFECT: return savegamebalance
    public int getSaveGameBalance() {
        return this.savegamebalance;
    }

    /*
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
     */

    //REQUIRES: player not null, result is either 0,1,2
    //MODIFIES: this
    //EFFECTS: adjusts wins and losses, player balance, and calls user interface to send out a message
    public void recordResult(Player player, Result result) {
        String message = "Tie";
        String title = "You tied.";
        switch (result) {
            case WIN:
                this.numwins += 1;
                message = "Win";
                title = "You won!";
                if (!isNull(round)) {
                    player.adjustBalance(1 * round.getBetAmount());
                }
            case LOSS:
                this.numlosses += 1;
                message = "Loss";
                title = "You lost";
                if (!isNull(round)) {
                    player.adjustBalance(-1 * round.getBetAmount());
                }
        }
        float winrate = getWinrate();
        roundlist.add(round);
        EventLog.getInstance().logEvent(new Event("previous round added to roundlist"));
        gui.setBanner(title);
        gui.updateGUI(false);
        //UserInterface.roundMessage(player, message, this.numwins, this.numlosses, winrate);

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
        //json.put("gameover", gameover);
        json.put("numwins", numwins);
        json.put("numlosses", numlosses);
        json.put("savegamebalance", getPlayer().getBalance());

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
                && numwins == game.numwins
                && numlosses == game.numlosses
                && savegamebalance == game.savegamebalance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameover, numwins, numlosses, savegamebalance);
    }

    public int getBalance() {
        return balance;
    }
}
