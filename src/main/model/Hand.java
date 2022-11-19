package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Hand represents a list of cards that a person has been dealt
public class Hand {

    private final ArrayList<Card> cards;
    private int handValue;

    //EFFECTS: creates a new Hand instance, and hits to add two cards to the hand.
    public Hand() {
        this.cards = new ArrayList<>();
        hit();
        hit();
    }

    //EFFECTS: creates a new Hand instance, using the given list of card objects
    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
        this.handValue = calculateHandValue();
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
    public ArrayList<Card> getMyCards() {
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

    @Override
    public String toString() {
        String delim = "";
        String s = "";
        for (Card c: cards) {
            s += c.getName() + " ";
        }
        s += "has value: ";
        int hv = getHandValue();
        s += hv;
        if (hv == 21) {
            s += " Blackjack!";
        } else if (hv > 21) {
            s += " Bust!";
        }
        return s;
    }

    /*
    public JSONArray toJson() {
        JSONArray jarray = new JSONArray();
        for (Card c : cards) {
            JSONObject json = new JSONObject();
            json.put("card", c.toString());
            jarray.put(json);
        }
        return jarray;
    }

     */
}
