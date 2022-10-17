package model;

import java.util.ArrayList;
import java.util.List;

//Hand represents a list of cards that a person has been dealt
public class Hand {

    private final List<Card> cards;
    private int handValue;

    //EFFECTS: creates a new Hand instance, and hits to add two cards to the hand.
    public Hand() {
        this.cards = new ArrayList<>();
        hit();
        hit();
    }

    //MODIFIES: this
    //EFFECTS: adds a card to the hand, and calls a method to update the hand value
    public void hit() {
        this.cards.add(new Card());
        this.handValue = calculateHandValue();
    }

    //EFFECTS: none
    public void stand() {
        // do nothing
    }

    //EFFECTS: returns the list of cards in the hand
    public List<Card> getMyCards() {
        return this.cards;
    }

    //EFFECTS: calculates the sum of the card values in the list of cards
    private int calculateHandValue() {
        int val = 0;
        for (Card c : this.cards) {
            val += c.getValue();
        }
        return val;
    }

    //EFFECTS: returns the current value of the hand of cards
    public int getHandValue() {
        return this.handValue;
    }

    //EFFECTS: returns a string of the cards in a hand
    public String showMyCards() {
        String s = "";
        for (Card c: this.cards) {
            s += c.getName() + " ";
        }
        return s;
    }
}
