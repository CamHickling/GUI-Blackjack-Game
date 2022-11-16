package model;

import org.json.JSONObject;
import ui.UserInterface;

import java.util.Objects;


//Round represents one round of blackjack
public class Round {
    private Player player;
    private Hand dealer;
    private int betamount;

    private int result;

    //REQUIRES: player and dealer are not null, betamount > 1
    //EFFECTS: Creates a round instance with player, dealer and betamount.
    //         Call methods to initiate dealers turn then player turn.
    public Round(Player player, Hand dealer, int betamount, boolean test) {
        this.player = player;
        this.dealer = dealer;
        this.betamount = betamount;

        dealersTurn(dealer);
        UserInterface.onAction(player.getHand(), "You are ");
        UserInterface.playersTurn(player, test);
        UserInterface.onAction(dealer, "The Dealer is ");
    }

    //EFFECTS: Instantiates a new Round class with given betamount and result
    //          used to recontruct round from JSON file
    public Round(int betamount, int result) {
        this.betamount = betamount;
        this.result = result;
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
            this.result = 0;
        }  else if (playervalue > 21) {
            this.result = 1;
        } else if (dealervalue > 21) {
            this.result = 2;;
        } else if (playervalue <= dealervalue) {
            this.result = 1;
        } else { //(playervalue > dealervalue)
            this.result = 2;
        }
        return this.result;
    }

    //EFFECTS: returns the betamount of the round
    public int getBetAmount() {
        return this.betamount;
    }

    //EFFECTS: returns the round's dealer hand object
    public Hand getDealer() {
        return this.dealer;
    }

    //EFFECTS: returns the round's player object
    public Player getPlayer() {
        return this.player;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("betamount", betamount);
        json.put("result", result);
        return json;
    }

    public int getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Round round = (Round) o;
        return betamount == round.betamount
                && result == round.result
                && Objects.equals(player, round.player)
                && Objects.equals(dealer, round.dealer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, dealer, betamount, result);
    }
}
