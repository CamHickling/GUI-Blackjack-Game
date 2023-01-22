# Blackjack Game

I propose to design a single-player game of blackjack written in Java that is played against a dealer.
Just as in a regular game of Blackjack, the **user** will be able to choose from a few actions on their turn.
These actions will **include**:
- stand = hold the hand as is
- hit = add a card to the hand

The **dealer** will select actions on its turn according to the rules of Blackjack
and will be controlled by some simple logic. 

The game will follow the generally accepted rules of blackjack which **includes but are not limited to:**

- A hand greater than 21 is considered a 'bust' 
- If the player has 'bust' and the dealer has not, the **player loses**
- If the dealer has 'bust' and the player has not, the **player wins**
- If the player has not 'bust' and has drawn a hand greater than the dealer the **player wins**
- If the dealer has not 'bust' and has drawn a hand greater than the player, the **player loses**
- If player and dealer both 'bust' neither win, **it is a tie**
- If neither the player nor dealer have 'bust' and both draw an equal hand, **the player loses**

The user will use this program as it will accurately simulate a game of blackjack,
a game that is fun to play.
However, my game would have a few key advantages. 
People can play just by themselves as it will not require more than a single player to play the game.
Users can play without monetary stakes,
only a score counter/virtual money count to introduce a competitive aspect to the game between single player users.
As my game with properly emulate a real game of Blackjack, users can practise their skills/strategies for real games
without any consequences. 

The game of blackjack interests me because of the statistics and strategy involved.
By completing this project, I can explore this side of the game, and maybe even illustrate the effect of card counting
and different strategies on the winnings/losings over time.

## User Stories

- As a user, I want to be able to select which move to perform as in real blackjack
- As a user, I want to be able to choose how much money to wager
- As a user, I want to be able to view a round report after each round
- As a user, I want to be told if my save game has more balance than my current game
- As a user, I want to choose between loading an old game or starting a new one
- As a user, I want to be able to choose to save and exit or continue at the end of each round

# Reflecting on the project's design
![default](./UML%20Project%20Diagram.png)

Potential refactoring:
- Singleton design pattern can be applied to the GUI class
  - This would greatly decrease overall coupling
  - it would allow Writer, Reader, Round and Game to access GUI without requiring a GUI to be passed to them explicitly
  - There would no longer be cases where GUI is passes around through multiple constructors
- Singleton design pattern can further be applied to the Game and Player classes
  - this would have a small decrease in coupling
  - Game and Player only ever exist as single instances, and are passed around 
  - Player is explicitly held by both Game and Round which is unnecessary
- Maintaining the Player and Hand classes as they are is advantageous as the Dealer can be an instance of a hand object
- The current GUI class probably violates a number of principles and best practises, so it should split up
    - This would increase cohesion and better align with the principle of single point of control
    - A lot of code in the GUI class is related to set up and only runs once
    - Two static classes can be extracted from GUI
        - LoadingScreen - sets up the splash screen
        - MainScreen - sets up the main game screen
    - GUI class would then only be responsible for basic setup, observing user actions, and on-close events