package model;

import java.util.ArrayList;
import java.util.List;

// Card represents a playing card from a deck
public class Card {
    private static final List<String> DECK = new ArrayList<String>(
            List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"));


    private final String name;
    private final int value;

    //EFFECTS: instantiates a card instance, assigns a random name from deck and the appropriate card value
    public Card() {
        this.name = DECK.get((int) (Math.random() * DECK.size()));
        if (this.name == "J" || this.name == "Q" || this.name == "K") {
            this.value = 10;
        } else if (this.name == "A") {
            this.value = 11;
        } else {
            this.value = Integer.valueOf(this.name);
        }
    }

    //EFFECTS: returns the card's name
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns the card's value
    public int getValue() {
        return this.value;
    }
}
