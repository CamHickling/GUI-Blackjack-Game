package model;

import org.json.JSONObject;

//Player represents blackjack player
public class Player {
    private Hand hand;
    private int balance;

    //REQUIRES: initialBalance > 0
    //EFFECTS: creates a Player instance, assigns a name and balance, and draws a hand of cards
    public Player(int initialBalance) {
        this.balance = initialBalance;
        drawHand();
    }

    //MODIFIES: this
    //EFFECTS: draws a new hand of cards and assigns it to the player
    public void drawHand() {
        this.hand = new Hand();
    }

    //MODIFIES: this
    //EFFECTS: tells the user they hit, and calls their hand to hit
    public void hit() {
        hand.hit();
        EventLog.getInstance().logEvent(new Event("Player hit"));
    }

    //MODIFIES: this
    //EFFECTS: tells the user they stood, and calls their hand to stand
    public void stand() {
        hand.stand();
        EventLog.getInstance().logEvent(new Event("Player stood"));
    }

    //EFFECTS: returns the player's balance
    public int getBalance() {
        return this.balance;
    }

    //REQUIRES: betAmount != 0
    //MODIFIES: this
    //EFFECTS: increments the player's balance by the betamount, but may not be less than 0
    public void adjustBalance(int betAmount) {
        this.balance = Math.max(0, this.balance + betAmount);
    }

    //EFFECTS: returns the player's hand of cards
    public Hand getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return hand.toString() + "" + balance;
    }
}
