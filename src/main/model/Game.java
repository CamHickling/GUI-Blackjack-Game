package model;

import ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

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

    public Game(String name, int balance, boolean testassignment) {
        this.gameover = false;
        this.playagain = false;
        this.player = new Player(name, balance);

        if (!testassignment) {
            // loop that plays one rounds then repeats unless the user asks to leave, or reaches 0 balance and loses
            do {
                int betamount = UserInterface.askBetAmount(this.player.getBalance());
                this.player.drawHand();
                this.dealer = new Hand();
                this.round = new Round(this.player, this.dealer, betamount, false, false);

                //record result and add to the list of rounds
                recordResult(player, round.judgeWinner(player.getHand().getHandValue(), dealer.getHandValue()));
                roundlist.add(round);

                //determine whether to play another round
                continuePlaying(this.player);

            } while (this.playagain && !this.gameover);
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

}
