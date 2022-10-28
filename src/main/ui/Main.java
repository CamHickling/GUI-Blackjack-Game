package ui;

import model.*;

public class Main {
    public static void main(String[] args) {
        String name = UserInterface.askName();
        int startingbalance = UserInterface.askStartingBalance();

        Game game = new Game(name, startingbalance, false);
    }
}
