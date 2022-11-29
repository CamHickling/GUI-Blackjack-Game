package model;

import org.json.JSONObject;
import ui.GUI;


import java.util.Objects;

import static java.util.Objects.isNull;


//Round represents one round of blackjack
public class Round {
    private Player player;
    private Hand dealer;
    private int betamount;

    private Result result;
    private GUI gui;

    //REQUIRES: player and dealer are not null, betamount > 1
    //EFFECTS: Creates a round instance with player, dealer and betamount.
    //         Call methods to initiate dealers turn then player turn.
    public Round(Player player, Hand dealer, int betamount, boolean test, GUI gui) {
        this.player = player;
        this.dealer = dealer;
        this.betamount = betamount;

        dealersTurn(dealer);
        //UserInterface.onAction(player.getHand(), "You are ");
        //UserInterface.playersTurn(player, test);
        gui.playersTurn();
        //UserInterface.onAction(dealer, "The Dealer is ");
    }

    //EFFECTS: Instantiates a new Round class with given betamount and result
    //          used to recontruct round from JSON file
    public Round(int betamount, Result result, GUI gui) {
        this.betamount = betamount;
        this.result = result;
        this.gui = gui;
    }

    //REQUIRES: dealer not null
    //MODIFIES: dealer's mycards field
    //EFFECTS: dealer draws a card to its hand until it reaches hand value 16 or greater
    public void dealersTurn(Hand dealer) {
        //!!! make functioning in GUI class
        //UserInterface.revealDealerCard(dealer.getMyCards().get(0).getName());
        while (dealer.getHandValue() < 16) {
            dealer.hit();
            EventLog.getInstance().logEvent(new Event("Dealer hit"));
            //UserInterface.dealerAction("hit");
        }
        dealer.stand();
        EventLog.getInstance().logEvent(new Event("Dealer stood"));
        //UserInterface.dealerAction("stand");
    }

    //EFFECTS: returns an integer value indicate the outcome of the round
    public Result judgeWinner(int playervalue, int dealervalue) {
        if (playervalue > 21 && dealervalue > 21) {
            this.result = Result.TIE;
            EventLog.getInstance().logEvent(new Event("Player tied round"));
        }  else if (playervalue > 21) {
            this.result = Result.LOSS;
            EventLog.getInstance().logEvent(new Event("Player lost round"));
        } else if (dealervalue > 21) {
            this.result = Result.WIN;
            EventLog.getInstance().logEvent(new Event("Player won round"));
        } else if (playervalue <= dealervalue) {
            this.result = Result.LOSS;
            EventLog.getInstance().logEvent(new Event("Player lost round"));
        } else { //(playervalue > dealervalue)
            this.result = Result.WIN;
            EventLog.getInstance().logEvent(new Event("Player won round"));
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
        String resultString = "";

        switch (result) {
            case TIE:
                resultString = "Tie";
                break;
            case WIN:
                resultString = "Win";
                break;
            case LOSS:
                resultString = "Loss";
                break;
        }
        json.put("result", resultString);
        return json;
    }

    @Override
    public String toString() {
        String resultString = "";
        int amt = betamount;
        String sign = " ";
        switch (result) {
            case WIN:
                resultString = "Win";
                sign = "  +";
                break;
            case TIE:
                resultString = "Tie";
                sign = " ";
                amt = 0;
                break;
            case LOSS:
                resultString = "Loss";
                amt *= -1;
                break;
        }
        return  resultString + ":  " + sign + amt;
    }

    public Result getResult() {
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
