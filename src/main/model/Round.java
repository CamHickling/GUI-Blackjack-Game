package model;

import org.json.JSONObject;
import ui.UserInterface;



//Round represents one round of blackjack
public class Round {
    private Player player;
    private Hand dealer;
    private int betamount;

    private int result;

    //REQUIRES: player and dealer are not null, betamount > 1
    //EFFECTS: Creates a round instance with player, dealer and betamount.
    //         Call methods to initiate dealers turn then player turn.
    public Round(Player player, Hand dealer, int betamount, boolean testassignment, boolean testbehavior) {
        this.player = player;
        this.dealer = dealer;
        this.betamount = betamount;

        if (!testassignment) {
            dealersTurn(dealer);
            UserInterface.onAction(player.getHand(), "You are ");
            if (!testbehavior) {
                UserInterface.playersTurn(player);
            } else {
                dealersTurn(player.getHand());
            }
            UserInterface.onAction(dealer, "The Dealer is ");
        }
    }

    //EFFECTS: Instantiates a new Round class with given player, dealer, betamount, result
    public Round(Player player, Hand dealer, int betamount, int result) {
        this.player = player;
        this.dealer = dealer;
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

    @Override
    public String toString() {
        return player.toString() + " !" + dealer.toString() + " " + betamount + " " + result;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player", player.toJson());
        json.put("hand", dealer.toJson());
        json.put("betamount", betamount);
        json.put("result", result);
        return json;
    }

    public int getResult() {
        return result;
    }
}
