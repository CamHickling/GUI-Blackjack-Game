package ui;

import model.Card;
import model.Event;
import model.EventLog;
import model.Game;
import model.Round;
import persistence.Reader;
import persistence.Writer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static java.util.Objects.isNull;

public class GUI extends JFrame implements ActionListener {

    //main screen elements
    private Game game;
    private JPanel main;
    private JLabel dealer;
    private JLabel player;
    private JLabel savegamebalance;
    private JLabel balance;
    private JButton savebutton;
    private JButton hitbutton;
    private JButton standbutton;
    private JButton playbutton;
    private JList<Round> roundlist;
    private JLabel bannertitle;
    private JLabel belowbannertext;

    private String path;
    private JLabel wins;
    private JLabel losses;
    private JLabel winrate;

    private JPanel lefthalf;
    private JPanel righthalf;


    //Load game elements
    private JLabel loadtitle;
    private JButton loadbutton;
    private JTextField initialBalanceField;
    private JButton startNewGameButton;
    private JPanel righttophalf;
    private JTextField betamountfield;
    private JPanel righttophalfright;
    private JPanel righttophalfleft;

    //EFFECTS: Creates a Graphical User Interface from the GUI Class
    public GUI() {
        super("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(700, 275));
        path = "./data/game.json";
        setLocationRelativeTo(null);
        setVisible(true);

        //PHASE 4: print out eventlog on close
        //BASED ON: https://kodejava.org/how-do-i-handle-a-window-closing-event/
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Iterator<Event> it = EventLog.getInstance().iterator();
                while (it.hasNext()) {
                    System.out.println(it.next() + "\n");
                }
                System.exit(0);
            }
        });

        //setup
        setupLoadScreen();
    }

    //EFFECT: initializes all the elements on the load screen
    public void setupLoadScreen() {
        JPanel load = makePanel("LOADING", this, 4, 1, true);
        JPanel loadbanner = makePanel("", load, false, false);
        JPanel iconbanner = makePanel("", load, true, false);
        JPanel middle = makePanel("Select", load, 1, 2, true);
        JPanel middleleft = makePanel("", middle, true, true);
        JPanel middleright = makePanel("", middle, true, true);
        JPanel bottom = makePanel("", load, true, false);

        loadtitle = makeLabel("Load Previous or Start New Game", loadbanner, 0);
        loadbutton = makeButton("Load", "load", bottom);
        startNewGameButton = makeButton("Start New Game", "startnewgame", middleright);
        initialBalanceField = makeField("starting balance", middleleft);

        //Modified from stack overflow: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
        BufferedImage blackJackIcon = null;
        try {
            blackJackIcon = ImageIO.read(new File("./data/icon3.png"));
            JLabel piclbl = new JLabel(new ImageIcon(blackJackIcon));
            iconbanner.add(piclbl);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
        pack();
    }

    //EFFECTS: Initializes all the panel elements for the main screen,
    // then calls to initialize labels and buttons
    public void setupMain() {
        this.main = new JPanel();
        this.main.setBorder(new TitledBorder(""));
        this.main.setLayout(new GridLayout(1,2));
        this.lefthalf = makePanel("Gameplay", main, 5, 1,true);
        this.righthalf = makePanel("Persistence", main, 2,1,true);

        //setup all panels
        setupPanels1();
    }

    public void setupPanels1() {
        JPanel leftbanner = makePanel("", lefthalf,false,false);
        JPanel lefttopthird = makePanel("", lefthalf, false,false);
        JPanel leftmiddlethird = makePanel("", lefthalf, false,false);
        JPanel spacer = makePanel("", lefthalf, false, false);
        JPanel leftbottomthird = makePanel("", lefthalf, 1, 2,true);

        setupPanels2(leftbanner, lefttopthird, leftmiddlethird, spacer, leftbottomthird);
    }

    //EFFECTS: set up children of already instantiated containers and call to set up buttons, text, fields
    public void setupPanels2(JPanel leftbanner, JPanel lefttopthird, JPanel leftmiddlethird,
                             JPanel spacer, JPanel leftbottomthird) {

        righttophalf = makePanel("", righthalf, 1,2,true);
        righttophalfleft = makePanel("Rounds Played", righttophalf, true, true);
        righttophalfright = makePanel("Stats", righttophalf, 3, 1, true);

        //JPanel righttopright = makePanel("Game Statistics ADD WINS LOSSES WINRATE", righttophalfright, 3,1, true);
        JPanel rightbottomhalf = makePanel("", righthalf, 3,1,false);

        JPanel leftbottomthirdlefthalf = makePanel("", leftbottomthird,true,false);
        JPanel leftbottomthirdrighthalf = makePanel("", leftbottomthird, true,false);

        JPanel rightrowtop = makePanel("Balances", rightbottomhalf, 1, 2, true);
        JPanel righttoprowleft = makePanel("", rightrowtop,false,false);
        JPanel righttoprowright = makePanel("", rightrowtop,false,false);
        JPanel rightrowmiddle = makePanel("", rightbottomhalf, true, true);
        JPanel rightrowbottom = makePanel("", rightbottomhalf, 1, 2, false);
        JPanel rightrowbottomleft = makePanel("", rightrowbottom, 1,2, false);
        JPanel rightrowbottomright = makePanel("", rightrowbottom, 1,2, false);

        //setup all lists
        updateRoundList();

        JPanel rowl = makePanel("", righttophalfright, false, true);
        JPanel rowm = makePanel("", righttophalfright, false, true);
        JPanel rowr = makePanel("", righttophalfright, false, true);

        //setup all buttons
        setupButtons(rightrowbottomleft, rightrowbottomright, leftbottomthirdlefthalf, leftbottomthirdrighthalf);

        //Setup all text
        setupText(lefttopthird, leftmiddlethird, righttoprowleft, righttoprowright, leftbanner, rowl, rowm, rowr);

        //setup betamount field
        betamountfield = makeField("enter a bet amount", rightrowmiddle);
    }

    //EFFECTS: Initializes all the labels for the main screen
    public void setupText(JPanel lefttopthird, JPanel leftmiddlethird, JPanel righttoprowleft,
                          JPanel righttoprowright, JPanel leftbanner, JPanel rowleft,
                          JPanel rowmiddle, JPanel rowright) {
        JLabel dealertitle = makeLabel("DEALER HAND", lefttopthird, 0);
        //this.dealer = makeLabel(getDealerHand(), lefttopthird, 1);
        this.dealer = makeLabel("", lefttopthird, 1);

        JLabel playertitle = makeLabel("PLAYER HAND", leftmiddlethird, 0);
        //this.player = makeLabel(game.getPlayer().getHand().toString(), leftmiddlethird, 1);
        this.player = makeLabel("", leftmiddlethird, 1);

        JLabel balancetitle = makeLabel("balance", righttoprowleft, 0);
        //JLabel balance = makeLabel(String.valueOf(game.getSaveGameBalance()), righttoprowleft, 1);
        this.balance = makeLabel("$" + String.valueOf(game.getBalance()), righttoprowleft, 1);

        JLabel savegamebalancetitle = makeLabel("saved balance", righttoprowright, 0);
        //JLabel savegamebalance = makeLabel("$" + String.valueOf(game.getSaveGameBalance()), righttoprowright, 1);
        this.savegamebalance = makeLabel("$" + String.valueOf(game.getSaveGameBalance()), righttoprowright, 1);

        this.bannertitle = makeLabel("banner title", leftbanner, 0);
        this.belowbannertext = makeLabel("", leftbanner, 1);

        JLabel winstitle = makeLabel("Wins", rowleft, 0);
        this.wins = makeLabel(String.valueOf(game.getNumwins()), rowleft, 1);
        JLabel lossestitle = makeLabel("Losses", rowmiddle, 0);
        this.losses = makeLabel(String.valueOf(game.getNumlosses()), rowmiddle, 1);
        JLabel winratetitle = makeLabel("Winrate", rowright, 0);
        this.winrate = makeLabel(String.format("%.2f", game.getWinrate()) + "%", rowright, 1);
    }

    //EFFECTS: Initializes all the buttons for the main screen
    public void setupButtons(JPanel rightbottomrowleft, JPanel rightbottomrowright,
                             JPanel leftbottomthirdlefthalf, JPanel leftbottomthirdrighthalf) {
        this.savebutton = makeButton("Save & Close", "save", rightbottomrowright);
        this.hitbutton = makeButton("Hit", "hit", leftbottomthirdlefthalf);
        this.standbutton = makeButton("Stand", "stand", leftbottomthirdrighthalf);
        this.playbutton = makeButton("Play", "play", rightbottomrowleft);
    }

    //EFFECTS: changes all the main screen button states to false
    public void disableAllButtons() {
        this.savebutton.setEnabled(false);
        this.hitbutton.setEnabled(false);
        this.standbutton.setEnabled(false);
        this.playbutton.setEnabled(false);
    }

    //EFFECTS: Initializes the round list for the main screen
    public void updateRoundList() {
        this.roundlist = new JList(game.getRoundList().toArray());
        this.righttophalfleft.removeAll();
        JScrollPane jsp = new JScrollPane(this.roundlist);
        this.righttophalfleft.add(jsp, BorderLayout.CENTER);
    }

    //EFFECTS: constucts and returns a JPanel with the following presets
    public JPanel makePanel(String title, Container addTo, int rows, int cols, boolean border) {
        JPanel jp = makePanel(title, addTo, false, border);
        jp.setLayout(new GridLayout(rows, cols));
        return jp;
    }

    //EFFECTS: constucts and returns a JPanel with the following presets adds it to the given container
    public JPanel makePanel(String title, Container addTo, boolean borderlayout, boolean border) {
        JPanel jp = new JPanel();
        if (border) {
            jp.setBorder(new TitledBorder(title));
        }
        if (borderlayout) {
            jp.setLayout(new BorderLayout());
        } else {
            jp.setLayout(new GridBagLayout());
        }
        addTo.add(jp);
        return jp;
    }

    //EFFECTS: constructs and returns a button with text and command and adds it to the given container
    public JButton makeButton(String text, String actionCommand, JPanel addTo) {
        JButton b = new JButton(text);
        b.setActionCommand(actionCommand);
        b.addActionListener(this);
        if (!isNull(balance)) {
            b.addActionListener(ae -> disableAllButtons());
        }
        addTo.add(b, BorderLayout.CENTER);
        return b;
    }

    //EFFECTS: constructs and returns a label with text, and y rank, then adds it to the given container
    public JLabel makeLabel(String label, JPanel addTo, int y) {
        JLabel lbl = new JLabel(label);
        if (y >= 0) {
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = y;
            addTo.add(lbl, c);
        } else {
            addTo.add(lbl);
        }
        return lbl;
    }

    //EFFECTS: constructs a textfield with the given title, adds focus event listener and functionality
    // and adds it to the given container
    public JTextField makeField(String title, Container addTo) {
        JTextField f = new JTextField(title);
        f.addActionListener(this);
        f.setForeground(Color.gray);
        f.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                f.setText("");
                f.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (f.getText().length() == 0) {
                    f.setText(title);
                    f.setForeground(Color.gray);
                }
            }
        });
        addTo.add(f);
        return f;
    }

    //EFFECTS: catches action listener event and call corresponding method
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "startnewgame":
                start();
                break;
            case "hit":
                hit();
                break;
            case "stand":
                stand();
                break;
            case "save":
                save();
                break;
            case "load":
                load();
                break;
            case "play":
                play();
                break;
        }
    }

    //EFFECTS: guards call to playgame, requires the input to be anumber less than equal to balance
    //          and greater than 1
    public void play() {
        disableAllButtons();
        String bf = betamountfield.getText();
        if (bf.matches("[1-9][0-9]*") && Integer.parseInt(bf) <= game.getBalance())  {
            enablehitstand();
            EventLog.getInstance().logEvent(new Event("bet amount set for the next round"));
            game.playGame(false, Integer.parseInt(bf));
            updateGUI(false);
        } else {
            enablesaveplay();
            bannertitle.setText("Please enter a valid bet amount!");

        }
        betamountfield.setText("");
        if (game.getGameOver()) {
            gameOver();
        }
    }

    //EFFECTS: guards initial game creation, requires balance input to be greater than = 1
    public void start() {
        String ib = initialBalanceField.getText();
        if (ib.matches("[1-9][0-9]*"))  {
            this.game = new Game(Integer.parseInt(ib), false, this, true);
            newGame();
        } else {
            loadtitle.setText("Please enter a valid integer starting balance!");
            initialBalanceField.setText("starting balance");
            initialBalanceField.setForeground(Color.gray);
        }
    }

    //EFFECTS: calls to load data from file and transition from load screen to main screen
    private void load() {
        path = "./data/game.json";
        File f = new File(path);
        if (f.length() > 3) {
            Reader r = new Reader(path, this);
            try {
                this.game = r.read(path, false);
                newGame();
            } catch (IOException e) {
                loadtitle.setText("Game could not be loaded. Please begin a new game.");
            }
        } else {
            loadtitle.setText("No game to load.");
        }
    }

    //EFFECT: sets up the main screen and guards against gameover status
    //          then repaints to bring main to screen
    public void newGame() {
        setupMain();
        if (!game.getGameOver()) {
            disableAllButtons();
            bannertitle.setText("Click Play");
            enablesaveplay();
        } else {
            gameOver();
        }
        //Change from load to main screen
        getContentPane().removeAll();
        add(main);
        repaint();

        //pack in contents
        pack();
    }

    //EFFECTS: calls to the player class to hit
    private void hit() {
        if (hitbutton.isEnabled()) {
            disableAllButtons();
            game.getPlayer().hit();
            updateGUI(false);
            enablehitstand();
        }
        if (game.getPlayer().getHand().getHandValue() > 21) {
            stand();
        }
    }

    //EFFECTS: calls to the player class to stand and update status of labels
    private void stand() {
        if (standbutton.isEnabled()) {
            disableAllButtons();
            game.getPlayer().stand();
            //once player has stood, record result and add round to list, then update GUI
            game.recordResult(game.getPlayer(), game.getRound().judgeWinner(game.getPlayer().getHand().getHandValue(),
                    game.getDealer().getHandValue()));
            updateGUI(true);
            if (game.getGameOver()) {
                gameOver();
            } else {
                enablesaveplay();
            }
            updateGUI(true);
        }
    }

    //EFFECTS: calls to save data to file
    private void save() {
        disableAllButtons();
        Writer w = new Writer(path);
        try {
            initialBalanceField.setText("starting balance");
            initialBalanceField.setForeground(Color.gray);
            w.open();
            w.write(game);
            w.close();
            //closeAnimation();

            close();

        } catch (IOException e) {
            bannertitle.setText("Could not save game.");
        }
    }

    //EFFECTS: closes window
    private void close() {
        //From Stack OverFlow:https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /*
    private void closeAnimation() {
        String title = ".";
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 5; i++) {
                bannertitle.setText(title);
                try {
                    wait(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        bannertitle.setText("Your game was saved successfully!");
        try {
            wait(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

     */

    //EFFECTS: disables buttons, and prints user a message
    public void gameOver() {
        disableAllButtons();
        savebutton.setEnabled(true);
        bannertitle.setText("GAMEOVER");
        belowbannertext.setText("You may save before exiting.");
    }

    //EFFECTS: update all labels to reflect current model values
    public void updateGUI(boolean roundover) {
        if (roundover) {
            this.dealer.setText(game.getDealer().toString());
        } else {
            this.dealer.setText(getDealerHand());
        }
        this.player.setText(game.getPlayer().getHand().toString());

        //Distinguish between balance and savegame balance
        this.balance.setText("$" + game.getPlayer().getBalance());
        //this.savegamebalance.setText(String.valueOf("$" + game.getSaveGameBalance()));

        this.wins.setText(String.valueOf(game.getNumwins()));
        this.losses.setText(String.valueOf(game.getNumlosses()));
        this.winrate.setText(String.format("%.2f", game.getWinrate()) + "%");
        updateRoundList();
    }

    //EFFECTS: returns dealer's hand in a format to hide cards
    public String getDealerHand() {
        ArrayList<Card> cards = game.getDealer().getMyCards();
        String s = "";
        s += cards.get(0).getName() + " ";
        for (int i = 1; i < cards.size() - 1; i++) {
            s += "* ";
        }
        s += "*";
        return s;
    }

    //EFFECTS: enables play and save buttons
    public void enablesaveplay() {
        this.playbutton.setEnabled(true);
        this.savebutton.setEnabled(true);
    }

    //EFFECTS: enables hit and stand buttons
    public void enablehitstand() {
        this.hitbutton.setEnabled(true);
        this.standbutton.setEnabled(true);
    }

    //EFFECTS: sends message to user to select an action
    public void playersTurn() {
        bannertitle.setText("Your Turn.");
        //disableAllButtons();
        savebutton.setEnabled(false);
        playbutton.setEnabled(false);
        enablehitstand();
    }

    //EFFECTS: sets banner text to given string
    public void setBanner(String text) {
        bannertitle.setText(text);
    }
}

