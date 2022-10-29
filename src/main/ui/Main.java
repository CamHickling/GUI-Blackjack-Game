package ui;

import model.*;
import persistence.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File f = new File("./data/rounds.json");
        if (f.length() > 3) {
            UserInterface.askToLoad();
        }

        String name = UserInterface.askName();
        int startingbalance = UserInterface.askStartingBalance();

        Game game = new Game(name, startingbalance, false);

    }
}
