package model;

import java.util.ArrayList;
import java.util.List;

// Card represents a playing card from a deck
public class Card {
    private static List<String> DECK;

    private final String name;
    private final int value;

    //EFFECTS: instantiates a card instance, assigns a random name from deck and the appropriate card value
    public Card() {
        DECK = new ArrayList<String>();
        for (int i = 2; i <= 10; i++) {
            DECK.add(String.valueOf(i));
        }
        DECK.add("J");
        DECK.add("Q");
        DECK.add("K");
        DECK.add("A");

        this.name = DECK.get((int) (Math.random() * DECK.size()));
        this.value =  findValue(this.name);
    }

    //EFFECTS: instantiates a card instance, assigns the given name and finds value
    public Card(String name) {
        this.name = name;
        this.value = findValue(this.name);
    }

    //returns the appropriate value for the given card name given
    public int findValue(String name) {
        if (this.name.equals("J") || this.name.equals("Q") || this.name.equals("K")) {
            return 10;
        } else if (this.name == "A") {
            return 11;
        } else {
            return Integer.valueOf(this.name);
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

    @Override
    public String toString() {
        return this.name;
    }
}
