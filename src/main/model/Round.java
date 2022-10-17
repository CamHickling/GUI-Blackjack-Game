package model;

import ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

//Round represents one round of blackjack
public class Round {
    private Player player;
    private Hand dealer;
    private int betamount;

    private int numwins;
    private int numlosses;

    //REQUIRES: player and dealer are not null, betamount > 1
    //EFFECTS: Creates a round instance with player, dealer and betamount.
    //         Call methods to initiate dealers turn then player turn.
    public Round(Player player, Hand dealer, int betamount, boolean testassignment, boolean testbehavior) {
        this.player = player;
        this.dealer = dealer;
        this.betamount = betamount;
        this.numlosses = 0;
        this.numwins = 0;

        if (!testassignment) {
            dealersTurn(dealer);
            UserInterface.onAction(player.getHand(), "You are ");
            if (!testbehavior) {
                UserInterface.playersTurn(player);
            } else {
                dealersTurn(player.getHand());
            }
            UserInterface.onAction(dealer, "The Dealer is ");
            recordResult(player, judgeWinner(player.getHand().getHandValue(), dealer.getHandValue()));
        }
    }

    //REQUIRES: dealer not null
    //MODIFIES: dealer's mycards field
    //EFFECTS: dealer draws a card to its hand until it reaches hand value 16 or greater
    public void dealersTurn(Hand dealer) {
        UserInterface.revealDealerCard(dealer.getMyCards().get(0).getName());
        while (dealer.getHandValue() < 16) {
            dealer.hit();
            UserInterface.dealerAction("hit");
        }
        dealer.stand();
        UserInterface.dealerAction("stand");
    }

    //EFFECTS: returns an integer value indicate the outcome of the round
    //          0 - player dealer tie
    //          1 - dealer beats player
    //          2 - player beats dealer
    public int judgeWinner(int playervalue, int dealervalue) {
        if (playervalue > 21 && dealervalue > 21) {
            return 0;
        }  else if (playervalue > 21) {
            return 1;
        } else if (dealervalue > 21) {
            return 2;
        } else if (playervalue <= dealervalue) {
            return 1;
        } else { //(playervalue > dealervalue)
            return 2;
        }
    }

    //REQUIRES: player, round not null, result is either 0,1,2
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
            player.adjustBalance(direction * getBetAmount());
        }
        float winrate = getWinrate(this.numwins, this.numlosses);
        UserInterface.roundMessage(player, messagelist.get(result), this.numwins, this.numlosses, winrate);
    }

    //EFFECTS: returns a winrate representing the ratio of the player's wins to total games excluding ties
    //          will return a 0% winrate on no wins no losses
    public float getWinrate(int wins, int losses) {
        int gamesplayed = Math.max(wins + losses, 1);
        return ((float) wins / gamesplayed) * 100;
    }

    public int getNumLosses() {
        return this.numlosses;
    }

    public int getNumWins() {
        return this.numwins;
    }


    //EFFECTS: returns the betamount of the round
    public int getBetAmount() {
        return betamount;
    }

    //EFFECTS: returns the round's dealer hand object
    public Hand getDealer() {
        return this.dealer;
    }

    //EFFECTS: returns the round's player object
    public Player getPlayer() {
        return this.player;
    }
}
