package model;

import java.util.ArrayList;
import static java.util.Map.entry;
import java.util.List;
import java.util.Map;

// Card represents a playing card from a deck
public class Card {
    private static final List<String> DECK = new ArrayList<String>(
            List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"));
    private static final Map<String, Integer> CARD_VALUE = Map.ofEntries(
            entry("2", 2),
            entry("3", 3),
            entry("4", 4),
            entry("5", 5),
            entry("6", 6),
            entry("7", 7),
            entry("8", 8),
            entry("9", 9),
            entry("10", 10),
            entry("J", 10),
            entry("Q", 10),
            entry("K", 10),
            entry("A", 11));

    private final String name;
    private final int value;

    //EFFECTS: instantiates a card instance, assigns a random name from deck and the appropriate card value
    public Card() {
        this.name = DECK.get((int) (Math.random() * DECK.size()));
        this.value = CARD_VALUE.get(this.name);
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
