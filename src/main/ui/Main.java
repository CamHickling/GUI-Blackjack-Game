package ui;

import model.*;

public class Main {
    public static void main(String[] args) {
        String name = UserInterface.askName();
        int startingbalance = UserInterface.askStartingBalance();
        int betamount = UserInterface.askBetAmount(startingbalance);

        Round round = new Round(new Player(name, startingbalance), new Hand(), betamount, false, false);
    }
}
