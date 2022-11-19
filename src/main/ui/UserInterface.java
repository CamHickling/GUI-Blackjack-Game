/*
package ui;

import model.Game;
import model.Hand;
import model.Player;
import model.Round;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class UserInterface {


    private static final String path = "./data/game.json";

    static Scanner scan = new Scanner(System.in);

    //EFFECTS: prints a message out to the user and waits for a name in response
    public static String askName() {
        System.out.println("Please print your name using only alphabetical characters:");
        return scan.nextLine();
    }

    //EFFECTS: prints a message out to the user and waits for a starting balance in response
    public static int askStartingBalance() {
        System.out.println("Please print a positive integer starting balance:");
        return scan.nextInt();
    }

    //EFFECTS: prints a message out to the user and waits for a bet amount in response
    public static int askBetAmount(int balance, boolean test) {
        System.out.println("Please print an integer bet amount between 1 and " + balance + " inclusive:");
        if (test) {
            return 1;
        }
        return scan.nextInt();
    }

    //EFFECTS: prints a message to the screen indicating whether the player's action
    public static void printMessage(String msg) {
        System.out.println(msg);
    }

    //EFFECTS: prints a message, list of card, and hand value to the user
    public static void onAction(Hand hand, String subject) {
        System.out.println(subject + "holding " + hand.toString() + ": " + hand.getHandValue());
    }

    //REQUIRES: player not null
    //MODIFIES: player's hand's mycards field
    //EFFECTS: player can select to continue drawing a card to their hand
    //         until they select to stand
    public static void playersTurn(Player player, Boolean test) {
        Boolean stand = null;
        Boolean action1  = test;
        Boolean action2 = test;
        String userIn;
        do {
            System.out.println("Would you like to hit or stand? \"h\" for hit, \"s\" for stand");
            if (action1 && action2) {
                userIn = "h";
            } else if (!action1 && action2) {
                userIn = "s";
            } else {
                if (isNull(stand)) {
                    scan.next();
                }
                userIn = scan.nextLine();
            }
            action1 = false;
            stand = userIn.equals("s");
            hitIfNotStand(player, stand);
        } while (!stand);
        player.stand();
    }

    private static void hitIfNotStand(Player player, Boolean stand) {
        if (!stand) {
            player.hit();
            onAction(player.getHand(),"You are ");
        }
    }

    //EFFECTS: returns true if the user indicates they want to play again
    //         returns false otherwise
    public static boolean playAgain(Boolean test) {
        String userIn;
        if (!test) {
            System.out.println("Would you like to play another round? y/n");
            userIn = scan.nextLine();
        } else {
            userIn = "n";
        }
        return userIn.equals("y");
    }

    public static void gameOver() {
        System.out.println("GAME OVER");
    }

    //EFFECTS: prints a message out to the user summarizing their wins, loses, winrate and balance.
    public static void roundMessage(Player player, String message, int wins, int losses, float winrate) {
        System.out.println(message);
        System.out.println("wins: " + String.valueOf(wins)
                + " losses: " + String.valueOf(losses)
                + " win rate: " + String.valueOf(winrate) + "%"
                + " balance: " + String.valueOf(player.getBalance()));
    }

    //EFFECTS: prints a message about the dealer's action
    public static void dealerAction(String action) {
        System.out.println("The Dealer chose to " + action);
    }

    //EFFECTS: prints as message revealing one of the dealer's cards
    public static void revealDealerCard(String cardname) {
        System.out.println("The Dealer is holding a(n) " + cardname);
    }

    // some code here used from Json Serialize Demo @ https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //MODIFIES: file described in path string
    //EFFECTS: records the current object state of Game in a JSON file in the data folder
    public static void askToSave(Game game, boolean test) {
        System.out.println("Would you like to save your game? y/n");
        String s;

        if (test) {
            s = "n";
        } else {
            s = scan.nextLine();
        }

        if (s.equals("y")) {
            Writer w = new Writer(path);

            try {
                w.open();
                w.write(game);
                w.close();
                System.out.println("...");
                System.out.println("Your game was saved successfully!");
            } catch (IOException e) {
                System.out.println("IO EXCEPTION");
            }
        } else if (s.equals("n")) {
            System.out.println("Game not saved...");
        }
    }

    public static void askToLoad() {
        System.out.println("Would you like to load your saved game? y/n");
        String s = scan.nextLine();
        if (s.equals("y")) {
            Reader r = new Reader(path);
            try {
                Game game = r.read("./data/game.json", false);
            } catch (IOException e) {
                System.out.println("IO EXCEPTION");
            }
        } else if (s.equals("n")) {
            System.out.println("You have chosen to start a new game.");
            System.out.println("The new game will not overwrite the saved game "
                    + "unless you choose to save before quitting");

            String name = UserInterface.askName();
            int startingbalance = UserInterface.askStartingBalance();

            Game game = new Game(startingbalance, false);
        }
    }
}

 */
